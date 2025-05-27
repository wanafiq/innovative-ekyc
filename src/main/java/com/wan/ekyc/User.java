package com.wan.ekyc;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private String id;
    private String email;
    @JsonIgnore
    private String password;
    private String fullName;
    private String idNo;
    private String address1;
    private String address2;
    private String address3;
    private String zipCode;
    private String country;
    private String status;
    private String journeyId;
}
