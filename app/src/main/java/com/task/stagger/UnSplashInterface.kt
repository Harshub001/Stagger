package com.task.stagger

import com.task.stagger.model.UnsplashData
import retrofit2.Response
import retrofit2.http.GET

// https://api.unsplash.com/photos/?client_id=238b4f660e017edb7dadc5ce864869daf68441fd58249d0f773123334f11ef9f
interface UnSplashInterface {

    @GET("photos/")
    suspend fun getPhotos(): Response<UnsplashData>
}