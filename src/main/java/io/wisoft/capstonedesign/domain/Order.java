package io.wisoft.capstonedesign.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "ORDERS")
@Getter
public class Order {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "body", columnDefinition = "TEXT")
    private String body;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void setUser(User user) { // 연관관계 편의 메소드
        this.user = user;
        user.getOrders().add(this);
    }
}
