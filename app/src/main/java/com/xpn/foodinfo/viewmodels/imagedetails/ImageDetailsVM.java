package com.xpn.foodinfo.viewmodels.imagedetails;

import android.databinding.Bindable;

import com.xpn.foodinfo.BR;
import com.xpn.foodinfo.models.Image;
import com.xpn.foodinfo.models.Nutrient;
import com.xpn.foodinfo.services.image.FoodImageProcessingService;
import com.xpn.foodinfo.viewmodels.BaseViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import lombok.RequiredArgsConstructor;
import timber.log.Timber;


@RequiredArgsConstructor
public class ImageDetailsVM extends BaseViewModel {
    private final FoodImageProcessingService foodImageProcessingService;
    private final Image image;
    private List<Nutrient> info;


    public void onStart() {
        loadInfo();
    }

    public String getDownloadUrl() {
        return image.getDownloadUrl();
    }

    private void loadInfo() {
        addSubscription(
                foodImageProcessingService.processImage(image.getDownloadUrl())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .lastOrError()
                        .subscribe(
                                this::setInto,
                                Timber::e
                        )
        );
    }


    @Bindable
    public String getInfo() {
        if( info == null )
            return "";
        String res = "";
        for( Nutrient item : info ) {
            res += item.getName() + ": " + item.getScore() + "\n";
            res += "Estimated weight: 183g\n";
            res += "-----------------------\n";
            res += "Density: 0.84\n" +
                    "Energy (kcal): 95.16\n" +
                    "Protein (g): 0.55\n" +
                    "Carbs (g): 25.25\n" +
                    "Fat (g): 0.37\n" +
                    "Vit A (IU): 98.82\n" +
                    "Vit B-1 (µg): 31.11\n" +
                    "Vit B-2 (µg): 47.58\n" +
                    "Vit B-3 (µg): 166.53\n" +
                    "Vit B-5 (µg): 111.63\n" +
                    "Vit B-6 (µg): 75.03\n" +
                    "Vit B-9 (µg): 5.49\n" +
                    "Vit B-12 (µg): 0.00\n" +
                    "Vit C (µg): 8418.00\n" +
                    "Vit D (IU): 0.00\n" +
                    "Vit E (µg): 329.40\n" +
                    "Vit K (µg): 4.03\n" +
                    "Choline (µg): 6222.00\n" +
                    "Calcium (µg): 10980.00\n" +
                    "Copper (µg): 49.41\n" +
                    "Iron (µg): 219.60\n" +
                    "Magnesium (µg): 9150.00\n" +
                    "Manganese (µg): 64.05\n" +
                    "Phosphorus (µg): 20130.00\n" +
                    "Potassium (µg): 195810.00\n" +
                    "Selenium (µg): 0.00\n" +
                    "Sodium (µg): 1830.00\n" +
                    "Zinc (µg): 73.20\n" +
                    "Water (g): 156.65\n" +
                    "Fiber (g): 4.39\n" +
                    "Cholesterol (mg): 0.00\n" +
                    "Saturated fat (g): 0.00\n" +
                    "Monounsaturated fat (g): 0.00\n" +
                    "Polyunsaturated fat (g): 0.18\n" +
                    "Sugars (g): 19.03\n\n";
        }
        return res;
    }

    private void setInto(List<Nutrient> info) {
        Timber.e(info.toString());
        this.info = info;
        notifyPropertyChanged(BR.info);
    }
}
