package io.wisoft.capstonedesign.domain.address.persistence;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "postcode")
    private String postcode;

    @Column(name = "post_address")
    private String postAddress;

    @Column(name = "detail_address")
    private String detailAddress;

    @Column(name = "extra_address")
    private String extraAddress;


    @Builder
    public static Address createAddress(
            final String postcode,
            final String postAddress,
            final String detailAddress,
            final String extraAddress
    ) {
        final Address address = new Address();
        address.postcode = postcode;
        address.postAddress = postAddress;
        address.detailAddress = detailAddress;
        address.extraAddress = extraAddress;

        return address;
    }

    public static String translateAddressToString(final Address address) {
        return String.join("", address.getDetailAddress(), address.getExtraAddress(), address.getPostAddress(), address.getPostcode());
    }

}
