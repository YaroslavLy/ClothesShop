package com.example.clothesshop.ui.type

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.clothesshop.R



class TypeRecyclerViewAdapter(private val mList: List<TypeView>) :
    RecyclerView.Adapter<TypeRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_type, parent, false)

        return TypeRecyclerViewAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]

        holder.textName.text=ItemsViewModel.name
        DownloadImageFromInternet(holder.imageView).execute(ItemsViewModel.url_image)
//        holder.linearLayout.setOnClickListener{
//            Log.i("tag","Hice")
//        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    @SuppressLint("StaticFieldLeak")
    @Suppress("DEPRECATION")
    private inner class DownloadImageFromInternet(var imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
//        init {
//            //Toast.makeText(context, "Please wait, it may take a few minute...",     Toast.LENGTH_SHORT).show()
//        }
        override fun doInBackground(vararg urls: String): Bitmap? {
            val imageURL = urls[0]
            var image: Bitmap? = null
            try {
                val `in` = java.net.URL(imageURL).openStream()
                image = BitmapFactory.decodeStream(`in`)
            }
            catch (e: Exception) {
                Log.e("Error Message", e.message.toString())
                e.printStackTrace()
            }
            return image
        }
        override fun onPostExecute(result: Bitmap?) {
            imageView.setImageBitmap(result)
        }
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageViewType)
        val textName: TextView = itemView.findViewById(R.id.textViewType)
    }

}

