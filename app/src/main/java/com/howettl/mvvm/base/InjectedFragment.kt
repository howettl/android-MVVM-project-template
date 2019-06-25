package com.howettl.mvvm.base

import android.content.Context
import androidx.fragment.app.Fragment
import com.howettl.mvvm.injection.ViewModelFactory
import com.howettl.mvvm.injection.component.DaggerFragmentInjector
import com.howettl.mvvm.injection.component.FragmentInjector
import com.howettl.mvvm.injection.module.DatabaseModule
import com.howettl.mvvm.injection.module.NetworkModule
import javax.inject.Inject

abstract class InjectedFragment : Fragment() {

    private lateinit var injector: FragmentInjector

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onAttach(context: Context) {
        super.onAttach(context)

        injector = DaggerFragmentInjector
            .builder()
            .context(context.applicationContext)
            .build()
            .also {
                it.inject(this@InjectedFragment)
            }
    }

}