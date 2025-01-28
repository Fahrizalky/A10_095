package com.example.tanipintar.viewmodel


import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanipintar.api.ApiService
import com.example.tanipintar.model.DataItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.tanipintar.api.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody

class TanamanViewModel : ViewModel() {
    init {
        getAllTanaman()
    }
    private val _tanamanList = MutableStateFlow<List<DataItem>>(emptyList())
    val tanamanList: StateFlow<List<DataItem>> = _tanamanList

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> get() = _message



    fun getTanamanById(tanamanId: String): DataItem? {
        return tanamanList.value.find { it.id_tanaman == tanamanId }
    }
    private fun getAllTanaman()
    {
        viewModelScope.launch {
            ApiClient.instance.getAllTanaman().enqueue(object : Callback<List<DataItem>> {
                override fun onResponse(
                    call: Call<List<DataItem>>,
                    response: Response<List<DataItem>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { data ->
                            _tanamanList.value = data
                            Log.d("TanamanViewModel", "Data berhasil diambil: $data")
                        } ?: run {
                            _message.value = "Data kosong."
                            Log.e("TanamanViewModel", "Response body kosong.")
                        }
                    } else {
                        _message.value = "Gagal mengambil data: ${response.code()}"
                        Log.e("TanamanViewModel", "Error code: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<List<DataItem>>, t: Throwable) {
                    _message.value = "Gagal mengambil data: ${t.message}"
                    Log.e("TanamanViewModel", "Error: ${t.message}")
                }
            })
        }
    }

fun insertTanaman(namaTanaman: String, periodeTanam: String, deskripsiTanaman: String) {
    viewModelScope.launch {
      ApiClient.instance.insertTanaman(namaTanaman, periodeTanam, deskripsiTanaman).enqueue(object : Callback<ResponseBody> {
        override fun onResponse(
            call: Call<ResponseBody>,
            response: Response<ResponseBody>
        ) {
            if (response.isSuccessful) {
                _message.value = "Data berhasil ditambahkan!"
                getAllTanaman()  // Refresh data setelah berhasil
            } else {
                _message.value = "Gagal menambahkan data: ${response.code()}"
            }
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            _message.value = "Error: ${t.message}"
        }
    }) }
}

fun updateTanaman(id: String, nama: String, periode: String, deskripsi: String) {
    viewModelScope.launch {
        try {
            ApiClient.instance.updateTanaman(id, nama, periode, deskripsi).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        getAllTanaman()
                        Log.d("UpdateTanaman", "Data berhasil diperbarui: ${response.body()?.string()}")
                        // Tambahkan logika jika pembaruan berhasil
                    } else {
                        Log.e("UpdateTanaman", "Gagal memperbarui data: ${response.errorBody()?.string()}")
                        // Tambahkan logika jika ada error di sisi server
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("UpdateTanaman", "Error: ${t.localizedMessage}")
                    // Tambahkan logika jika terjadi kegagalan jaringan
                }
            })
        } catch (e: Exception) {
            Log.e("UpdateTanaman", "Exception: ${e.localizedMessage}")
            // Tangani pengecualian lain di luar enqueue
        }
    }
}
    fun deleteTanaman(id: Int) {
        viewModelScope.launch {
            ApiClient.instance.deleteTanaman(id).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.d("DeleteTanaman", "ID tanaman yang dikirim: $id")
                    Log.d("DeleteTanaman", "Response Code: ${response.code()}")
                    Log.d("DeleteTanaman", "Response Body: ${response.body()?.string()}")
                    if (response.isSuccessful) {
                        _message.value = "Data berhasil dihapus!"
                        getAllTanaman() // Refresh data setelah berhasil
                    } else {
                        _message.value = "Gagal menghapus data."
                        Log.e("DeleteTanaman", "Error: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    _message.value = "Error: ${t.message}"
                }
            })
        }
    }
}
