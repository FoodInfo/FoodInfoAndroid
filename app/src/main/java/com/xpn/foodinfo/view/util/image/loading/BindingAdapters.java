package com.xpn.foodinfo.view.util.image.loading;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.widget.ImageView;

import com.xpn.foodinfo.GlideApp;
import com.xpn.foodinfo.view.util.image.svg.SvgSoftwareLayerSetter;


public class BindingAdapters {

    @BindingAdapter({"imageUrl", "placeholder"})
    public static void loadImage(ImageView imageView, String url, Drawable placeholder) {
        if( url.contains( ".svg" ) )
            GlideApp.with(imageView)
                    .as(PictureDrawable.class)
                    .listener(new SvgSoftwareLayerSetter())
                    .load(url)
                    .placeholder(placeholder)
                    .error(placeholder)
                    .centerInside()
                    .into(imageView);
        else
            GlideApp.with(imageView)
                    .load(url)
                    .placeholder(placeholder)
                    .error(placeholder)
                    .centerInside()
                    .into(imageView);
    }


    @BindingAdapter("android:src")
    public static void setImageResource(ImageView imageView, int resource){
        imageView.setImageResource(resource);
    }

    @BindingAdapter("src")
    public static void loadImage(ImageView imageView, Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }

    @BindingAdapter("src")
    public static void loadImage(ImageView imageView, byte[] image) {
        if( image == null )
            return;
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        imageView.setImageBitmap(bitmap);
    }
}
