package fr.epita.calcforkids.services

import fr.epita.calcforkids.models.AdsResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("/v1/fbdb37c1-f194-4c53-b94d-f9da5f2b39bb")
    fun getAdList(): Call<List<AdsResponse>>
}