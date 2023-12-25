package com.example.ploygardenplants.enums;

public enum LogSubject {

    REF_ID("RefID"),
    TIME("Time"),
    RESULT("Result"),
    REQUEST("Request"),
    RESPONSE("Response"),
    STATUS("Status"),
    IP("IP"),
    PATH("Path"),
    TYPE("Type"),
    MESSAGE("Message"),
    MESSAGE_CODE("MessageCode"),
    PARAMETER("Parameter"),
    NAME("Name"),
    EVENT("Event"),
    CHANNEL("Channel"),
    PAYLOAD("Payload"),
    REF_KEY("RefKey"),
    NULL("");

    private String desc;

    private LogSubject(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public static LogSubject fromDesc(String desc) {
        if (desc != null) {
            for (LogSubject status : LogSubject.values()) {
                if (status.getDesc().equals(desc)) {
                    return status;
                }
            }
        }
        return LogSubject.NULL;
    }
}
