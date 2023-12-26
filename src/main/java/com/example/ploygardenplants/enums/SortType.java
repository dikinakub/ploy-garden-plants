package com.example.ploygardenplants.enums;

public enum SortType {

    ASC("ASC"), DESC("DESC");

    private String value;

    private SortType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static SortType fromValue(String value) {
        if (value != null) {
            for (SortType val : SortType.values()) {
                if (val.getValue().equalsIgnoreCase(value)) {
                    return val;
                }
            }
        }
        return SortType.ASC;
    }

    public static SortType invert(SortType value) {
        if (SortType.ASC.equals(value)) {
            return SortType.DESC;
        }
        return SortType.ASC;
    }

}
