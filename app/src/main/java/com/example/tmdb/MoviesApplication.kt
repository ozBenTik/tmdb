package com.example.tmdb

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.HiltAndroidApp
import di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import util.AppCoroutineDispatchers
import javax.inject.Inject


@HiltAndroidApp
class MoviesApplication : Application() {

    private var backgroundTime: Long
        set(value) {
            getSharedPreferences("appGlobal", Context.MODE_PRIVATE)?.let {
                it.edit {
                    putLong("backgroundTimeStart", value)
                }
            }
        }
        get() = getSharedPreferences("appGlobal", Context.MODE_PRIVATE)
            .getLong("backgroundTimeStart", 0L)

    @Inject
    @ApplicationScope
    lateinit var applicationScope: CoroutineScope

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var dispacher: AppCoroutineDispatchers

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        ProcessLifecycleOwner
            .get()
            .lifecycle
            .addObserver(object : LifecycleEventObserver {
                override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                    when (event) {
                        Lifecycle.Event.ON_RESUME -> {
                            backgroundTime.takeIf {
                                (it > 0) && ((it + 300000) > System.currentTimeMillis())
                            }?.let {
                                applicationScope.launch(dispacher.io) {
                                    firebaseAuth.signOut()
                                }
                            }
                            backgroundTime = 0L
                        }
                        Lifecycle.Event.ON_PAUSE -> {
                            backgroundTime = System.currentTimeMillis()
                        }
                    }
                }
            })
    }
}