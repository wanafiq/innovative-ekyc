package com.wan.ekyc.common;

public class Constant {
    // Document Category
    public static final String NON_PASSPORT = "NON_PASSPORT";
    public static final String PASSPORT = "PASSPORT";

    // Document Type
    public static final String ID = "ID";
    public static final String DRIVING_LICENSE = "DRIVING_LICENSE";
    public static final String VISA = "VISA";

    // User Status
    public static final String PENDING_EKYC = "PENDING_EKYC";
    public static final String PENDING_EKYC_REVIEW = "PENDING_EKYC_REVIEW";
    public static final String COMPLETED_EKYC = "COMPLETED_EKYC";

    // eKYC Type
    public static final String CREATE_JOURNEY = "CREATE_JOURNEY";
    public static final String OKAY_ID = "OKAY_ID";
    public static final String OKAY_DOC = "OKAY_DOC";
    public static final String OKAY_FACE = "OKAY_FACE";
    public static final String OKAY_LIVE = "OKAY_LIVE";
    public static final String COMPLETE_JOURNEY = "COMPLETE_JOURNEY";
    public static final String GET_SCORECARD = "GET_SCORECARD";

    // eKYC Audit Status
    public static final String IN_PROGRESS = "IN_PROGRESS";
    public static final String FAILED = "FAILED";
    public static final String SUCCESS = "SUCCESS";
}
