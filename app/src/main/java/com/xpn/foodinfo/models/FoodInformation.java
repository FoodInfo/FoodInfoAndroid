package com.xpn.foodinfo.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;


@Data
@ToString
@AllArgsConstructor
public class FoodInformation {
    private final List<Nutrient> nutrients;

    @Data
    @ToString
    @AllArgsConstructor
    public static class Nutrient {
        private final String name;
        private final int quantity;
        private final BoundingBox box;
    }


    @Data
    @ToString
    @AllArgsConstructor
    public static class BoundingBox {
        private final float topLeft;
        private final float topRight;
        private final float bottomLeft;
        private final float bottomRight;
    }
}
