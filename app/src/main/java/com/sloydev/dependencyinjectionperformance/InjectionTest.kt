package com.sloydev.dependencyinjectionperformance

import android.os.Build
import android.util.Log
import com.sloydev.dependencyinjectionperformance.dagger2.DaggerJavaDaggerComponent
import com.sloydev.dependencyinjectionperformance.dagger2.DaggerKotlinDaggerComponent
import com.sloydev.dependencyinjectionperformance.koin.koinJavaModule
import com.sloydev.dependencyinjectionperformance.koin.koinKotlinModule
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.erased.instance
import org.koin.core.time.measureDuration
import org.koin.log.Logger
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.standalone.get
import javax.inject.Inject

class InjectionTest : KoinComponent {

    private val kotlinDaggerTest = KotlinDaggerTest()
    private val javaDaggerTest = JavaDaggerTest()

    private val rounds = 100

    private val testLogger = object : Logger {
        override fun debug(msg: String) {
            //Log.d("KOIN-PERFORMANCE", msg)
        }

        override fun info(msg: String) {
        }

        override fun err(msg: String) {
        }
    }

    fun runTests() {
        Log.d("KOIN-RESULT", " ")
        Log.d("KOIN-RESULT", "=========|=====================")
        Log.d("KOIN-RESULT", "Device:  | ${Build.BRAND} ${Build.DEVICE} v${Build.VERSION.RELEASE}")
        runKoinKotlinInjection()
        runKoinJavaInjection()
        runKodeinKotlinInjection()
        runKodeinJavaInjection()
        runDaggerKotlinInjection()
        runDaggerJavaInjection()
        Log.d("KOIN-RESULT", "=========|=====================")
        Log.d("KOIN-RESULT", " ")
    }

    private fun runKoinKotlinInjection() {
        StandAloneContext.startKoin(listOf(koinKotlinModule), logger = testLogger)
        val durations = (1..rounds).map {
            measureDuration {
                get<Fib8>()
            }
        }
        report(durations, "Koin + Kotlin")
        stopKoin()
    }

    private fun runKoinJavaInjection() {
        StandAloneContext.startKoin(listOf(koinJavaModule), logger = testLogger)
        val durations = (1..rounds).map {
            measureDuration {
                get<FibonacciJava.Fib8>()
            }
        }
        report(durations, "Koin + Java")
        stopKoin()
    }

    private fun runKodeinKotlinInjection() {
        val kodein = Kodein {
            import(kodeinKotlinModule)
        }
        val durations = (1..rounds).map {
            measureDuration {
                kodein.direct.instance<Fib8>()
            }
        }
        report(durations, "Kodein + Kotlin")
    }

    private fun runKodeinJavaInjection() {
        val kodein = Kodein {
            import(kodeinJavaModule)
        }
        val durations = (1..rounds).map {
            measureDuration {
                kodein.direct.instance<FibonacciJava.Fib8>()
            }
        }
        report(durations, "Kodein + Java")
    }

    private fun runDaggerKotlinInjection() {
        val component = DaggerKotlinDaggerComponent.create()
        val durations = (1..rounds).map {
            measureDuration {
                component.inject(kotlinDaggerTest)
            }
        }
        report(durations, "Dagger2 + Kotlin")
    }

    private fun runDaggerJavaInjection() {
        val component = DaggerJavaDaggerComponent.create()
        val durations = (1..rounds).map {
            measureDuration {
                component.inject(javaDaggerTest)
            }
        }
        report(durations, "Dagger2 + Java")
    }

    private fun report(durations: List<Double>, testName: String) {
        Log.d("KOIN-RESULT", "---------|--------------------")
        Log.d("KOIN-RESULT", "Test:    | $testName")
        Log.d("KOIN-RESULT", "Min-Max: | ${durations.min().format()}-${durations.max().format()} ms")
        Log.d("KOIN-RESULT", "Average: | ${durations.average().format()} ms")
    }

    private fun Double?.format() = String.format("%.2f", this)

    class KotlinDaggerTest {
        @Inject
        lateinit var daggerFib8: Fib8
    }

    class JavaDaggerTest {
        @Inject
        lateinit var daggerFib8: FibonacciJava.Fib8
    }
}