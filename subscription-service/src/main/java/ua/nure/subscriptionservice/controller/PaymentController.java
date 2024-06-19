package ua.nure.subscriptionservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.nure.subscriptionservice.model.Payment;
import ua.nure.subscriptionservice.resolver.UserId;
import ua.nure.subscriptionservice.response.ResponseBody;
import ua.nure.subscriptionservice.service.IPaymentService;

import java.util.UUID;

@RestController
@RequestMapping("/api/subscription-service/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final IPaymentService paymentService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-all")
    public ResponseEntity<ResponseBody> findAllPayments(){
        return ResponseEntity.ok(new ResponseBody
                (paymentService.findAllPayments()));
    }

    @PreAuthorize("!hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_PREMIUM_USER' )")
    @GetMapping("/by-user-id")
    public ResponseEntity<ResponseBody> findPaymentsByUserId(@UserId UUID userId) {
        return ResponseEntity.ok(new ResponseBody(paymentService.findPaymentsByUserId(userId)));
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<ResponseBody> findPaymentById(@PathVariable UUID paymentId) {
        return ResponseEntity.ok(new ResponseBody(
                paymentService.findPaymentById(paymentId)
        ));
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseBody> createPayment(@RequestBody Payment payment) {
        return ResponseEntity.ok(new ResponseBody(
                paymentService.createPayment(payment)));
    }

    @DeleteMapping("/{paymentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePayment(@PathVariable UUID paymentId) {
        paymentService.deletePayment(paymentId);
    }

    @PutMapping("/{paymentId}")
    public ResponseEntity<ResponseBody> updatePayment(@PathVariable UUID paymentId, @RequestBody Payment updatedPayment) {
        updatedPayment.setPaymentId(paymentId);
        return ResponseEntity.ok(new ResponseBody(
                paymentService.updatePayment(updatedPayment)));
    }


}
