package com.example.reciepeapp.client

import com.example.reciepeapp.responses.categoryDetailResponse.CategoryDetailResponse
import com.example.reciepeapp.responses.categoryResponse.CategoryResponse
import com.example.reciepeapp.responses.foodDetailResponse.FoodDetailResponse
import com.example.reciepeapp.responses.searchResponse.SearchFoodResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface getResult {

    @GET("api/json/v1/1/categories.php")
    suspend fun getAllCategories(): Response<CategoryResponse>

    @GET("api/json/v1/1/filter.php")
    suspend fun getCategoryDetail(@Query("c")c:String):Response<CategoryDetailResponse>

    @GET("api/json/v1/1/lookup.php")
    suspend fun getFoodDetail(@Query("i")i:String):Response<FoodDetailResponse>

    @GET("https://www.themealdb.com/api/json/v1/1/search.php")
    suspend fun getSearchResult(@Query("s")s:String):Response<SearchFoodResponse>
}