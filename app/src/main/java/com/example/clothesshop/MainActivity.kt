package com.example.clothesshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ProductViewModel>()

        // This loop will create 20 Views containing
        // the image with the count of view
        var listProductViewModel=ProvideData.getData()

        for (item in listProductViewModel) {
            //data.add(ProductViewModel(R.drawable.ic_baseline_shop_24, "Item " + i))
            data.add(item)
        }

        // This will pass the ArrayList to our Adapter
        val adapter = AdapterProduсt(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
    }
}