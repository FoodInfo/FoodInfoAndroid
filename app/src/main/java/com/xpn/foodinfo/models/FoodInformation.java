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
}
