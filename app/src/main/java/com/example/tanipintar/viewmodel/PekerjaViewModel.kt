package com.example.tanipintar.viewmodel

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanipintar.api.ApiService
import com.example.tanipintar.model.Pekerja
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

class PekerjaViewModel : ViewModel() {

    private val _pekerjaList = MutableStateFlow<List<Pekerja>>(emptyList())
    val pekerjaList: StateFlow<List<Pekerja>> = _pekerjaList

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> get() = _message

    init {
        getAllpekerja()
    }

    fun getpekerjaById(pekerjaId: String): Pekerja? {
        return pekerjaList.value.find { it.id_pekerja == pekerjaId }
    }
    private fun getAllpekerja()
    {
        viewModelScope.launch {
            ApiClient.instance.getAllpekerja().enqueue(object : Callback<List<Pekerja>> {
                override fun onResponse(
                    call: Call<List<Pekerja>>,
                    response: Response<List<Pekerja>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { data ->
                            _pekerjaList.value = data
                            Log.d("pekerjaViewModel", "Data berhasil diambil: $data")
                        } ?: run {
                            _message.value = "Data kosong."
                            Log.e("pekerjaViewModel", "Response body kosong.")
                        }
                    } else {
                        _message.value = "Gagal mengambil data: ${response.code()}"
                        Log.e("pekerjaViewModel", "Error code: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<List<Pekerja>>, t: Throwable) {
                    _message.value = "Gagal mengambil data: ${t.message}"
                    Log.e("pekerjaViewModel", "Error: ${t.message}")
                }
            })
        }
    }

    fun insertpekerja(namapekerja: String, periodeTanam: String, deskripsipekerja: String) {
        viewModelScope.launch {
            ApiClient.instance.insertpekerja(namapekerja, periodeTanam, deskripsipekerja).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        _message.value = "Data berhasil ditambahkan!"
                        getAllpekerja()  // Refresh data setelah berhasil
                    } else {
                        _message.value = "Gagal menambahkan data: ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    _message.value = "Error: ${t.message}"
                }
            }) }
    }

    fun updatepekerja(id: String, nama: String, periode: String, deskripsi: String) {
        viewModelScope.launch {
            try {
                ApiClient.instance.updatepekerja(id, nama, periode, deskripsi).enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            getAllpekerja()
                            Log.d("Updatepekerja", "Data berhasil diperbarui: ${response.body()?.string()}")
                            // Tambahkan logika jika pembaruan berhasil
                        } else {
                            Log.e("Updatepekerja", "Gagal memperbarui data: ${response.errorBody()?.string()}")
                            // Tambahkan logika jika ada error di sisi server
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.e("Updatepekerja", "Error: ${t.localizedMessage}")
                        // Tambahkan logika jika terjadi kegagalan jaringan
                    }
                })
            } catch (e: Exception) {
                Log.e("Updatepekerja", "Exception: ${e.localizedMessage}")
                // Tangani pengecualian lain di luar enqueue
            }
        }
    }
    fun deletepekerja(id: Int) {
        viewModelScope.launch {
            ApiClient.instance.deletepekerja(id).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.d("Deletepekerja", "ID pekerja yang dikirim: $id")
                    Log.d("Deletepekerja", "Response Code: ${response.code()}")
                    Log.d("Deletepekerja", "Response Body: ${response.body()?.string()}")
                    if (response.isSuccessful) {
                        _message.value = "Data berhasil dihapus!"
                        getAllpekerja() // Refresh data setelah berhasil
                    } else {
                        _message.value = "Gagal menghapus data."
                        Log.e("Deletepekerja", "Error: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    _message.value = "Error: ${t.message}"
                }
            })
        }
    }
}
