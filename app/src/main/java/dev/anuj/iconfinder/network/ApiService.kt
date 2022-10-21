package dev.anuj.iconfinder.network

import dev.anuj.iconfinder.data.CategoriesResponse
import dev.anuj.iconfinder.data.IconResponse
import dev.anuj.iconfinder.data.IconsetResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search")
    suspend fun searchIcons(
        @Query("query") query: String,
    ): IconResponse

    @GET("categories")
    suspend fun getCategories(
        @Query("count") count: Int = 25,
        @Query("after") after: String? = null,
    ): CategoriesResponse

    @GET("categories/{category_id}/iconsets")
    suspend fun getIconSets(
        @Path("category_id") categoryId: String,
        @Query("count") count: Int = 25,
    ): IconsetResponse

    @GET("iconsets/{iconset_id}/icons")
    suspend fun getIcons(
        @Path("iconset_id") iconsetId: Int,
    ): IconResponse
}