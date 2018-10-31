# Android Injection Performance

This project aims to measure the performance of several Dependency Injection frameworks (or Service Locators) in different devices.

## Libraries tested
- [Koin](https://insert-koin.io/) - 1.0.1
- [Kodein](http://kodein.org/Kodein-DI/) - 5.3.0
- [Dagger 2](https://google.github.io/dagger/) - 2.16

## The test
The test data are classes with dependencies in a structure similar to Fibonacci sequence, to simulate multiple levels of transitive dependencies.
For each library there is a test with Kotlin classes and one with Java classes, because some libraries seem to be affected by this.

Each test injects one of this dependencies 100 times and prints the maximum time, the minimum and the average.

The project contains an Android application that run the tests on its onCreate and prints the result to the Logcat.

The actual test is implemented in the class [InjectionTest.kt](https://github.com/Sloy/android-dependency-injection-performance/blob/master/app/src/main/java/com/sloydev/dependencyinjectionperformance/InjectionTest.kt)

## Results
Results can be quite different between different devices, so here are some results in different devices with different Android versions. More results are welcomed.

- [Samsung Galaxy J5](#samsung-galaxy-j5)
- [Huawei P8 Lite](#huawei-p8-lite)
- [OnePlus One](#oneplus-one)
- [OnePlus 5](#huawei-p8-lite)
- [Nexus 6](#nexus-6)

### Samsung Galaxy J5
```
=========|=====================
Device:  | samsung j5nlte v6.0.1
---------|--------------------
Test:    | Koin + Kotlin
Min-Max: | 47,41-74,25 ms
Average: | 60,16 ms
---------|--------------------
Test:    | Koin + Java
Min-Max: | 189,77-205,96 ms
Average: | 194,09 ms
---------|--------------------
Test:    | Kodein + Kotlin
Min-Max: | 0,76-3,38 ms
Average: | 0,83 ms
---------|--------------------
Test:    | Kodein + Java
Min-Max: | 0,82-4,67 ms
Average: | 0,93 ms
---------|--------------------
Test:    | Dagger2 + Kotlin
Min-Max: | 0,03-9,48 ms
Average: | 0,12 ms
---------|--------------------
Test:    | Dagger2 + Java
Min-Max: | 0,02-2,03 ms
Average: | 0,05 ms
=========|=====================
```

### Huawei P8 Lite
```
=========|=====================
Device:  | Huawei hwALE-H v6.0
---------|--------------------
Test:    | Koin + Kotlin
Min-Max: | 245,43-273,91 ms
Average: | 253,30 ms
---------|--------------------
Test:    | Koin + Java
Min-Max: | 373,67-399,93 ms
Average: | 377,57 ms
---------|--------------------
Test:    | Kodein + Kotlin
Min-Max: | 7,49-15,63 ms
Average: | 7,63 ms
---------|--------------------
Test:    | Kodein + Java
Min-Max: | 7,94-10,78 ms
Average: | 8,08 ms
---------|--------------------
Test:    | Dagger2 + Kotlin
Min-Max: | 0,22-5,60 ms
Average: | 0,28 ms
---------|--------------------
Test:    | Dagger2 + Java
Min-Max: | 0,20-5,04 ms
Average: | 0,25 ms
=========|=====================
```

### OnePlus One
```
=========|=====================
Device:  | oneplus A0001 v5.0.2
---------|--------------------
Test:    | Koin + Kotlin
Min-Max: | 26,78-73,14 ms
Average: | 34,38 ms
---------|--------------------
Test:    | Koin + Java
Min-Max: | 105,04-201,14 ms
Average: | 128,99 ms
---------|--------------------
Test:    | Kodein + Kotlin
Min-Max: | 1,11-3,34 ms
Average: | 1,34 ms
---------|--------------------
Test:    | Kodein + Java
Min-Max: | 1,27-3,06 ms
Average: | 1,40 ms
---------|--------------------
Test:    | Dagger2 + Kotlin
Min-Max: | 0,02-2,81 ms
Average: | 0,07 ms
---------|--------------------
Test:    | Dagger2 + Java
Min-Max: | 0,02-1,84 ms
Average: | 0,05 ms
=========|=====================
```

### OnePlus 5
```
=========|=====================
Device:  | OnePlus OnePlus5 v8.1.0
---------|--------------------
Test:    | Koin + Kotlin
Min-Max: | 4,08-23,18 ms
Average: | 5,04 ms
---------|--------------------
Test:    | Koin + Java
Min-Max: | 4,61-11,47 ms
Average: | 5,16 ms
---------|--------------------
Test:    | Kodein + Kotlin
Min-Max: | 0,09-3,51 ms
Average: | 0,27 ms
---------|--------------------
Test:    | Kodein + Java
Min-Max: | 0,09-1,03 ms
Average: | 0,16 ms
---------|--------------------
Test:    | Dagger2 + Kotlin
Min-Max: | 0,01-0,42 ms
Average: | 0,04 ms
---------|--------------------
Test:    | Dagger2 + Java
Min-Max: | 0,01-0,32 ms
Average: | 0,03 ms
=========|=====================
```

### Nexus 6
```
=========|=====================
Device:  | google shamu v7.1.1
---------|--------------------
Test:    | Koin + Kotlin
Min-Max: | 12,47-52,59 ms
Average: | 15,56 ms
---------|--------------------
Test:    | Koin + Java
Min-Max: | 15,30-26,37 ms
Average: | 17,14 ms
---------|--------------------
Test:    | Kodein + Kotlin
Min-Max: | 0,50-13,12 ms
Average: | 1,50 ms
---------|--------------------
Test:    | Kodein + Java
Min-Max: | 0,52-5,87 ms
Average: | 0,98 ms
---------|--------------------
Test:    | Dagger2 + Kotlin
Min-Max: | 0,13-1,46 ms
Average: | 0,32 ms
---------|--------------------
Test:    | Dagger2 + Java
Min-Max: | 0,10-1,15 ms
Average: | 0,30 ms
=========|=====================
```