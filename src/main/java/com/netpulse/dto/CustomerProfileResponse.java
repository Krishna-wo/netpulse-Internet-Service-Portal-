package com.netpulse.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerProfileResponse {
    private String accountId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String pincode;
    private String kycStatus;

    // ✅ PLAN DETAILS
    private String planName;
    private Long planId;
    private int downloadSpeed;
    private int uploadSpeed;
    private int dataCap;
    private BigDecimal monthlyPrice;

    private LocalDate joinDate;
}