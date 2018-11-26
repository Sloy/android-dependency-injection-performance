package com.sloydev.dependencyinjectionperformance

import android.os.Build
import android.util.Log
import com.sloydev.dependencyinjectionperformance.custom.DIContainer
import com.sloydev.dependencyinjectionperformance.custom.customJavaModule
import com.sloydev.dependencyinjectionperformance.custom.customKotlinModule
import com.sloydev.dependencyinjectionperformance.dagger2.DaggerJavaDaggerComponent
import com.sloydev.dependencyinjectionperformance.dagger2.DaggerKotlinDaggerComponent
import com.sloydev.dependencyinjectionperformance.dagger2.JavaDaggerComponent
import com.sloydev.dependencyinjectionperformance.dagger2.KotlinDaggerComponent
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
        runCustomKotlinInjection()
        runCustomJavaInjection()
        Log.d("KOIN-RESULT", "=========|=====================")
        Log.d("KOIN-RESULT", " ")
    }

    private fun runKoinKotlinInjection() {
        val startup = measureDuration {
            StandAloneContext.startKoin(listOf(koinKotlinModule), logger = testLogger)
        }

        val durations = (1..rounds).map {
            measureDuration {
                get<Fib8>()
            }
        }
        report(durations, startup, "Koin + Kotlin")
        stopKoin()
    }

    private fun runKoinJavaInjection() {
        val startup = measureDuration {
            StandAloneContext.startKoin(listOf(koinJavaModule), logger = testLogger)
        }

        val durations = (1..rounds).map {
            measureDuration {
                get<FibonacciJava.Fib8>()
            }
        }
        report(durations, startup, "Koin + Java")
        stopKoin()
    }

    private fun runKodeinKotlinInjection() {
        lateinit var kodein: Kodein
        val startup = measureDuration {
            kodein = Kodein {
                import(kodeinKotlinModule)
            }
        }
        val durations = (1..rounds).map {
            measureDuration {
                kodein.direct.instance<Fib8>()
            }
        }
        report(durations, startup, "Kodein + Kotlin")
    }

    private fun runKodeinJavaInjection() {
        lateinit var kodein: Kodein
        val startup = measureDuration {
            kodein = Kodein {
                import(kodeinJavaModule)
            }
        }
        val durations = (1..rounds).map {
            measureDuration {
                kodein.direct.instance<FibonacciJava.Fib8>()
            }
        }
        report(durations, startup, "Kodein + Java")
    }

    private fun runDaggerKotlinInjection() {
        lateinit var component: KotlinDaggerComponent
        val startup = measureDuration {
            component = DaggerKotlinDaggerComponent.create()
        }
        val durations = (1..rounds).map {
            measureDuration {
                component.inject(kotlinDaggerTest)
            }
        }
        report(durations, startup, "Dagger2 + Kotlin")
    }

    private fun runDaggerJavaInjection() {
        lateinit var component: JavaDaggerComponent
        val startup = measureDuration {
            component = DaggerJavaDaggerComponent.create()
        }
        val durations = (1..rounds).map {
            measureDuration {
                component.inject(javaDaggerTest)
            }
        }
        report(durations, startup, "Dagger2 + Java")
    }

    private fun runCustomKotlinInjection() {
        val startup = measureDuration {
            DIContainer.loadModule(customKotlinModule)
        }
        val durations = (1..rounds).map {
            measureDuration {
                DIContainer.get<Fib8>()
            }
        }
        report(durations, startup, "Custom + Kotlin")
    }

    private fun runCustomJavaInjection() {
        val startup = measureDuration {
            DIContainer.loadModule(customJavaModule)
        }
        val durations = (1..rounds).map {
            measureDuration {
                DIContainer.get<FibonacciJava.Fib8>()
            }
        }
        report(durations, startup, "Custom + Java")
    }

    private fun report(durations: List<Double>, startup: Double, testName: String) {
        Log.d("KOIN-RESULT", "---------|--------------------")
        Log.d("KOIN-RESULT", "Test:    | $testName")
        Log.d("KOIN-RESULT", "Startup: | ${startup.format()} ms")
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