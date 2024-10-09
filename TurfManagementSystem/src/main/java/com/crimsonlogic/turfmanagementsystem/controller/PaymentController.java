package com.crimsonlogic.turfmanagementsystem.controller;
/**
 * PaymentController handles payment-related HTTP requests.
 * Author: Shradha
 */

import com.crimsonlogic.turfmanagementsystem.dto.PaymentDTO;
import com.crimsonlogic.turfmanagementsystem.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    //inesert payment
    @PostMapping("/pay")
    public ResponseEntity<PaymentDTO> createPayment(@RequestBody PaymentDTO paymentDTO) {
        PaymentDTO createdPayment = paymentService.createPayment(paymentDTO);
        return ResponseEntity.ok(createdPayment);
    }

}
