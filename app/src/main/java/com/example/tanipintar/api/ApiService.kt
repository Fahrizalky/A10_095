package com.example.tanipintar.api

import com.example.tanipintar.model.Aktivitas
import com.example.tanipintar.model.CatatanPanen
import com.example.tanipintar.model.DataItem
import com.example.tanipintar.model.Pekerja
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiService {
    @GET("tanaman_api.php") // Sesuaikan dengan endpoint Anda
    fun getAllTanaman(): Call<List<DataItem>>

    @FormUrlEncoded
    @POST("tanaman_api.php")
    fun insertTanaman(
        @Field("nama_tanaman") namaTanaman: String,
        @Field("periode_tanam") periodeTanam: String,
        @Field("deskripsi_tanaman") deskripsiTanaman: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @PUT("tanaman_api.php")
    fun updateTanaman(
        @Field("id_tanaman") idTanaman: String,
        @Field("nama_tanaman") namaTanaman: String,
        @Field("periode_tanam") periodeTanam: String,
        @Field("deskripsi_tanaman") deskripsiTanaman: String
    ): Call<ResponseBody>

    @DELETE("tanaman_api.php")
    fun deleteTanaman(
        @Query("id_tanaman") idTanaman: Int
    ): Call<ResponseBody>

//bagian pekerja
    @GET("catatan_panen.php") // Sesuaikan dengan endpoint Anda
    fun getAllcatatan(): Call<List<CatatanPanen>>

    @FormUrlEncoded
    @POST("catatan_panen.php")
    fun insertcatatan(
        @Field("id_tanaman") idTanaman: String,
        @Field("tanggal_panen") tanggalPanen: String,
        @Field("jumlah_hasil") jumlahHasil: String,
        @Field("keterangan") keterangan: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @PUT("catatan_panen.php")
    fun updatecatatan(
        @Field("id_panen") idPanen: String,
        @Field("id_tanaman") idTanaman: String,
        @Field("tanggal_panen") tanggalPanen: String,
        @Field("jumlah_hasil") jumlahHasil: String,
        @Field("keterangan") keterangan: String
    ): Call<ResponseBody>

    @DELETE("catatan_panen.php")
    fun deletecatatan(
        @Query("id_panen") idPanen: Int
    ): Call<ResponseBody>

//    bagian catatan
@GET("pekerja.php") // Sesuaikan dengan endpoint Anda
fun getAllpekerja(): Call<List<Pekerja>>
    @FormUrlEncoded
    @POST("pekerja.php")
    fun insertpekerja(
        @Field("nama_pekerja") namaPekerja: String,
        @Field("jabatan") jabatan: String,
        @Field("kontak") kontak: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @PUT("pekerja.php")
    fun updatepekerja(
        @Field("id_pekerja") idPekerja: String,
        @Field("nama_pekerja") namaPekerja: String,
        @Field("jabatan") jabatan: String,
        @Field("kontak") kontak: String
    ): Call<ResponseBody>

    @DELETE("pekerja.php")
    fun deletepekerja(
        @Query("id_pekerja") idPekerja: Int
    ): Call<ResponseBody>



    //    bagian aktivitas
    @GET("aktivitas.php") // Sesuaikan dengan endpoint Anda
    fun getAllaktivitas(): Call<List<Aktivitas>>
    @FormUrlEncoded
    @POST("aktivitas.php")
    fun insertaktivitas(
        @Field("id_tanaman") idTanaman: String,
        @Field("id_pekerja") idPekerja: String,
        @Field("tanggal_aktivitas") tanggalAktivitas: String,
        @Field("deskripsi_aktivitas") deskripsiAktivitas: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @PUT("aktivitas.php")
    fun updateaktivitas(
        @Field("id_aktivitas") idAktivitas: String,
        @Field("id_tanaman") idTanaman: String,
        @Field("id_pekerja") idPekerja: String,
        @Field("tanggal_aktivitas") tanggalAktivitas: String,
        @Field("deskripsi_aktivitas") deskripsiAktivitas: String
    ): Call<ResponseBody>

    @DELETE("aktivitas.php")
    fun deleteaktivitas(
        @Query("id_aktivitas") idAktivitas: Int
    ): Call<ResponseBody>
}