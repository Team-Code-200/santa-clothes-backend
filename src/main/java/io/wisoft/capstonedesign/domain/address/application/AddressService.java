package io.wisoft.capstonedesign.domain.address.application;

import io.wisoft.capstonedesign.domain.address.persistence.Address;
import io.wisoft.capstonedesign.domain.address.persistence.AddressRepository;
import io.wisoft.capstonedesign.domain.address.web.dto.CreateAddressRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    @Transactional
    public Long join(final CreateAddressRequest request) {

        Address address = Address.builder()
                .postcode(request.postcode())
                .postAddress(request.postAddress())
                .detailAddress(request.detailAddress())
                .extraAddress(request.extraAddress())
                .build();

        addressRepository.save(address);
        return address.getId();
    }

}
