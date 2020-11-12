package com.dhavalsheth.boot.rest.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class DeviceId  implements Serializable {
    @Setter
    @Getter
    private String serialNo;

    @Setter
    @Getter
    private String machineCode;

    // default constructor
    public DeviceId() {
    }

    public DeviceId(String serialNumber, String machineCode) {
        this.serialNo = serialNumber;
        this.machineCode = machineCode;
    }
}