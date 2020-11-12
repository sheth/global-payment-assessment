package com.dhavalsheth.boot.rest.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(DeviceId.class)
public class Device {

    @Id
    @Setter
    @Getter
    private String serialNo;

    @Id
    @Setter
    @Getter
    private String machineCode;

    @Setter
    @Getter
    private String name;
}
