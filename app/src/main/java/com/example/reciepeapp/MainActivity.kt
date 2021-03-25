package com.example.reciepeapp

import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.reciepeapp.adapter.CategoryAdapter
import com.example.reciepeapp.adapter.SearchAdapter
import com.example.reciepeapp.client.Client
import com.example.reciepeapp.responses.categoryResponse.CategoriesItem
import com.example.reciepeapp.responses.searchResponse.MealsItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    val list = arrayListOf<CategoriesItem>()
    val categoryAdapter = CategoryAdapter(list)

    val list1 = arrayListOf<MealsItem>()
    val searchAdapter = SearchAdapter(list1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkConnection()
        setSupportActionBar(toolbar)

        categoryAdapter.onItemClick = {
            val intent = Intent(this@MainActivity,FoodActivity::class.java)
            intent.putExtra("idCategory",it.idCategory.toString())
            intent.putExtra("strCategoryThumb",it.strCategoryThumb.toString())
            intent.putExtra("strCategory",it.strCategory.toString())
            intent.putExtra("strCategoryDescription",it.strCategoryDescription.toString())
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
        mealrv.apply {
            layoutManager = GridLayoutManager(this@MainActivity,3, RecyclerView.VERTICAL,false)
            adapter = categoryAdapter
        }

        GlobalScope.launch(Dispatchers.Main) {
            val response = withContext(Dispatchers.IO){ Client.api.getAllCategories()}
            if (response.isSuccessful){
                response.body()?.let { res->
                    res.categories?.let {
                        list.addAll(it)
                    }
                    runOnUiThread{
                        categoryAdapter.notifyDataSetChanged()
                        progBar.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val item = menu?.findItem(R.id.search)
        val searchView = item?.actionView as androidx.appcompat.widget.SearchView

        searchView.queryHint = "search here..."
        searchView.setOnQueryTextListener(object :androidx.appcompat.widget.SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0?.length!! > 2){
                    loadJson(p0.toString())
                    searchAdapter.notifyDataSetChanged()
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                loadJson(p0.toString())
                searchAdapter.notifyDataSetChanged()
                return false
            }

        })
        item.icon.setVisible(false,false)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id:Int = item.itemId
        if (id == R.id.search){
            Toast.makeText(this,"search the meal here...", Toast.LENGTH_LONG).show()
        }
        return true
    }

    public fun loadJson(keyword:String){

        searchAdapter.notifyDataSetChanged()
        if (keyword.length > 2){

            mealrv.apply {
                layoutManager = GridLayoutManager(this@MainActivity,2,RecyclerView.VERTICAL,false)
                adapter = searchAdapter
            }
            searchAdapter.notifyDataSetChanged()

            searchAdapter.onItemClick = {
                Toast.makeText(this,"${it.strMeal}",Toast.LENGTH_SHORT).show()
                val intent = Intent(this,FoodDetailActivity::class.java)
                intent.putExtra("strCategory",it.strCategory.toString())
                intent.putExtra("strMeal",it.strMeal.toString())
                intent.putExtra("idMeal",it.idMeal.toString())
                intent.putExtra("strMealThumb",it.strMealThumb.toString())

                startActivity(intent)
            }

            GlobalScope.launch {
                val response = withContext(Dispatchers.IO){Client.api.getSearchResult("${keyword}")
                }
                if (response.isSuccessful){
                    response.body()?.let { res ->
                        res.meals?.let {
                            list.clear()
                            list1.addAll(it)
                            Log.i("abc", it.toString())
                        }
                        runOnUiThread { searchAdapter.notifyDataSetChanged() }
                    }
                }
            }
        }
        else {
//            mealrv.apply {
//                layoutManager = GridLayoutManager(this@MainActivity,3,RecyclerView.VERTICAL,false)
//                adapter = categoryAdapter
//            }

            searchAdapter.onItemClick = {
                Toast.makeText(this,"${it.strMeal}",Toast.LENGTH_SHORT).show()
                val intent = Intent(this,FoodDetailActivity::class.java)
                intent.putExtra("strCategory",it.strCategory.toString())
                intent.putExtra("strMeal",it.strMeal.toString())
                intent.putExtra("idMeal",it.idMeal.toString())
                intent.putExtra("strMealThumb",it.strMealThumb.toString())

                startActivity(intent)
            }
            getMeals()
        }
    }


}