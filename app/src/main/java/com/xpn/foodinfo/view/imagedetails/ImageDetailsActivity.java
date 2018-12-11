package com.xpn.foodinfo.view.imagedetails;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.xpn.foodinfo.R;
import com.xpn.foodinfo.databinding.ActivityImageDetailsBinding;
import com.xpn.foodinfo.models.Image;
import com.xpn.foodinfo.viewmodels.imagedetails.ImageDetailsVM;
import com.xpn.foodinfo.viewmodels.imagedetails.ImageDetailsVMFactory;


public class ImageDetailsActivity extends AppCompatActivity {

    private static final String IMAGE_EXTRA = "img";

    public static void start(Context context, Image image) {
        Intent i = new Intent(context, ImageDetailsActivity.class);
        i.putExtra(IMAGE_EXTRA, image);
        context.startActivity(i);
    }

    ActivityImageDetailsBinding binding;
    ImageDetailsVM viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_details);

        Image image = (Image) getIntent().getExtras().getSerializable(IMAGE_EXTRA);
        viewModel = ViewModelProviders.of(this, new ImageDetailsVMFactory(image)).get(ImageDetailsVM.class);
        binding.setViewModel(viewModel);
    }
}
