package com.xpn.foodinfo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;


@Data
@ToString
@AllArgsConstructor
public class Preference {
    private final String tag;
    private final String preference;
}
