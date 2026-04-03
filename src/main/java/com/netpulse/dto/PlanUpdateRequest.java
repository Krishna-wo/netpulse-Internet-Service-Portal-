package com.netpulse.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanUpdateRequest {

    @NotNull(message = "Plan ID is required")
    private Long planId;
}