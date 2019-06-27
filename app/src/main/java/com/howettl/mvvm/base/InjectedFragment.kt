package com.howettl.mvvm.base

import android.content.Context
import androidx.fragment.app.Fragment
import com.howettl.mvvm.injection.ViewModelFactory
import com.howettl.mvvm.injection.component.ApplicationComponent
import javax.inject.Inject

abstract class InjectedFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onAttach(context: Context) {
        super.onAttach(context)

        inject(getApplicationComponent() ?: return)
    }

    abstract fun inject(component: ApplicationComponent)

    private fun getApplicationComponent(): ApplicationComponent? =
        (activity?.application as? BaseApplication)?.applicationComponent

}