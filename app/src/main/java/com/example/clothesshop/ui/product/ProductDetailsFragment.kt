package com.example.clothesshop.ui.product

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.clothesshop.GlideApp
import com.example.clothesshop.R
import com.example.clothesshop.data.ProductRepository
import com.example.clothesshop.databinding.FragmentProductDetailsBinding
import com.example.clothesshop.model.Product


class ProductDetailsFragment:Fragment(R.layout.fragment_product_details) {

    private var _binding:FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProductDetailsBinding.inflate(inflater,container,false);
        val view = binding.root;
        return view;
    }

    lateinit var type: String
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: ProductDetailsFragmentArgs by navArgs()
        type =  args.id

        // TODO replace
        var viewModel = ProductViewModel(productRepository = ProductRepository(""))


        viewModel.getProduct()
        viewModel.productFormState.observe(viewLifecycleOwner, Observer {
                typeFormState ->
            if(typeFormState==null){
                return@Observer
            }
            typeFormState?.let {
                updateUiWithProduct(it,view)
            }

        })

        //todo
        //binding.details.text=args.id

    }

    private fun updateUiWithProduct(product: Product, view: View) {
    Log.i("Ok22",product.code.toString())
        if(product.code.equals(type))
        {
            GlideApp.with(binding.imageview.context)
                .load(product.image)
                .error(R.drawable.ic_baseline_autorenew_24)
                .into(binding.imageview)
            binding.textName.text=product.name
            var text = product.description?.replace(";","\n--------------------------------------------------------------\n")
            binding.details.text=text
        }
    }
}