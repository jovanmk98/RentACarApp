package org.meicode.rentacarapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.meicode.rentacarapp.Api.JsonPlaceHolderApi;
import org.meicode.rentacarapp.Model.Car;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RentCarActivity extends AppCompatActivity {
    private TextView txtCars;
    List<Car> carsList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_car);
        txtCars = (TextView) findViewById(R.id.tx_carname);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Car>> call = jsonPlaceHolderApi.getPosts();
        call.enqueue(new Callback<List<Car>>() {
            @Override
            public void onResponse(Call<List<Car>> call, Response<List<Car>> response) {
                if (!response.isSuccessful()){
                    txtCars.setText("code: ");
                    return;
                }
                carsList = response.body();
                for (Car car : carsList){
                    String content = "";
                    content += "name: "  + car.getName();
                    txtCars.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Car>> call, Throwable t) {
                txtCars.setText(t.getMessage());
            }
        });
    }
}