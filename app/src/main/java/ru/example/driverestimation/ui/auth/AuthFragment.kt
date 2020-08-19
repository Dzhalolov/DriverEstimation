package ru.example.driverestimation.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fr_auth.*
import ru.example.driverestimation.R
import ru.example.driverestimation.ui.personalArea.PersonalAreaActivity

class AuthFragment : Fragment() {

    companion object {
        fun newInstance(): AuthFragment {
            val args = Bundle()

            val fragment = AuthFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fr_auth, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_sign_in.setOnClickListener {
            if (isValidData()) {
                val startPersonalAreaActivity = Intent(activity, PersonalAreaActivity::class.java)
                startActivity(startPersonalAreaActivity)
                activity?.finish()
            }
        }

        btn_sign_up.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.auth_container, RegistrationFragment.newInstance())
                ?.addToBackStack(RegistrationFragment::class.java.name)
                ?.commit()
        }
    }

    private fun isValidData(): Boolean {
        return true
    }

}