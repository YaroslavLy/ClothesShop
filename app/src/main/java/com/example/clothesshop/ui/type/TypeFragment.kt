package com.example.clothesshop.ui.type

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clothesshop.R
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.clothesshop.model.Type
import com.example.clothesshop.ui.category.CategoryViewModel
import com.example.clothesshop.ui.category.CategoryViewModelFactory


class TypeFragment : Fragment() {
    private var columnCount = 3
    private lateinit var typeViewModel: TypeViewModel
    private var data = ArrayList<Type>()


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

        typeViewModel=
            ViewModelProvider(
                this,
                TypeViewModelFactory(type)
            )[TypeViewModel::class.java]
        //TypeViewModel(type)


        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, 3)
                }
                adapter = TypeRecyclerViewAdapter(data,view)
            }
        }

        //typeViewModel.getTypes()
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

    private fun updateUiWithProduct(product: Type, view: View) {
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