package com.example.ploygardenplants.enums;

public enum StatusCode {

    PURCHASE_ORDER("PO", "", "Purchse Order"),
    GOODS_RECEIPT("GR", "", "Goods Receipt"),
    INVOICE("INV", "380", "Invoice"),
    DEBIT_NOTE("DN", "80", "Debit Note"),
    CREDIT_NOTE("CN", "81", "Credit Note"),
    E_TAX_INVOICE("TIV", "388", "TAX Invoice"),
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
