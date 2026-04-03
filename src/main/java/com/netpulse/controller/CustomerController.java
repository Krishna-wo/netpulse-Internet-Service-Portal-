package com.netpulse.controller;

import com.netpulse.dto.ApiResponse;
import com.netpulse.dto.CustomerProfileResponse;
import com.netpulse.dto.PlanUpdateRequest;
import com.netpulse.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CustomerController {

    private final CustomerService customerService;

    // ✅ Get customer profile
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse> getProfile(Authentication authentication) {
        String email = authentication.getName();
        CustomerProfileResponse profile = customerService.getProfile(email);
        return ResponseEntity.ok(ApiResponse.ok("Profile fetched", profile));
    }

    // ✅ NEW: Update customer plan
    @PostMapping("/plan")
    public ResponseEntity<ApiResponse> updatePlan(
            Authentication authentication,
            @RequestBody PlanUpdateRequest request) {

        String email = authentication.getName();
        customerService.updateCustomerPlan(email, request.getPlanId());

        return ResponseEntity.ok(ApiResponse.ok("Plan updated successfully", null));
    }

    // ✅ NEW: Get current plan
    @GetMapping("/plan")
    public ResponseEntity<ApiResponse> getCurrentPlan(Authentication authentication) {
        String email = authentication.getName();
        var plan = customerService.getCustomerCurrentPlan(email);

        return ResponseEntity.ok(ApiResponse.ok("Current plan fetched", plan));
    }
}