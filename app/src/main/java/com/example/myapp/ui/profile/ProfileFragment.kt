package com.example.myapp.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapp.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import android.annotation.SuppressLint
import android.content.Intent
import com.example.myapp.LoginActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    private lateinit var auth: FirebaseAuth

    private val PREFS_KEY_IS_LOGGED_IN = "isLoggedIn"

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //---------------------------//

        auth = FirebaseAuth.getInstance()
        val prefs = requireContext().getSharedPreferences(requireContext().packageName, Context.MODE_PRIVATE)
        binding.singOutBtn.setOnClickListener{
            val editor = requireContext().getSharedPreferences(requireContext().packageName, Context.MODE_PRIVATE).edit()
            editor.putBoolean(PREFS_KEY_IS_LOGGED_IN, false)
            editor.apply()

            auth.signOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }


        val storePhone = requireContext().getSharedPreferences(requireContext().packageName, Context.MODE_PRIVATE)
        val email = storePhone.getString("email", "")
        val displayName = storePhone.getString("name", "")
        binding.textNotifications.text = "$email \n $displayName"

        //-----------------------------//

//        val textView: TextView = binding.textNotifications
//        profileViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}