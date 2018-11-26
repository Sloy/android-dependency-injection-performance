package com.sloydev.dependencyinjectionperformance.custom

import kotlin.reflect.KClass

data class DefinitionKey<T>(val clazz: Class<T>, val name: String?)

sealed class Definition<T>(val key: DefinitionKey<T>, val create: () -> T) {

    class Factory<T>(key: DefinitionKey<T>, create: () -> T) : Definition<T>(key, create)

    class Singleton<T>(key: DefinitionKey<T>, create: () -> T) : Definition<T>(key, create)
}

class Module(val definitions: List<Definition<*>>)

class ModuleBuilder(val definitions: MutableList<Definition<*>>) {
    inline fun <reified T> factory(name: String? = null, noinline block: () -> T): Definition.Factory<T> {
        val factory = Definition.Factory(DefinitionKey(T::class.java, name), block)
        definitions += factory
        return factory
    }

    inline fun <reified T> single(name: String? = null, noinline block: () -> T): Definition.Singleton<T> {
        val singleton = Definition.Singleton(DefinitionKey(T::class.java, name), block)
        definitions += singleton
        return singleton
    }

    inline fun <reified T : Any> get(name: String? = null) = DIContainer.get<T>(name)

    inline infix fun <I : Any, reified C : I> Definition<C>.bind(iface: KClass<I>) {
        definitions += Definition.Factory(DefinitionKey(iface.java, null)) { get<C>(key.name) }
    }
}

fun module(block: ModuleBuilder.() -> Unit): Module {
    val builder = ModuleBuilder(arrayListOf())
    builder.block()
    return Module(builder.definitions)
}

object DIContainer {

    private val definitions: MutableMap<DefinitionKey<*>, Definition<*>> = HashMap()
    private val singletons: MutableMap<DefinitionKey<*>, Any> = HashMap()

    fun loadModules(modules: List<Module>) {
        modules.forEach { loadModule(it) }
    }

    fun loadModule(module: Module) {
        module.definitions.forEach {
            definitions[it.key] = it
            singletons.remove(it.key)
        }
    }

    fun unloadModules() {
        definitions.clear()
        singletons.clear()
    }

    inline fun <reified T : Any> get(name: String? = null) = get(T::class.java, name)

    inline fun <reified T : Any> inject(name: String? = null) = inject(T::class.java, name)

    @JvmStatic
    @JvmOverloads
    fun <T : Any> get(clazz: Class<T>, name: String? = null): T {
        val key = DefinitionKey(clazz, name)
        val singleton = singletons[key]
        if (singleton != null) {
            return singleton as T
        }

        val definition: Definition<T>? = definitions[key] as Definition<T>?
        if (definition != null) {
            val instance: T = definition.create()
            if (definition is Definition.Singleton) {
                singletons[key] = instance
            }
            return instance
        } else {
            throw IllegalStateException("No definition found for ${clazz.canonicalName} with name $name")
        }
    }

    @JvmStatic
    @JvmOverloads
    fun <T : Any> inject(clazz: Class<T>, name: String? = null): Lazy<T> {
        return lazy { get(clazz, name) }
    }
}