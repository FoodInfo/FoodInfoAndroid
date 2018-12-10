package com.xpn.foodinfo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;


@Data
@ToString
@AllArgsConstructor
public class Image {
    private final String name;
    private final String uploaderId;
    private final String downloadUrl;
}
