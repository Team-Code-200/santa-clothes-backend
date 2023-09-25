package io.wisoft.capstonedesign.domain.address.web;

import io.wisoft.capstonedesign.domain.address.application.AddressService;
import io.wisoft.capstonedesign.domain.address.web.dto.CreateAddressRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }


    @GetMapping("/api/address")
    public String showAddressSearchForm() {
        return "address";
    }

    @GetMapping("/api/submit")
    public String showSubmitForm() {
        return "submit";
    }

    @PostMapping("/api/submit/new")
    public String submitForm(@ModelAttribute @Valid CreateAddressRequest request) {
        addressService.join(request);
        return "submit";
    }

}

