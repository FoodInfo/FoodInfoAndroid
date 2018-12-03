package com.xpn.foodinfo.models;

import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class User {
    @ToString.Exclude
    private final String id;
    private final String displayName;
    private final String email;
    private final String photoUrl;
}
