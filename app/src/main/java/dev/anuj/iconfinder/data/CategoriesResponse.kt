package dev.anuj.iconfinder.data

data class CategoriesResponse(
    val categories: List<Category>,
    val total_count: Int
)