package com.example.pkaart.roomDb

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface ProductDao {

    @Insert
    suspend fun insertProduct(product: ProductModel)

    @Delete
    suspend fun deleteProduct(product: ProductModel)

    @Query("SELECT * FROM products")
    fun getAllProducts(): LiveData<List<ProductModel>>

    @Query("SELECT * FROM products WHERE productId = :id")
    fun isExist(id: String): ProductModel


}