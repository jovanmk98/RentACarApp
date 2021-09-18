package org.meicode.rentacarapp.Api;

import org.meicode.rentacarapp.Model.Car;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    @GET("cars")
    Call<List<Car>> getPosts();

}
