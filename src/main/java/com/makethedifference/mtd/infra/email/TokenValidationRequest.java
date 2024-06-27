package com.makethedifference.mtd.infra.email;

import lombok.Data;

@Data
public class TokenValidationRequest {
    private String email;
    private String token;
}