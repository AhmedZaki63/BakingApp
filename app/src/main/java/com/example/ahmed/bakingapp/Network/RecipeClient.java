package com.example.ahmed.bakingapp.Network;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeClient {

    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";

    public static Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RecipeApi createApi(Retrofit retrofit) {
        return retrofit.create(RecipeApi.class);
    }
}
