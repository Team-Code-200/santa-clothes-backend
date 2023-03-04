package io.wisoft.capstonedesign.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SHOP")
@Getter
public class Shop {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private int price;

    @Column(name = "image")
    private String image;

    @Column(name = "body")
    private String body;

    @OneToMany(mappedBy = "shop")
    private List<UserShop> userShops = new ArrayList<>();
}
