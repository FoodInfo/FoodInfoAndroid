package com.xpn.foodinfo.viewmodels.main.home;

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
    private final Image image;

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
}
