# Android Injection Performance

This project aims to measure the performance of several Dependency Injection frameworks (or Service Locators) in different devices.

## Libraries tested
- [Koin](https://insert-koin.io/) - 2.0.1
- [Kodein](http://kodein.org/Kodein-DI/) - 6.3.3
- [Dagger 2](https://google.github.io/dagger/) - 2.24
- [Katana](https://github.com/rewe-digital-incubator/katana/) - 1.7.1

## The test
The test data are classes with dependencies in a structure similar to Fibonacci sequence, to simulate multiple levels of transitive dependencies.
For each library there is a test with Kotlin classes and one with Java classes, because some libraries seem to be affected by this difference.

Each test injects one of this dependencies 100 times and prints the maximum time, the minimum and the average.

The project contains an Android application that run the tests on its onCreate and prints the result to the Logcat.

The actual test is implemented in the class [InjectionTest.kt](https://github.com/Sloy/android-dependency-injection-performance/blob/master/app/src/main/java/com/sloydev/dependencyinjectionperformance/InjectionTest.kt)

## Results
Results can be quite different between different devices, so here are some results in different devices with different Android versions. Each table contains the **median** time of each library's setup and injection. More results are welcomed.

- [Samsung Galaxy J5](#samsung-galaxy-j5)
- [Samsung Galaxy S8](#samsung-galaxy-s8)
- [Huawei P8 Lite](#huawei-p8-lite)
- [Xiaomi MI A1](#xiaomi-mi-a1)
- [OnePlus One](#oneplus-one)
- [OnePlus 5](#oneplus-5)
- [Nexus 6](#nexus-6)
- [OnePlus 5T](#oneplus-5t) (dependencies updated)

### Samsung Galaxy J5
Samsung j5nlte with Android 6.0.1
 
Library | Setup Kotlin | Setup Java | Inject Kotlin | Inject Java
--- | ---:| ---:| ---:| ---:
**Koin** | 51.47 ms | 53.65 ms  | 2.47 ms | 2.52 ms
**Kodein** | 73.36 ms | 75.21 ms  | 9.89 ms | 9.58 ms
**Katana** | 12.34 ms | 12.30 ms  | 2.00 ms | 1.94 ms
**Custom** | 4.85 ms | 4.81 ms  | 0.73 ms | 0.84 ms
**Dagger** | 0.02 ms | 0.02 ms  | 0.27 ms | 0.23 ms

### Samsung Galaxy S8
Samsung dreamlte with Android 8.0.0
 
Library | Setup Kotlin | Setup Java | Inject Kotlin | Inject Java
--- | ---:| ---:| ---:| ---:
**Koin** | 5.68 ms | 6.04 ms  | 0.13 ms | 0.21 ms
**Kodein** | 7.13 ms | 7.38 ms  | 0.20 ms | 0.21 ms
**Katana** | 0.64 ms | 0.68 ms  | 0.21 ms | 0.16 ms
**Custom** | 0.15 ms | 0.16 ms  | 0.11 ms | 0.11 ms
**Dagger** | 0.01 ms | 0.01 ms  | 0.10 ms | 0.10 ms

### Huawei P8 Lite
Huawei hwALE-H with Android 6.0
 
Library | Setup Kotlin | Setup Java | Inject Kotlin | Inject Java
--- | ---:| ---:| ---:| ---:
**Koin** | 12.12 ms | 12.34 ms  | 0.26 ms | 0.26 ms
**Kodein** | 14.46 ms | 14.52 ms  | 0.80 ms | 0.79 ms
**Katana** | 2.01 ms | 1.99 ms  | 0.18 ms | 0.18 ms
**Custom** | 0.51 ms | 0.50 ms  | 0.08 ms | 0.09 ms
**Dagger** | 0.00 ms | 0.00 ms  | 0.03 ms | 0.02 ms

### Xiaomi MI A1
Xiaomi tissot_sprout with Android 8.1.0
 
Library | Setup Kotlin | Setup Java | Inject Kotlin | Inject Java
--- | ---:| ---:| ---:| ---:
**Koin** | 9.17 ms | 11.10 ms  | 0.25 ms | 0.54 ms
**Kodein** | 16.64 ms | 16.22 ms  | 0.82 ms | 0.32 ms
**Katana** | 1.42 ms | 1.28 ms  | 0.31 ms | 0.31 ms
**Custom** | 0.28 ms | 0.28 ms  | 0.19 ms | 0.23 ms
**Dagger** | 0.02 ms | 0.02 ms  | 0.28 ms | 0.21 ms

### OnePlus One
OnePlus A0001 with Android 5.0.2
 
Library | Setup Kotlin | Setup Java | Inject Kotlin | Inject Java
--- | ---:| ---:| ---:| ---:
**Koin** | 11.84 ms | 12.00 ms  | 0.27 ms | 0.30 ms
**Kodein** | 22.52 ms | 23.05 ms  | 1.11 ms | 1.28 ms
**Katana** | 1.87 ms | 1.58 ms  | 0.27 ms | 0.18 ms
**Custom** | 0.43 ms | 0.35 ms  | 0.09 ms | 0.10 ms
**Dagger** | 0.00 ms | 0.00 ms  | 0.04 ms | 0.04 ms

### OnePlus 5
OnePlus OnePlus5 with Android 8.1.0
 
Library | Setup Kotlin | Setup Java | Inject Kotlin | Inject Java
--- | ---:| ---:| ---:| ---:
**Koin** | 2.27 ms | 2.46 ms  | 0.05 ms | 0.05 ms
**Kodein** | 4.81 ms | 4.43 ms  | 0.09 ms | 0.08 ms
**Katana** | 0.34 ms | 0.32 ms  | 0.04 ms | 0.04 ms
**Custom** | 0.07 ms | 0.08 ms  | 0.02 ms | 0.03 ms
**Dagger** | 0.00 ms | 0.00 ms  | 0.04 ms | 0.02 ms

### Nexus 6
Google shamu with Android 7.1.1
 
Library | Setup Kotlin | Setup Java | Inject Kotlin | Inject Java
--- | ---:| ---:| ---:| ---:
**Koin** | 19.80 ms | 20.43 ms  | 0.45 ms | 0.35 ms
**Kodein** | 21.48 ms | 21.02 ms  | 0.74 ms | 0.62 ms
**Katana** | 1.23 ms | 1.16 ms  | 0.31 ms | 0.27 ms
**Custom** | 0.29 ms | 0.29 ms  | 0.20 ms | 0.31 ms
**Dagger** | 0.03 ms | 0.03 ms  | 0.22 ms | 0.15 ms

### OnePlus 5T
OnePlus OnePlus5T with Android 9.0
 
Library | Setup Kotlin | Setup Java | Inject Kotlin | Inject Java
--- | ---:| ---:| ---:| ---:
**Koin** | 0.45 ms | 0.47 ms  | 0.06 ms | 0.05 ms
**Kodein** | 5.21 ms | 5.30 ms  | 0.09 ms | 0.09 ms
**Katana** | 0.34 ms | 0.37 ms  | 0.04 ms | 0.04 ms
**Custom** | 0.08 ms | 0.08 ms  | 0.03 ms | 0.03 ms
**Dagger** | 0.00 ms | 0.00 ms  | 0.03 ms | 0.04 ms
