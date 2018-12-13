package com.xpn.foodinfo.services.image;

import com.xpn.foodinfo.models.Nutrient;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface FoodImageProcessingService {

    @GET( "processImage" )
    Observable<List<Nutrient>> processImage(@Query(value = "imageUrl") String text);
}
