package com.example.clothesshop.ui.product

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clothesshop.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.clothesshop.model.Product
import com.example.clothesshop.ui.category.CategoryViewModel
import com.example.clothesshop.ui.category.CategoryViewModelFactory

class ProductFragment: Fragment() {

    private var columnCount = 2
    private lateinit var productViewModel: ProductViewModel
    private var data = ArrayList<Product>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_product_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId== R.id.menu_logout)
        {
            Firebase.auth.signOut()
            this.view?.let { Navigation.findNavController(it).navigate(R.id.action_productFragment_to_loginFragment2) }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)//
        val view = inflater.inflate(R.layout.fragment_product_list, container, false)
        //recyclerview.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)//ProductViewModel("Products")

        productViewModel=
            ViewModelProvider(
                this,
                ProductViewModelFactory()
            )[ProductViewModel::class.java]

        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = ProductRecyclerViewAdapter(data)
            }
        }

        //productViewModel.getProducts()
        productViewModel.productFormState.observe(viewLifecycleOwner, Observer {
                productFormState ->
            if(productFormState==null){
                return@Observer
            }
            productFormState?.let {
                updateUiWithProduct(it,view)
            }

        })
    }

    private fun updateUiWithProduct(product: Product, view: View) {
        data.add(product)

        if (view is RecyclerView) {
            with(view) {
                adapter?.notifyDataSetChanged()
            }
        }
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            ProductFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}