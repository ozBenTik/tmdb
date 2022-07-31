package com.example.tmdb

import android.app.Application
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.HiltAndroidApp
import di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import util.AppCoroutineDispatchers
import java.time.LocalDateTime
import javax.inject.Inject


@HiltAndroidApp
class MoviesApplication: Application() {

    var backgroundTime: LocalDateTime? = null

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

        ProcessLifecycleOwner.get().lifecycle.addObserver(object : LifecycleEventObserver{
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                when(event) {
                    Lifecycle.Event.ON_RESUME -> {

                        backgroundTime?.takeIf {
                            it.plusMinutes(5).isBefore(LocalDateTime.now())
                        }?.let {
                            applicationScope.launch(dispacher.io) {
                                firebaseAuth.signOut()
                            }
                        }
                        backgroundTime= null
                    }
                    Lifecycle.Event.ON_PAUSE -> {
                        backgroundTime = LocalDateTime.now()
                    }
                }
            }
        })
    }
}