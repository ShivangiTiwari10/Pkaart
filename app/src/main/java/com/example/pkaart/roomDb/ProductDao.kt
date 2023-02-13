package com.example.pkaart.roomDb

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface ProductDao {

    @Insert
    fun insertProduct(product: ProductModel)

    @Delete
    suspend fun deleteProduct(product: ProductModel)

    @Query("SELECT * FROM products")
    fun getAllProducts(): LiveData<ProductModel>

    @Query("SELECT * FROM products WHERE productId =:Id")
    fun isExist(id:String):ProductModel


}