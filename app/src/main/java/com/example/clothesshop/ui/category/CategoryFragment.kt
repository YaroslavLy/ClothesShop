package com.example.clothesshop.ui.category

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.clothesshop.R
import com.example.clothesshop.databinding.FragmentCategoryBinding
import com.example.clothesshop.model.Category
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers



class CategoryFragment : Fragment() {

    private val uiScope = CoroutineScope(Dispatchers.Main)
    private var count=0

    private lateinit var categoryViewModel: CategoryViewModel
    private var listCategory = mutableListOf<Category>()
    private var _binding: FragmentCategoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryViewModel =
            ViewModelProvider(
                this,
                CategoryViewModelFactory()
            )[CategoryViewModel::class.java]

        //CategoryViewModel("Categories")
        //categoryViewModel.getCategories()

        categoryViewModel.categoryFormState.observe(viewLifecycleOwner, Observer {
            categoryFromState ->
            if(categoryFromState == null)
                return@Observer

            categoryFromState?.let {
                listCategory.add(it)

                updateUiWithCategories(view)
            }
        })

    }


    private fun updateUiWithCategories(view: View)
    {
        var categoryImage: ImageView? = null
        var categoryText: TextView? = null
        var category : RelativeLayout? = null
        var folder: String?= ""
        // TODO if add category replace to recyclerView
        when (count) {
            0 -> {
                categoryImage = binding.myImageView
                categoryText = binding.myImageViewText
                category = binding.category1
                folder=listCategory[count].name_folder
            }
            1 -> {
                categoryImage = binding.myImageView2
                categoryText = binding.myImageViewText2
                category = binding.category2
                folder=listCategory[count].name_folder
            }
            2 -> {
                categoryImage = binding.myImageView3
                categoryText = binding.myImageViewText3
                category = binding.category3
                folder=listCategory[count].name_folder

            }
            3 -> {
                categoryImage = binding.myImageView4
                categoryText = binding.myImageViewText4
                category = binding.category4
                folder=listCategory[count].name_folder

            }
            4 -> {
                categoryImage = binding.myImageView5
                categoryText = binding.myImageViewText5
                category = binding.category5
                folder=listCategory[count].name_folder
            }
        }

        if (categoryImage != null) {
            DownloadImageFromInternet(categoryImage).execute(listCategory[count].url_image)
        }
        categoryText?.text = listCategory[count].name_pl
        category?.setOnClickListener {
            val action = folder?.let { it1 ->
                CategoryFragmentDirections.actionCategoryFragmentToTypeFragment(
                    it1
                )
            }
            if (action != null) {
                Navigation.findNavController(view)
                    .navigate(action)
            }

               // .navigate(R.id.action_categoryFragment_to_typeFragment)
        }


        count++
    }

    @SuppressLint("StaticFieldLeak")
    @Suppress("DEPRECATION")
    private inner class DownloadImageFromInternet(var imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
        init {
            //Toast.makeText(context, "Please wait, it may take a few minute...",     Toast.LENGTH_SHORT).show()
        }
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



}