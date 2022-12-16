package com.example.clothesshop.ui.type

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clothesshop.R
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.clothesshop.databinding.FragmentTypeListBinding
import com.example.clothesshop.utils.Constants
import com.example.clothesshop.utils.Constants.KEY_FOLDER
import com.example.clothesshop.model.Type


class TypeFragment : Fragment() {

    private var _binding: FragmentTypeListBinding ? = null
    private val binding get() = _binding!!

    private lateinit var typeRecyclerViewAdapter: TypeRecyclerViewAdapter

    private lateinit var typeViewModel: TypeViewModel
    private var data = ArrayList<Type>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)//
        _binding = FragmentTypeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // todo replace to true way read argument
        val sharedPreferences = context?.getSharedPreferences(Constants.SHARED_PREFS_CATEGORY, Context.MODE_PRIVATE)
        val type = sharedPreferences?.getString(KEY_FOLDER,"all") ?:"all"// "all"// args.arg
        Log.i("Test0112",type.toString())

        typeViewModel=
            ViewModelProvider(
                this,
                TypeViewModelFactory(type)
            )[TypeViewModel::class.java]


        binding.list.layoutManager = GridLayoutManager(context, 3)
        typeRecyclerViewAdapter = TypeRecyclerViewAdapter(object : TypeItemActionListener{
            override fun onTypeClick(type: Type) {
                findNavController().navigate(R.id.action_typeFragment2_to_productFragment3)
            }
        })
        binding.list.adapter = typeRecyclerViewAdapter

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

    private fun updateUiWithProduct(product: Type, view: View) {
        if(!data.contains(product))
            data.add(product)
        typeRecyclerViewAdapter.typesList = data.toMutableList()
        if (view is RecyclerView) {
            with(view) {
                adapter?.notifyDataSetChanged()
            }
        }
    }


}