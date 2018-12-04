package com.xpn.foodinfo.view.util;

import android.support.media.ExifInterface;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


public class CameraInfoUtil {

    public static int getRotation(byte[] source) {
        int orientation;
        try (InputStream stream = new ByteArrayInputStream(source)) {
            // http://sylvana.net/jpegcrop/exif_orientation.html
            ExifInterface exif = new ExifInterface(stream);
            int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (exifOrientation) {
                case ExifInterface.ORIENTATION_NORMAL:
                case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                    orientation = 0;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                    orientation = 180;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_90:
                case ExifInterface.ORIENTATION_TRANSPOSE:
                    orientation = 90;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                case ExifInterface.ORIENTATION_TRANSVERSE:
                    orientation = 270;
                    break;

                default:
                    orientation = 0;
            }

        } catch (IOException e) {
            e.printStackTrace();
            orientation = 0;
        }

        return orientation;
    }
}