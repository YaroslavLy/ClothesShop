package com.example.clothesshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        recyclerview.layoutManager = LinearLayoutManager(this)

        val data = ArrayList<ProductViewModel>()

        var listProductViewModel=ProvideData.getData()
        var mDataBase=FirebaseDatabase.getInstance().getReference("Products");
        //var listProductViewModel=ProvideData.getDataBD(mDataBase)

        for (item in listProductViewModel) {
            //data.add(ProductViewModel(R.drawable.ic_baseline_shop_24, "Item " + i))
            //data.add(item)
            //mDataBase.push().setValue(item)
        }
        val adapter = AdapterProduсt(data,this)
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (dataSn in dataSnapshot.children)
                {
                    var item = dataSn.getValue(ProductViewModel::class.java)
                    if (item != null) {
                        data.add(item)
                    }
                }
                adapter.notifyDataSetChanged()
            }
            override fun onCancelled(databaseError: DatabaseError) {

                Log.w("TAG", "loadPost:onCancelled", databaseError.toException())
            }
        }

        mDataBase.addValueEventListener(postListener)

        recyclerview.adapter = adapter
    }
}