package com.sloydev.dependencyinjectionperformance

import android.content.res.Resources
import android.util.Log
import java.util.*
import kotlin.system.measureNanoTime

typealias Milliseconds = Double

data class LibraryResult(val injectorName: String, val results: Map<Variant, TestResult>) {
    operator fun get(variant: Variant) = results[variant]!!
}

data class TestResult(
    val startupTime: List<Milliseconds>,
    val injectionTime: List<Milliseconds>
)

enum class Variant {
    KOTLIN
}

fun Milliseconds?.format() = String.format(Locale.ENGLISH, "%.2f ms", this)

fun measureTime(block: () -> Unit): Milliseconds = measureNanoTime(block) / 1000000.0

fun List<Milliseconds>.median() = sorted().let { (it[it.size / 2] + it[(it.size - 1) / 2]) / 2 }


fun log(msg: String) {
    Log.i("DI-TEST", msg)
}

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()
