package io.wisoft.capstonedesign.domain.address.web;

import io.wisoft.capstonedesign.domain.address.application.AddressService;
import io.wisoft.capstonedesign.domain.address.web.dto.CreateAddressRequest;
import io.wisoft.capstonedesign.domain.address.web.dto.CreateAddressResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AddressController {

    private final AddressService addressService;

    public AddressController(final AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/api/submit/new")
    public CreateAddressResponse submitForm(@ModelAttribute @Valid final CreateAddressRequest request) {
        Long id = addressService.join(request);
        return new CreateAddressResponse(id);
    }

}

