package io.wisoft.capstonedesign.domain.payment.web;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.response.Payment;
import io.wisoft.capstonedesign.domain.payment.persistence.OrderPayment;
import io.wisoft.capstonedesign.domain.payment.persistence.PaymentRepository;
import io.wisoft.capstonedesign.domain.usershop.application.UserShopService;
import io.wisoft.capstonedesign.domain.usershop.persistence.UserShop;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    @Value("${iamport.api_key}")
    private String apiKey;

    @Value("${iamport.api_secret}")
    private String apiSecret;

    private final UserShopService userShopService;
    private final PaymentRepository paymentRepository;

    @PostMapping("/payment/callback/{id}")
    public ResponseEntity<?> callbackReceive(@RequestBody final Map<String, Object> model,
                                             @PathVariable("id") final Long orderId) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
        JSONObject jsonObject = new JSONObject();

        try {
            String impUid = (String) model.get("imp_uid");
            String merchantUid = (String) model.get("merchant_uid");
            boolean success = (Boolean) model.get("success");
            String errorMsg = (String) model.get("error_msg");

            int amount = (int) model.get("paid_amount");
            String buyerEmail = (String) model.get("buyer_email");
            String buyerName = (String) model.get("buyer_name");
            String buyerAddress = (String) model.get("buyer_addr");
            String postcode = (String) model.get("buyer_postcode");

            if (success) {
                IamportClient client = new IamportClient(apiKey, apiSecret);
//                IamportResponse<Payment> response = client.paymentByImpUid(impUid);
                Payment payment = client.paymentByImpUid(impUid).getResponse();

                UserShop userShop = userShopService.findById(orderId);
                OrderPayment orderPayment = OrderPayment.createPayment(amount, buyerEmail, buyerName, buyerAddress, postcode, userShop);
                paymentRepository.save(orderPayment);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("ok", responseHeaders, HttpStatus.OK);
    }
}
