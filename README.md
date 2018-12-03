# Android Injection Performance

This project aims to measure the performance of several Dependency Injection frameworks (or Service Locators) in different devices.

## Libraries tested
- [Koin](https://insert-koin.io/) - 2.0.0-alpha-3
- [Kodein](http://kodein.org/Kodein-DI/) - 5.3.0
- [Dagger 2](https://google.github.io/dagger/) - 2.16
- [Katana](https://github.com/rewe-digital-incubator/katana/) - 1.0.1

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

### Samsung Galaxy J5
samsung j5nlte with Android 6.0.1
 
Library | Setup Kotlin | Setup Java | Inject Kotlin | Inject Java
--- | ---:| ---:| ---:| ---:
**Koin** | 50,23 ms | 51,90 ms  | 2,47 ms | 2,46 ms
**Kodein** | 69,35 ms | 69,30 ms  | 8,47 ms | 8,39 ms
**Katana** | 11,48 ms | 11,42 ms  | 2,07 ms | 2,04 ms
**Custom** | 4,75 ms | 4,78 ms  | 0,70 ms | 0,84 ms
**Dagger** | 0,02 ms | 0,02 ms  | 0,25 ms | 0,22 ms

### Samsung Galaxy S8
samsung dreamlte with Android 8.0.0
 
Library | Setup Kotlin | Setup Java | Inject Kotlin | Inject Java
--- | ---:| ---:| ---:| ---:
**Koin** | 4,32 ms | 4,55 ms  | 0,08 ms | 0,08 ms
**Kodein** | 5,28 ms | 5,50 ms  | 0,14 ms | 0,13 ms
**Katana** | 0,47 ms | 0,42 ms  | 0,07 ms | 0,09 ms
**Custom** | 0,11 ms | 0,12 ms  | 0,05 ms | 0,05 ms
**Dagger** | 0,01 ms | 0,01 ms  | 0,04 ms | 0,04 ms

### Huawei P8 Lite
Huawei hwALE-H with Android 6.0
 
Library | Setup Kotlin | Setup Java | Inject Kotlin | Inject Java
--- | ---:| ---:| ---:| ---:
**Koin** | 11,28 ms | 12,22 ms  | 0,25 ms | 0,25 ms
**Kodein** | 64,97 ms | 64,91 ms  | 7,58 ms | 7,59 ms
**Katana** | 10,37 ms | 10,43 ms  | 1,93 ms | 1,90 ms
**Custom** | 4,32 ms | 4,32 ms  | 0,65 ms | 0,80 ms
**Dagger** | 0,01 ms | 0,01 ms  | 0,23 ms | 0,20 ms

### Xiaomi MI A1
xiaomi tissot_sprout with Android 8.1.0
 
Library | Setup Kotlin | Setup Java | Inject Kotlin | Inject Java
--- | ---:| ---:| ---:| ---:
**Koin** | 6,53 ms | 6,77 ms  | 0,15 ms | 0,13 ms
**Kodein** | 11,18 ms | 10,95 ms  | 0,29 ms | 0,26 ms
**Katana** | 0,97 ms | 0,90 ms  | 0,13 ms | 0,13 ms
**Custom** | 0,24 ms | 0,24 ms  | 0,08 ms | 0,10 ms
**Dagger** | 0,01 ms | 0,01 ms  | 0,09 ms | 0,05 ms

### OnePlus One
oneplus A0001 with Android 5.0.2
 
Library | Setup Kotlin | Setup Java | Inject Kotlin | Inject Java
--- | ---:| ---:| ---:| ---:
**Koin** | 10,56 ms | 11,32 ms  | 0,29 ms | 0,28 ms
**Kodein** | 78,66 ms | 73,30 ms  | 8,79 ms | 7,02 ms
**Katana** | 12,08 ms | 9,38 ms  | 2,02 ms | 2,02 ms
**Custom** | 4,27 ms | 4,32 ms  | 0,70 ms | 0,92 ms
**Dagger** | 0,02 ms | 0,02 ms  | 0,22 ms | 0,19 ms

### OnePlus 5
OnePlus OnePlus5 with Android 8.1.0
 
Library | Setup Kotlin | Setup Java | Inject Kotlin | Inject Java
--- | ---:| ---:| ---:| ---:
**Koin** | 2,94 ms | 3,00 ms  | 0,06 ms | 0,06 ms
**Kodein** | 4,78 ms | 4,66 ms  | 0,11 ms | 0,10 ms
**Katana** | 0,33 ms | 0,32 ms  | 0,06 ms | 0,07 ms
**Custom** | 0,08 ms | 0,08 ms  | 0,04 ms | 0,04 ms
**Dagger** | 0,01 ms | 0,01 ms  | 0,05 ms | 0,04 ms

### Nexus 6
google shamu with Android 7.1.1
 
Library | Setup Kotlin | Setup Java | Inject Kotlin | Inject Java
--- | ---:| ---:| ---:| ---:
**Koin** | 17,89 ms | 18,23 ms  | 0,39 ms | 0,31 ms
**Kodein** | 17,48 ms | 15,95 ms  | 0,60 ms | 0,47 ms
**Katana** | 0,97 ms | 0,97 ms  | 0,54 ms | 0,59 ms
**Custom** | 0,20 ms | 0,21 ms  | 0,20 ms | 0,20 ms
**Dagger** | 0,02 ms | 0,02 ms  | 0,22 ms | 0,13 ms
