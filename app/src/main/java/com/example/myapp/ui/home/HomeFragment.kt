package com.example.myapp.ui.home


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.myapp.Item
import com.example.myapp.MainActivity
import com.example.myapp.MainDb
import com.example.myapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }



        val db = MainDb.getDb(requireActivity())

        db.getDao().getAllItems().asLiveData().observe(requireActivity()){
            binding.textHome.text = ""
            it.forEach{
                val text = "id: ${it.id} Name: ${it.name} Price: ${it.price} \n"
                binding.textHome.append(text)
            }
        }

        binding.button2.setOnClickListener{
            var item = Item(
                null,
                binding.addName.text.toString(),
                binding.addPrice.text.toString(),
            )

            Thread{
                db.getDao().insertItem(item)
            }.start()
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}