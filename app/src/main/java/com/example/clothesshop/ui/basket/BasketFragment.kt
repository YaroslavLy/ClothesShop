package com.example.clothesshop.ui.basket

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clothesshop.R
import com.example.clothesshop.model.ProductBasket
import com.example.clothesshop.model.Type
import com.example.clothesshop.ui.type.TypeFragmentArgs
import com.example.clothesshop.ui.type.TypeRecyclerViewAdapter
import com.example.clothesshop.ui.type.TypeViewModel
import com.example.clothesshop.ui.type.TypeViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class BasketFragment : Fragment() {

    private var columnCount = 3
    private lateinit var basketViewModel: BasketViewModel
    private var data = ArrayList<ProductBasket>()

    override fun onResume() {
        Log.i("tag99","Basket  Fragment LY")
        super.onResume()
        var bottomNavigationView =
            activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        if (bottomNavigationView != null) {
            var b2 = bottomNavigationView.menu.findItem(R.id.basket_menu)
            b2.isChecked = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var button =
            activity?.findViewById<Button>(R.id.button)
        var linearText =
            activity?.findViewById<LinearLayout>(R.id.linearLayoutTop)
        button?.visibility= View.VISIBLE
        linearText?.visibility= View.VISIBLE
//        arguments?.let {
//            columnCount = it.getInt(columnCount.toString())
//        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)//
        val view = inflater.inflate(R.layout.fragment_basket, container, false)
        //recyclerview.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val args: TypeFragmentArgs by navArgs()
//        val type =  args.arg


        basketViewModel=
            ViewModelProvider(
                this,
                BasketViewModelFactory()
            )[BasketViewModel::class.java]
        //TypeViewModel(type)

//        typeViewModel=
//            ViewModelProvider(
//                this,
//                TypeViewModelFactory(type)
//            )[TypeViewModel::class.java]

        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, 1)
                }
                adapter = BasketRecyclerViewAdapter(data,view)
            }
        }

        //typeViewModel.getTypes()
        basketViewModel.basketProductFormState.observe(viewLifecycleOwner, Observer {
                typeFormState ->
            if(typeFormState==null){
                return@Observer
            }
            typeFormState?.let {
                updateUiWithProduct(it,view)
            }

        })
    }

    private fun updateUiWithProduct(product: ProductBasket, view: View) {

        data.add(product)
        view.background = Color.WHITE.toDrawable()
        if (view is RecyclerView) {
            with(view) {
                adapter?.notifyDataSetChanged()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        var button =
            activity?.findViewById<Button>(R.id.button)
        var linearText =
            activity?.findViewById<LinearLayout>(R.id.linearLayoutTop)
        button?.visibility= View.GONE
        linearText?.visibility= View.GONE
    }

}














