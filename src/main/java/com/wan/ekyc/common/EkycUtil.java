package com.wan.ekyc.common;

import com.wan.ekyc.dto.innovative.OkIdResponse;
import com.wan.ekyc.dto.innovative.child.FieldMap;
import com.wan.ekyc.dto.innovative.child.ResultItem;
import com.wan.ekyc.dto.innovative.child.VerifiedFields;

import java.util.ArrayList;
import java.util.List;

public class EkycUtil {

    public static OkIdFields translateOkIdFields(OkIdResponse res) {
        OkIdFields fields = new OkIdFields();

        for (ResultItem ri : res.getResult()) {
            VerifiedFields vf = ri.getVerifiedFields();
            if (vf != null) {
                List<FieldMap> fieldMaps = vf.getFieldMaps();
                for (FieldMap f : fieldMaps) {
                    int type = f.getFieldType();
                    String value = f.getFieldVisual();

                    switch (type) {
                        case 0:
                            fields.setDocumentClassCode(value);
                        case 1:
                            fields.setIssuingStateCode(value);
                            break;
                        case 2:
                            fields.setDocumentNumber(value);
                            break;
                        case 3:
                            fields.setDateOfExpiry(value);
                            break;
                        case 4:
                            fields.setDateOfIssue(value);
                            break;
                        case 5:
                            fields.setDateOfBirth(value);
                            break;
                        case 6:
                            fields.setPlaceOfBirth(value);
                            break;
                        case 8:
                            fields.setSurname(value);
                            break;
                        case 9:
                            fields.setGivenNames(value);
                            break;
                        case 11:
                            fields.setNationality(value);
                            break;
                        case 12:
                            fields.setSex(getGender(value));
                            break;
                        case 17:
                            fields.setAddress(value);
                            break;
                        case 25:
                            fields.setSurnameAndGivenName(value.replace("^", " "));
                            break;
                        case 26:
                            fields.setNationalityCode(value);
                            break;
                        case 35:
                            fields.setMrzType(value);
                            break;
                        case 36:
                            fields.setOptionalData(value);
                            break;
                        case 38:
                            fields.setIssuingStateName(value);
                            break;
                        case 39:
                            fields.setPlaceOfIssue(value);
                            break;
                        case 51:
                            fields.setMrzStrings(value);
                            break;
                        case 57:
                            fields.setRegCertRegNumber(value);
                            break;
                        case 168:
                            fields.setSerialNumber(value);
                            break;
                        case 185:
                            fields.setAge(value);
                            break;
                        case 363:
                            fields.setReligion(value);
                            break;
                        case 522:
                            fields.setAgeAtIssue(value);
                            break;
                        case 523:
                            fields.setYearsSinceIssue(value);
                            break;
                        default:
                            break;
                    }
                }
            }
        }

        if (fields.getAddress() != null) {
            List<String> address = splitAddress(fields.getAddress());
            fields.setAddress1(address.get(0));
            fields.setAddress2(address.size() > 1 ? address.get(1) : "");
            fields.setAddress3(address.size() > 2 ? address.get(2) : "");
        }

        return fields;
    }

    private static String getGender(String sex) {
       return switch (sex) {
           case "M" -> "Male";
           case "F" -> "Female";
           default -> "";
       };
    }

    private static List<String> splitAddress(String address) {
        List<String> lines = new ArrayList<>();

        String[] split = address.split("[,^]");

        StringBuilder line3 = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            if (i == 2 || i > 2) {
                line3.append(split[i]);
            }
            lines.add(split[i]);
        }
        lines.add(line3.toString());

        return lines;
    }

}
