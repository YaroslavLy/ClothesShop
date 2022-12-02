package com.example.clothesshop.ui.basket

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clothesshop.databinding.FragmentBasketBinding
import com.example.clothesshop.model.ProductBasket


class BasketFragment : Fragment() {
    private lateinit var basketViewModel: BasketViewModel
    private var data = ArrayList<ProductBasket>()

    private lateinit var adapter: BasketRecyclerViewAdapter

    private var _binding: FragmentBasketBinding? = null
    private val binding get() = _binding!!

    override fun onResume() {
        super.onResume()
        basketViewModel.getProductsFromBasket()
        updateListData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        _binding = FragmentBasketBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        basketViewModel =
            ViewModelProvider(
                this,
                BasketViewModelFactory()
            )[BasketViewModel::class.java]

        binding.listProductBasket.layoutManager =
            LinearLayoutManager(context)
        adapter = BasketRecyclerViewAdapter(object : ProductBasketActionListener {
            override fun onProductDelete(productBasket: ProductBasket) {
                basketViewModel.removeProductFromBasket(productBasket)
                data.remove(productBasket)
                updateListData()
            }

            override fun onUserDetails(productBasket: ProductBasket) {
                val action = productBasket.code?.let { it1 ->
                    BasketFragmentDirections.actionBasketFragment2ToProductDetailsFragment2(it1)
                }
                if (action != null) {
                    Navigation.findNavController(view).navigate(action)
                }
            }
        })

        binding.listProductBasket.adapter = adapter

        basketViewModel.getProductsFromBasket()
        basketViewModel.basketProductFormState.observe(
            viewLifecycleOwner,
            Observer { productBasket ->
                if (productBasket == null) {
                    return@Observer
                }
                updateUiWithProduct(productBasket)
            })
    }



    private fun updateListData() {
        adapter.productsBasket = data.toMutableList()
    }

    private fun updateUiWithProduct(product: ProductBasket) {
        if (!containBasketProduct(product)) {
            data.add(product)
            updateListData()
        }
        binding.listProductBasket.background = Color.WHITE.toDrawable()

    }

    private fun containBasketProduct(product: ProductBasket): Boolean {
        for (pr in data) {
            if (pr.code.equals(product.code)) {
                return true
            }
        }
        return false
    }


}














