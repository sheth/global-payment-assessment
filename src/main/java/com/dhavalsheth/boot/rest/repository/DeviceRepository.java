package com.dhavalsheth.boot.rest.repository;

import com.dhavalsheth.boot.rest.model.Device;
import com.dhavalsheth.boot.rest.model.DeviceId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, DeviceId> {


}