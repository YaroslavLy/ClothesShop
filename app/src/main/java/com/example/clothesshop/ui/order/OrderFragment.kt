package com.example.clothesshop.ui.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.clothesshop.R
import com.example.clothesshop.databinding.FragmentBasketBinding
import com.example.clothesshop.databinding.FragmentOrderBinding


class OrderFragment : Fragment() {

    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.next.setOnClickListener {
            var sendText=""
            sendText = binding.name.text.toString() +"\n" + binding.number.text.toString()+"\n"+
                    binding.email.text.toString() +"\n" + binding.streetNumber.text.toString()+"\n"+
                    binding.postNumber.text.toString()+"\n"+binding.place.text.toString()

            val action = OrderFragmentDirections.actionOrderFragmentToOrderPaymentFragment(sendText)
            Navigation.findNavController(view).navigate(action)
            //findNavController().navigate(R.id.action_orderFragment_to_orderPaymentFragment)
        }
    }

}