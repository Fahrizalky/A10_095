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
import com.example.tanipintar.model.Aktivitas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody

class AktivitasViewModel : ViewModel() {

    private val _aktivitasList = MutableStateFlow<List<Aktivitas>>(emptyList())
    val aktivitasList: StateFlow<List<Aktivitas>> = _aktivitasList

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> get() = _message

    init {
        getAllaktivitas()
    }

    fun getaktivitasById(aktivitasId: String): Aktivitas? {
        return aktivitasList.value.find { it.id_aktivitas == aktivitasId }
    }
    private fun getAllaktivitas()
    {
        viewModelScope.launch {
            ApiClient.instance.getAllaktivitas().enqueue(object : Callback<List<Aktivitas>> {
                override fun onResponse(
                    call: Call<List<Aktivitas>>,
                    response: Response<List<Aktivitas>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { data ->
                            _aktivitasList.value = data
                            Log.d("aktivitasViewModel", "Data berhasil diambil: $data")
                        } ?: run {
                            _message.value = "Data kosong."
                            Log.e("aktivitasViewModel", "Response body kosong.")
                        }
                    } else {
                        _message.value = "Gagal mengambil data: ${response.code()}"
                        Log.e("aktivitasViewModel", "Error code: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<List<Aktivitas>>, t: Throwable) {
                    _message.value = "Gagal mengambil data: ${t.message}"
                    Log.e("aktivitasViewModel", "Error: ${t.message}")
                }
            })
        }
    }

    fun insertaktivitas(id_tanaman: String, id_pekerja: String, tanggal_aktivitas: String, deskripsi_aktivitas: String) {
        viewModelScope.launch {
            ApiClient.instance.insertaktivitas(id_tanaman,id_pekerja,tanggal_aktivitas,deskripsi_aktivitas).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        _message.value = "Data berhasil ditambahkan!"
                        getAllaktivitas()  // Refresh data setelah berhasil
                    } else {
                        _message.value = "Gagal menambahkan data: ${response.code()}"
                        Log.e("Addaktivitas", "Error: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    _message.value = "Error: ${t.message}"
                }
            }) }
    }

    fun updateaktivitas(id: String, id_tanaman: String, id_pekerja: String, tanggal_aktivitas: String, deskripsi_aktivitas: String) {
        viewModelScope.launch {
            try {
                ApiClient.instance.updateaktivitas(id, id_tanaman,id_pekerja,tanggal_aktivitas,deskripsi_aktivitas).enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            getAllaktivitas()
                            Log.d("Updateaktivitas", "Data berhasil diperbarui: ${response.body()?.string()}")
                            // Tambahkan logika jika pembaruan berhasil
                        } else {
                            Log.e("Updateaktivitas", "Gagal memperbarui data: ${response.errorBody()?.string()}")
                            // Tambahkan logika jika ada error di sisi server
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.e("Updateaktivitas", "Error: ${t.localizedMessage}")
                        // Tambahkan logika jika terjadi kegagalan jaringan
                    }
                })
            } catch (e: Exception) {
                Log.e("Updateaktivitas", "Exception: ${e.localizedMessage}")
                // Tangani pengecualian lain di luar enqueue
            }
        }
    }
    fun deleteaktivitas(id: Int) {
        viewModelScope.launch {
            ApiClient.instance.deleteaktivitas(id).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.d("Deleteaktivitas", "ID aktivitas yang dikirim: $id")
                    Log.d("Deleteaktivitas", "Response Code: ${response.code()}")
                    Log.d("Deleteaktivitas", "Response Body: ${response.body()?.string()}")
                    if (response.isSuccessful) {
                        _message.value = "Data berhasil dihapus!"
                        getAllaktivitas() // Refresh data setelah berhasil
                    } else {
                        _message.value = "Gagal menghapus data."
                        Log.e("Deleteaktivitas", "Error: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    _message.value = "Error: ${t.message}"
                }
            })
        }
    }
}

