package com.example.clothesshop.ui.type

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clothesshop.R
import com.example.clothesshop.ui.product.ProductFragment
import com.example.clothesshop.ui.product.ProductRecyclerViewAdapter
import com.example.clothesshop.ui.product.ProductView
import com.example.clothesshop.ui.product.ProductViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs


class TypeFragment : Fragment() {
    private var columnCount = 3
    private lateinit var typeViewModel: TypeViewModel
    private var data = ArrayList<TypeView>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        arguments?.let {
//            columnCount = it.getInt(columnCount.toString())
//        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)//
        val view = inflater.inflate(R.layout.fragment_type_list, container, false)
        //recyclerview.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: TypeFragmentArgs by navArgs()
        val type =  args.arg

        typeViewModel= TypeViewModel("Categories/Types/$type")


        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, 3)
                }
                adapter = TypeRecyclerViewAdapter(data)
            }
        }

        typeViewModel.getTypes()
        typeViewModel.typeFormState.observe(viewLifecycleOwner, Observer {
                typeFormState ->
            if(typeFormState==null){
                return@Observer
            }
            typeFormState?.let {
                updateUiWithProduct(it,view)
            }

        })
    }

    private fun updateUiWithProduct(product: TypeView, view: View) {
        data.add(product)

        if (view is RecyclerView) {
            with(view) {
                adapter?.notifyDataSetChanged()
            }
        }
    }

//    companion object {
//
//        // TODO: Customize parameter argument names
//        const val ARG_COLUMN_COUNT = "column-count"
//
//        // TODO: Customize parameter initialization
//        @JvmStatic
//        fun newInstance(columnCount: Int) =
//            ProductFragment().apply {
//                arguments = Bundle().apply {
//                    putInt(ARG_COLUMN_COUNT, columnCount)
//                }
//            }
//    }
}