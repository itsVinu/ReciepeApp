package com.example.reciepeapp

import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.reciepeapp.adapter.CategoryDetailAdapter
import com.example.reciepeapp.client.Client
import com.example.reciepeapp.responses.categoryDetailResponse.MealsItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_food.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FoodActivity : AppCompatActivity() {
    val list = arrayListOf<MealsItem>()
    val catDetailAdapter = CategoryDetailAdapter(list)
    lateinit var strCategory:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        val idCategory =  intent.getStringExtra("idCategory")!!
        val strCategoryDescription = intent.getStringExtra("strCategoryDescription")
        strCategory = intent.getStringExtra("strCategory")!!
        val strCategoryThumb = intent.getStringExtra("strCategoryThumb")

        checkConnection()

        Picasso.get().load(strCategoryThumb).into(backdrop)
        title_on_appbar.setText(strCategory)
//        setSupportActionBar(toolbar)

        detailRv.apply {
            layoutManager = GridLayoutManager(this@FoodActivity,3, RecyclerView.VERTICAL,false)
            adapter = catDetailAdapter
        }

        catDetailAdapter.onItemClick = {
            Toast.makeText(this,"${it.strMeal}", Toast.LENGTH_SHORT).show()
            val intent = Intent(this,FoodDetailActivity::class.java)
            intent.putExtra("strCategory",strCategory.toString())
            intent.putExtra("strMeal",it.strMeal.toString())
            intent.putExtra("idMeal",it.idMeal.toString())
            intent.putExtra("strMealThumb",it.strMealThumb.toString())

            startActivity(intent)
        }
    }

    private fun checkConnection() {
        var connectivityManager = this.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
        var networkInfo = connectivityManager.getActiveNetworkInfo()

        if (networkInfo != null && networkInfo.isConnected){
            getMeals()
        }
        else if(networkInfo == null){
            AlertDialog.Builder(this)
                .setTitle("RETRY")
                .setMessage("NO INTERNET CONNECTION")
                .setPositiveButton("Retry",{ dialogInterface: DialogInterface, i: Int -> checkConnection() })
                .show()
        }
    }

    private fun getMeals() {
        GlobalScope.launch {
            val response = withContext(Dispatchers.IO) { Client.api.getCategoryDetail("${strCategory}")}
            if (response.isSuccessful){
                response.body().let { res->
                    res?.meals?.let {
                        list.addAll(it)
                    }
                    runOnUiThread {
//                        progress.visibility = View.GONE
                        catDetailAdapter.notifyDataSetChanged()
                        progress.visibility = View.GONE
                    }
                }
            }
        }
    }
}