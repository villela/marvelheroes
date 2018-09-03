package com.matheusvillela.marvelheroes

import android.app.Application
import com.matheusvillela.marvelheroes.di.AppModule
import timber.log.Timber
import toothpick.Scope
import toothpick.Toothpick
import toothpick.configuration.Configuration
import toothpick.registries.FactoryRegistryLocator
import toothpick.registries.MemberInjectorRegistryLocator

class MarvelHeroesApp : Application() {
    override fun onCreate() {
        super.onCreate()

        Toothpick.setConfiguration(Configuration.forProduction().disableReflection())
        MemberInjectorRegistryLocator.setRootRegistry(com.matheusvillela.marvelheroes.MemberInjectorRegistry())
        FactoryRegistryLocator.setRootRegistry(com.matheusvillela.marvelheroes.FactoryRegistry())

        val appScope: Scope = Toothpick.openScope(this)

        appScope.installModules(AppModule(this))
        Toothpick.inject(this, appScope)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}