package com.example.tanipintar.viewmodel

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tanipintar.api.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.tanipintar.api.ApiClient
import com.example.tanipintar.model.CatatanPanen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody

class CatatanViewModel : ViewModel() {

    private val _catatanList = MutableStateFlow<List<CatatanPanen>>(emptyList())
    val catatanList: StateFlow<List<CatatanPanen>> = _catatanList

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> get() = _message

    init {
        getAllcatatan()
    }

    fun getcatatanById(catatanId: String): CatatanPanen? {
        return catatanList.value.find { it.id_panen == catatanId }
    }
    private fun getAllcatatan()
    {
        viewModelScope.launch {
            ApiClient.instance.getAllcatatan().enqueue(object : Callback<List<CatatanPanen>> {
                override fun onResponse(
                    call: Call<List<CatatanPanen>>,
                    response: Response<List<CatatanPanen>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { data ->
                            _catatanList.value = data
                            Log.d("catatanViewModel", "Data berhasil diambil: $data")
                        } ?: run {
                            _message.value = "Data kosong."
                            Log.e("catatanViewModel", "Response body kosong.")
                        }
                    } else {
                        _message.value = "Gagal mengambil data: ${response.code()}"
                        Log.e("catatanViewModel", "Error code: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<List<CatatanPanen>>, t: Throwable) {
                    _message.value = "Gagal mengambil data: ${t.message}"
                    Log.e("catatanViewModel", "Error: ${t.message}")
                }
            })
        }
    }

    fun insertcatatan(idTanaman: String, tanggalPanen: String, jumlahHasil: String,keterangan:String) {
        viewModelScope.launch {
            ApiClient.instance.insertcatatan(idTanaman, tanggalPanen, jumlahHasil,keterangan).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        _message.value = "Data berhasil ditambahkan!"
                        getAllcatatan()  // Refresh data setelah berhasil
                    } else {
                        _message.value = "Gagal menambahkan data: ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    _message.value = "Error: ${t.message}"
                }
            }) }
    }

    fun updatecatatan(id: String, idTanaman: String, tanggalPanen: String, jumlahHasil: String,keterangan:String) {
        viewModelScope.launch {
            try {
                ApiClient.instance.updatecatatan(id, idTanaman, tanggalPanen, jumlahHasil,keterangan).enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            getAllcatatan()
                            Log.d("Updatecatatan", "Data berhasil diperbarui: ${response.body()?.string()}")
                            // Tambahkan logika jika pembaruan berhasil
                        } else {
                            Log.e("Updatecatatan", "Gagal memperbarui data: ${response.errorBody()?.string()}")
                            // Tambahkan logika jika ada error di sisi server
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.e("Updatecatatan", "Error: ${t.localizedMessage}")
                        // Tambahkan logika jika terjadi kegagalan jaringan
                    }
                })
            } catch (e: Exception) {
                Log.e("Updatecatatan", "Exception: ${e.localizedMessage}")
                // Tangani pengecualian lain di luar enqueue
            }
        }
    }
    fun deletecatatan(id: Int) {
        viewModelScope.launch {
            ApiClient.instance.deletecatatan(id).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.d("Deletecatatan", "ID catatan yang dikirim: $id")
                    Log.d("Deletecatatan", "Response Code: ${response.code()}")
                    Log.d("Deletecatatan", "Response Body: ${response.body()?.string()}")
                    if (response.isSuccessful) {
                        _message.value = "Data berhasil dihapus!"
                        getAllcatatan() // Refresh data setelah berhasil
                    } else {
                        _message.value = "Gagal menghapus data."
                        Log.e("Deletecatatan", "Error: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    _message.value = "Error: ${t.message}"
                }
            })
        }
    }
}

