package com.mry5l.apiclientsdk.model.enums;

public enum InterfaceInfoStatusEnum {

    OFFLINE("关闭", 0),

    ONLINE("上线", 1);


    private final String text;

    private final int value;

    InterfaceInfoStatusEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public int getValue() {
        return value;
    }
}
