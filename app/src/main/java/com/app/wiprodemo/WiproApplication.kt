package com.app.wiprodemo

import android.app.Application
import com.app.wiprodemo.data.db.AppDatabase
import com.app.wiprodemo.data.network.MyApi
import com.app.wiprodemo.data.network.NetworkConnectionInterceptor
import com.app.wiprodemo.data.repositories.PostRepository
import com.app.wiprodemo.ui.posts.PostsViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class WiproApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@WiproApplication))
        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { PostRepository(instance(), instance()) }
        bind() from provider{ PostsViewModelFactory(instance()) }
    }
}