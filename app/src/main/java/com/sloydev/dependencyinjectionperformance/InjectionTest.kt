package com.sloydev.dependencyinjectionperformance

import android.os.Build
import com.sloydev.dependencyinjectionperformance.custom.DIContainer
import com.sloydev.dependencyinjectionperformance.custom.customKotlinModule
import com.sloydev.dependencyinjectionperformance.dagger2.DaggerKotlinDaggerComponent
import com.sloydev.dependencyinjectionperformance.dagger2.KotlinDaggerComponent
import com.sloydev.dependencyinjectionperformance.koin.koinKotlinModule
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import javax.inject.Inject

class InjectionTest : KoinComponent {

    private val kotlinDaggerTest = KotlinDaggerTest()

    private val rounds = 100

    fun runTests(): List<LibraryResult> {
        val results = listOf(
            koinTest(),
            customTest(),
            daggerTest()
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

    private fun koinTest(): LibraryResult {
        log("Running Koin...")
        return LibraryResult("Koin (DSL)", mapOf(
            Variant.KOTLIN to runTest(
                setup = {
                    startKoin {
                        modules(koinKotlinModule)
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

    private fun daggerTest(): LibraryResult {
        log("Running Dagger...")
        lateinit var kotlinComponent: KotlinDaggerComponent
        return LibraryResult("Dagger", mapOf(
            Variant.KOTLIN to runTest(
                setup = { kotlinComponent = DaggerKotlinDaggerComponent.create() },
                test = { kotlinComponent.inject(kotlinDaggerTest) }
            )
        ))
    }

    class KotlinDaggerTest {
        @Inject
        lateinit var daggerFib8: Fib8
    }
}
