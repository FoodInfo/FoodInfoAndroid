package com.xpn.foodinfo.viewmodels.main.home;

import com.xpn.foodinfo.models.User;
import com.xpn.foodinfo.viewmodels.BaseViewModel;


public class HomeVM extends BaseViewModel {
    private final User user;

    HomeVM(User user) {
        this.user = user;
    }

    public String getPhotoUrl() {
        return user.getPhotoUrl();
    }
}
