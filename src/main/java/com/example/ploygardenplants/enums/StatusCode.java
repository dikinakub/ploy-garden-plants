package com.example.ploygardenplants.enums;

public enum StatusCode {

    STATUS01("STATUS01", "New Order", "ออเดอร์ใหม่"),
    STATUS02("STATUS02", "Packing", "แพ็คสินค้า"),
    STATUS03("STATUS03", "Deposit", "มัดจำ"),
    STATUS04("STATUS04", "Paid", "จ่ายแล้ว"),
    STATUS05("STATUS05", "Not paying", "ไม่จ่าย"),
    STATUS06("STATUS06", "Cancel", "ยกเลิก"),
    STATUS07("STATUS07", "Complete", "สมบูรณ์"),
    NULL(null, "", "");

    private final String statusCode;
    private final String statusDescEn;
    private final String statusDescTh;

    private StatusCode(String statusCode, String statusDescEn, String statusDescTh) {
        this.statusCode = statusCode;
        this.statusDescEn = statusDescEn;
        this.statusDescTh = statusDescTh;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusDescEn() {
        return statusDescEn;
    }

    public String getStatusDescTh() {
        return statusDescTh;
    }

    public static StatusCode fromStatusCode(String statusCode) {
        if (statusCode != null) {
            for (StatusCode status : StatusCode.values()) {
                if (statusCode.equals(status.getStatusCode())) {
                    return status;
                }
            }
        }
        return StatusCode.NULL;
    }

}
