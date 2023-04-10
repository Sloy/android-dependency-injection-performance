package com.sloydev.dependencyinjectionperformance

import android.os.Build
import com.sloydev.dependencyinjectionperformance.custom.DIContainer
import com.sloydev.dependencyinjectionperformance.custom.customKotlinModule
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

// Use Koin Generation
import org.koin.ksp.generated.*


class InjectionTest : KoinComponent {
    private val rounds = 100

    fun runTests(): List<LibraryResult> {
        val results = listOf(
            koinAnnotationTest(),
            customTest()
        )
        reportMarkdown(results)
        return results
    }

    private fun reportMarkdown(results: List<LibraryResult>) {
        log("Done!")
        log(" ")
        log("${Build.BRAND} ${Build.DEVICE} with Android ${Build.VERSION.RELEASE}")
        log(" ")
        log("Library | Setup Kotlin | Setup Java | Inject Kotlin | Inject Java")
        log("--- | ---:| ---:| ---:| ---:")
        results.forEach {
            log("**${it.injectorName}** | ${it[Variant.KOTLIN].startupTime.median().format()}  | ${it[Variant.KOTLIN].injectionTime.median().format()} ")
        }
    }

    private fun runTest(
        setup: () -> Unit,
        test: () -> Unit,
        teardown: () -> Unit = {}
    ): TestResult {
        val startup = (1..rounds).map { measureTime { setup() }.also { teardown() } }
        setup()

        val testDurations = (1..rounds).map { measureTime { test() } }
        teardown()
        return TestResult(startup, testDurations)
    }

    private fun koinAnnotationTest(): LibraryResult {
        log("Running Koin...")
        return LibraryResult("Koin (Annotation)", mapOf(
            Variant.KOTLIN to runTest(
                setup = {
                    startKoin {
                        modules(defaultModule)
                    }
                },
                test = { get<Fib8>() },
                teardown = { stopKoin() }
            ),
        ))
    }

    private fun customTest(): LibraryResult {
        log("Running Custom...")
        return LibraryResult("Custom", mapOf(
            Variant.KOTLIN to runTest(
                setup = { DIContainer.loadModule(customKotlinModule) },
                test = { DIContainer.get<Fib8>() },
                teardown = { DIContainer.unloadModules() }
            )
        ))
    }
}
