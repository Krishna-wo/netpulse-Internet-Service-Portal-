package com.netpulse.service;

import com.netpulse.dto.CustomerProfileResponse;
import com.netpulse.model.Customer;
import com.netpulse.model.Plan;
import com.netpulse.repository.CustomerRepository;
import com.netpulse.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PlanRepository planRepository;

    // ✅ Get customer profile with plan details
    public CustomerProfileResponse getProfile(String email) {
        Customer customer = customerRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Plan plan = customer.getCurrentPlan();

        return CustomerProfileResponse.builder()
                .accountId(customer.getAccountId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getUser().getEmail())
                .phone(customer.getUser().getPhone())
                .address(customer.getAddress())
                .city(customer.getCity())
                .state(customer.getState())
                .pincode(customer.getPincode())
                .kycStatus(customer.getKycStatus().name())
                // ✅ PLAN INFO
                .planName(plan != null ? plan.getName() : null)
                .planId(plan != null ? plan.getId() : null)
                .downloadSpeed(plan != null ? plan.getDownloadSpeedMbps() : 0)
                .uploadSpeed(plan != null ? plan.getUploadSpeedMbps() : 0)
                .dataCap(plan != null ? plan.getDataCapGb() : 0)
                .monthlyPrice(plan != null ? plan.getMonthlyPrice() : null)
                .joinDate(customer.getJoinDate())
                .build();
    }

    // ✅ UPDATE CUSTOMER PLAN
    @Transactional
    public void updateCustomerPlan(String email, Long planId) {
        Customer customer = customerRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Plan newPlan = planRepository.findById(planId)
                .filter(Plan::isActive)
                .orElseThrow(() -> new RuntimeException("Selected plan not found or inactive"));

        Plan oldPlan = customer.getCurrentPlan();
        customer.setCurrentPlan(newPlan);
        customerRepository.save(customer);

        log.info("Customer {} plan changed from {} to {}", email,
                oldPlan != null ? oldPlan.getName() : "None", newPlan.getName());
    }

    // ✅ GET CURRENT PLAN
    public Plan getCustomerCurrentPlan(String email) {
        Customer customer = customerRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        return customer.getCurrentPlan();
    }
}