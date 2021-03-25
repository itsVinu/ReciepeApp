package com.example.reciepeapp.responses.categoryDetailResponse

data class CategoryDetailResponse(
	val meals: List<MealsItem>? = null
)

data class MealsItem(
	val strMealThumb: String? = null,
	val idMeal: String? = null,
	val strMeal: String? = null
)

