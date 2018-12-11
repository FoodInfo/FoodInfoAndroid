package com.xpn.foodinfo.viewmodels.main.home;

import android.view.View;

import com.xpn.foodinfo.models.Image;
import com.xpn.foodinfo.util.DateTimeUtil;
import com.xpn.foodinfo.viewmodels.BaseViewModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class ImageItemVM extends BaseViewModel {
    private final int id;
    private final Image image;
    private final Contract contract;

    public String getDownloadUrl() {
        return image.getDownloadUrl();
    }

    public String getDateCaptured() {
        Date d;
        try {
            d = DateTimeUtil.ISOToDate(image.getDateCaptured());
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }

        DateFormat format = new SimpleDateFormat("MMM-d HH:m", Locale.US);
        return format.format(d);
    }

    public void onOptionsClicked(View view) {
        contract.onShowPopupMenu(view, id);
    }

    public void onContentClicked() {
        contract.onShowImageDetails(id);
    }


    interface Contract {
        void onShowPopupMenu(View view, int id);
        void onShowImageDetails(int id);
    }
}
