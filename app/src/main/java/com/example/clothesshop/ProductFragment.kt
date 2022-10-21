package com.example.clothesshop

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProductFragment: Fragment() {

    private var columnCount = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product_list, container, false)
        //val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        //recyclerview.layoutManager = LinearLayoutManager(this)

        val data = ArrayList<ProductViewModel>()

        //var listProductViewModel = ProvideData.getData()
        var mDataBase = FirebaseDatabase.getInstance().getReference("Products");
        //var listProductViewModel=ProvideData.getDataBD(mDataBase)

        //for (item in listProductViewModel) {
            //data.add(ProductViewModel(R.drawable.ic_baseline_shop_24, "Item " + i))
            //data.add(item)
            //mDataBase.push().setValue(item)
        //}
        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = AdapterProduсt(data)//MyItemRecyclerViewAdapter(PlaceholderContent.ITEMS)
            }
        }
        //adapter =

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (dataSn in dataSnapshot.children) {
                    var item = dataSn.getValue(ProductViewModel::class.java)
                    if (item != null) {
                        data.add(item)
                        Log.i("tag1",item.code.toString())
                    }
                }

                if (view is RecyclerView) {
                    with(view) {
                        adapter?.notifyDataSetChanged() //adapter = AdapterProduсt(data)//MyItemRecyclerViewAdapter(PlaceholderContent.ITEMS)
                    }
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {

                Log.w("TAG", "loadPost:onCancelled", databaseError.toException())
            }
        }

        mDataBase.addValueEventListener(postListener)

        //recyclerview.adapter = adapter

        return view
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