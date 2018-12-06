package com.xpn.foodinfo.view.util;

import android.content.Intent;
import android.support.v4.app.Fragment;


public class Util {

    public static void chooseImageFromGallery(Fragment fragment, int requestCode) {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Choose image from gallery");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        fragment.startActivityForResult(chooserIntent, requestCode);
    }
}
