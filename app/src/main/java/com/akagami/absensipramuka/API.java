package com.akagami.absensipramuka;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface API {

    String BASEURL = "http://192.168.1.6:8000/api/";

    @FormUrlEncoded
    @POST("login")
    Call<String> postLogin(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("profil_siswa")
    Call<String> postProfilSiswa(
            @Field("username") String username
    );

    @FormUrlEncoded
    @POST("absensi_siswa")
    Call<String> postAbsensiSiswa(
            @Field("username") String username
    );
}
