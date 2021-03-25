package com.example.reciepeapp

import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract.CalendarCache.URI
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.reciepeapp.client.Client
import com.example.reciepeapp.responses.foodDetailResponse.MealsItem
import com.google.gson.internal.bind.TypeAdapters.URI
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_food.*
import kotlinx.android.synthetic.main.activity_food.backdrop
import kotlinx.android.synthetic.main.activity_food.title_on_appbar
import kotlinx.android.synthetic.main.activity_food_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FoodDetailActivity : AppCompatActivity() {
    val list = arrayListOf<MealsItem>()
    lateinit var idMeal:String
    lateinit var strYoutube:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_detail)

        checkConnection()

        val strCategory = intent.getStringExtra("strCategory")
        val strMeal = intent.getStringExtra("strMeal")
        idMeal = intent.getStringExtra("idMeal")!!
        val strMealThumb = intent.getStringExtra("strMealThumb")

        Picasso.get().load(strMealThumb).into(backdrop)
        title_on_appbar.setText(strMeal)
//        setSupportActionBar(toolbar)

        btnYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(strYoutube))
            intent.putExtra("strYoutube",strYoutube)

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
            val response = withContext(Dispatchers.IO){ Client.api.getFoodDetail(idMeal!!)}
            if (response.isSuccessful){
                response.body()?.let {res->
                    res.let {
                        strYoutube = it.meals?.get(0)?.strYoutube.toString()
                        runOnUiThread {
                            progressbar.visibility = View.GONE
                            instTxt.text = measureTxt.text.toString() +  it.meals?.get(0)?.strInstructions.toString()

                            if (it.meals?.get(0)?.strIngredient1!=""){
                                ingridientTxt.text =  it.meals?.get(0)?.strIngredient1.toString() + "\n"
                                measureTxt.text = measureTxt.text.toString() +  it.meals?.get(0)?.strMeasure1.toString() + "\n"
                            }
                            if (it.meals?.get(0)?.strIngredient1!=""){
                                ingridientTxt.text = ingridientTxt.text.toString() + it.meals?.get(0)?.strIngredient2.toString() + "\n"
                                measureTxt.text = measureTxt.text.toString() +  it.meals?.get(0)?.strMeasure2.toString() + "\n"
                            }
                            if (it.meals?.get(0)?.strIngredient1!=""){
                                ingridientTxt.text =  ingridientTxt.text.toString() + it.meals?.get(0)?.strIngredient3.toString() + "\n"
                                measureTxt.text = measureTxt.text.toString() +  it.meals?.get(0)?.strMeasure3.toString() + "\n"
                            }
                            if (it.meals?.get(0)?.strIngredient1!=""){
                                ingridientTxt.text = ingridientTxt.text.toString() + it.meals?.get(0)?.strIngredient4.toString() + "\n"
                                measureTxt.text = measureTxt.text.toString() +  it.meals?.get(0)?.strMeasure4.toString() + "\n"
                            }
                            if (it.meals?.get(0)?.strIngredient1!=""){
                                ingridientTxt.text =  it.meals?.get(0)?.strIngredient5.toString() + "\n"
                                measureTxt.text = measureTxt.text.toString() +  it.meals?.get(0)?.strMeasure1.toString() + "\n"
                            }
                            if (it.meals?.get(0)?.strIngredient1!=""){
                                ingridientTxt.text = ingridientTxt.text.toString() + it.meals?.get(0)?.strIngredient6.toString() + "\n"
                                measureTxt.text = measureTxt.text.toString() +  it.meals?.get(0)?.strMeasure6.toString() + "\n"
                            }
                            if (it.meals?.get(0)?.strIngredient1!=""){
                                ingridientTxt.text = ingridientTxt.text.toString() + it.meals?.get(0)?.strIngredient7.toString() + "\n"
                                measureTxt.text = measureTxt.text.toString() +  it.meals?.get(0)?.strMeasure7.toString() + "\n"
                            }
                            if (it.meals?.get(0)?.strIngredient1!=""){
                                ingridientTxt.text = ingridientTxt.text.toString() + it.meals?.get(0)?.strIngredient8.toString() + "\n"
                                measureTxt.text = measureTxt.text.toString() +  it.meals?.get(0)?.strMeasure8.toString() + "\n"
                            }
                            if (it.meals?.get(0)?.strIngredient1!=""){
                                ingridientTxt.text = ingridientTxt.text.toString() + it.meals?.get(0)?.strIngredient9.toString() + "\n"
                                measureTxt.text = measureTxt.text.toString() +  it.meals?.get(0)?.strMeasure9.toString() + "\n"
                            }
                            if (it.meals?.get(0)?.strIngredient1!=""){
                                ingridientTxt.text = ingridientTxt.text.toString() + it.meals?.get(0)?.strIngredient10.toString() + "\n"
                                measureTxt.text = measureTxt.text.toString() +  it.meals?.get(0)?.strMeasure10.toString() + "\n"
                            }
                            if (it.meals?.get(0)?.strIngredient1!=""){
                                ingridientTxt.text = ingridientTxt.text.toString() + it.meals?.get(0)?.strIngredient11.toString() + "\n"
                                measureTxt.text = measureTxt.text.toString() +  it.meals?.get(0)?.strMeasure11.toString() + "\n"
                            }
                            if (it.meals?.get(0)?.strIngredient1!=""){
                                ingridientTxt.text = ingridientTxt.text.toString() + it.meals?.get(0)?.strIngredient12.toString() + "\n"
                                measureTxt.text = measureTxt.text.toString() +  it.meals?.get(0)?.strMeasure12.toString() + "\n"
                            }
                            if (it.meals?.get(0)?.strIngredient1!=""){
                                ingridientTxt.text = ingridientTxt.text.toString() + it.meals?.get(0)?.strIngredient13.toString() + "\n"
                                measureTxt.text = measureTxt.text.toString() +  it.meals?.get(0)?.strMeasure13.toString() + "\n"
                            }
                            if (it.meals?.get(0)?.strIngredient1!=""){
                                ingridientTxt.text = ingridientTxt.text.toString() + it.meals?.get(0)?.strIngredient14.toString() + "\n"
                                measureTxt.text = measureTxt.text.toString() +  it.meals?.get(0)?.strMeasure14.toString() + "\n"
                            }
                            if (it.meals?.get(0)?.strIngredient1!=""){
                                ingridientTxt.text = ingridientTxt.text.toString() + it.meals?.get(0)?.strIngredient15.toString() + "\n"
                                measureTxt.text = measureTxt.text.toString() +  it.meals?.get(0)?.strMeasure15.toString() + "\n"
                            }
                            if (it.meals?.get(0)?.strIngredient1!=""){
                                ingridientTxt.text = ingridientTxt.text.toString() + it.meals?.get(0)?.strIngredient16.toString() + "\n"
                                measureTxt.text = measureTxt.text.toString() +  it.meals?.get(0)?.strMeasure16.toString() + "\n"
                            }
                            if (it.meals?.get(0)?.strIngredient1!=""){
                                ingridientTxt.text = ingridientTxt.text.toString() + it.meals?.get(0)?.strIngredient17.toString() + "\n"
                                measureTxt.text = measureTxt.text.toString() +  it.meals?.get(0)?.strMeasure17.toString() + "\n"
                            }
                            if (it.meals?.get(0)?.strIngredient1!=""){
                                ingridientTxt.text = ingridientTxt.text.toString() + it.meals?.get(0)?.strIngredient18.toString() + "\n"
                                measureTxt.text = measureTxt.text.toString() + it.meals?.get(0)?.strMeasure18.toString() + "\n"
                            }
                            if (it.meals?.get(0)?.strIngredient1!=""){
                                ingridientTxt.text = ingridientTxt.text.toString() + it.meals?.get(0)?.strIngredient19.toString() + "\n"
                                measureTxt.text = measureTxt.text.toString() + it.meals?.get(0)?.strMeasure19.toString() + "\n"
                            }
                            catName.text = it.meals?.get(0)?.strCategory.toString()
                            countryName.text = it.meals?.get(0)?.strArea.toString()
                        }
                    }
                }
            }
        }
    }
}