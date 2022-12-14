package com.example.storeapp.ui.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storeapp.data.entity.ProductEntity
import com.example.storeapp.data.entity.Products
import com.example.storeapp.domain.mapper.ProductEntityMapper
import com.example.storeapp.repository.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val dbRepository: StoreRepository
): ViewModel() {
    val favList: MutableLiveData<List<ProductEntity>> = MutableLiveData()
    val progressBar = MutableLiveData<Boolean>()
    private val mapper = ProductEntityMapper()

    fun getAllFavoriteFromRoom(){
        viewModelScope.launch(Dispatchers.IO) {
            progressBar.postValue(true)
            favList.postValue(dbRepository.getAllFavorites())
        }
    }
    fun addCart(product: Products){
        viewModelScope.launch {
            val productEntity: ProductEntity = mapper.mapToEntity(product)
            dbRepository.addProduct(productEntity)
        }
    }

    fun deleteFavorite(productId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.deleteFavorite(productId)
            getAllFavoriteFromRoom()
        }
    }
}