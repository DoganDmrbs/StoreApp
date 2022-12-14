package com.example.storeapp.ui.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storeapp.data.entity.ProductEntity
import com.example.storeapp.repository.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val dbRepository: StoreRepository,
): ViewModel() {
    val cartList: MutableLiveData<List<ProductEntity>> = MutableLiveData()
    val progressBar = MutableLiveData<Boolean>()

    fun getAllProductFromRoom(){
        viewModelScope.launch(Dispatchers.IO) {
            progressBar.postValue(true)
            cartList.postValue(dbRepository.getAllProducts())
        }
    }

    fun deleteProduct(productId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.deleteProduct(productId)
            getAllProductFromRoom()
        }
    }
}