package ru.example.driverestimation.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.example.driverestimation.R

class RegistrationFragment : Fragment() {

    companion object {
        fun newInstance(): RegistrationFragment {
            val args = Bundle()

            val fragment = RegistrationFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fr_registration, container, false)
    }

}