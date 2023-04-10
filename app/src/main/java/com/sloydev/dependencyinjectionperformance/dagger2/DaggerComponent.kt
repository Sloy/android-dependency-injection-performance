package com.sloydev.dependencyinjectionperformance.dagger2

import com.sloydev.dependencyinjectionperformance.InjectionTest
import dagger.Component

@Component(modules = [KotlinDaggerModule::class])
interface KotlinDaggerComponent {
    fun inject(injectionTest: InjectionTest.KotlinDaggerTest)
}
