package com.xpn.foodinfo.view.util.image.loading;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.xpn.foodinfo.GlideApp;
import com.xpn.foodinfo.GlideRequest;
import com.xpn.foodinfo.view.util.image.svg.SvgSoftwareLayerSetter;
import com.xpn.foodinfo.view.util.image.transformation.CompressTransformation;
import com.xpn.foodinfo.view.util.image.transformation.RotateTransformation;


public class BindingAdapters {

    public interface ImageListener {
        void onImageLoaded(Bitmap image);
        void onFailure(Exception e);
    }

    @BindingAdapter(value={"url", "uri", "imageData", "rotationDegrees", "compressQuality", "placeholder", "listener"}, requireAll=false)
    public static void loadImage(ImageView imageView,
                                 String url, Uri uri, byte[] imageData, Integer rotationDegrees,
                                 Integer compressQuality,
                                 Drawable placeholder,
                                 ImageListener listener) {
        GlideRequest <Bitmap> request;
        if( url != null ) {
            if( !url.contains( ".svg" ) )
                request = GlideApp.with(imageView).asBitmap().load(url);
            else
                request = GlideApp.with(imageView)
                        .asBitmap()
                        .listener(new SvgSoftwareLayerSetter());
        }
        else if( uri != null )          request = GlideApp.with(imageView).asBitmap().load(uri);
        else if( imageData != null )    request = GlideApp.with(imageView).asBitmap().load(imageData);
        else                            request = GlideApp.with(imageView).asBitmap().load("error");

        if( rotationDegrees != null )   request = request.apply(RequestOptions.bitmapTransform(new RotateTransformation(rotationDegrees)));
        if( compressQuality != null )   request = request.apply(RequestOptions.bitmapTransform(new CompressTransformation(compressQuality)));
        if( placeholder != null )       request = request.placeholder(placeholder).error(placeholder);

        if( listener != null ) {
            request = request.listener(new RequestListener<Bitmap>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                    listener.onFailure(e);
                    return false;
                }

                @Override
                public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                    listener.onImageLoaded(resource);
                    return false;
                }
            });
        }

        request.into(imageView);
    }
}
