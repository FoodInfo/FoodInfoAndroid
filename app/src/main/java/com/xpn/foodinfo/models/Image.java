package com.xpn.foodinfo.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;


@Data
@ToString
@AllArgsConstructor
public class Image implements Serializable {
    private final String name;
    private final String uploaderId;
    private final String downloadUrl;
    private final String dateCaptured;

    public Image() {
        this(null, null, null, null);
    }
}
