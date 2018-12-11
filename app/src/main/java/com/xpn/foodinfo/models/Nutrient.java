package com.xpn.foodinfo.models;

import android.graphics.PointF;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;


@Data
@ToString
@AllArgsConstructor
public class Nutrient {
    private final String name;
    private final float score;
    private final List<PointF> boundingBox;
}