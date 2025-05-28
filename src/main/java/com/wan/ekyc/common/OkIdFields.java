package com.wan.ekyc.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OkIdFields {
    private String documentClassCode; // FieldType 0
    private String issuingStateCode; // FieldType 1
    private String documentNumber; // FieldType 2
    private String dateOfExpiry; // FieldType 3
    private String dateOfIssue; // FieldType 4
    private String dateOfBirth; // FieldType 5
    private String placeOfBirth; // FieldType 6
    private String surname; // FieldType 8
    private String givenNames; // FieldType 9
    private String nationality; // FieldType 11
    private String sex; // FieldType 12
    private String address; // FieldType 17
    private String surnameAndGivenName; // FieldType 25
    private String nationalityCode; // FieldType 26
    private String mrzType; // FieldType 35
    private String optionalData; // FieldType 36
    private String issuingStateName; // FieldType 38
    private String placeOfIssue; // FieldType 39
    private String mrzStrings; // FieldType 51
    private String regCertRegNumber; // FieldType 57
    private String serialNumber; // FieldType 168
    private String age; // FieldType 185
    private String religion; // FieldType 363
    private String ageAtIssue; // FieldType 522
    private String yearsSinceIssue; // FieldType 523

    // extra
    private String address1;
    private String address2;
    private String address3;
}
