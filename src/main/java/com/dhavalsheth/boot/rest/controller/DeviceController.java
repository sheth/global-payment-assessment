package com.dhavalsheth.boot.rest.controller;

import com.dhavalsheth.boot.rest.exception.MachineCodeAndSerialNoNotFound;
import com.dhavalsheth.boot.rest.exception.MachineCodeBlankException;
import com.dhavalsheth.boot.rest.exception.SerialNoFormatIncorrectException;
import com.dhavalsheth.boot.rest.model.Device;
import com.dhavalsheth.boot.rest.model.DeviceId;
import com.dhavalsheth.boot.rest.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Device Controller exposes a series of REST-ful endpoints
 */
@RestController
public class DeviceController {

    @Autowired
    private DeviceRepository DeviceRepository;


    /**
     * Get Device using serialNo and machineCode
     * Returns HTTP 404 if Device not found
     *
     * @param serialNo    {@link DeviceId}
     * @param machineCode {@link DeviceId}
     * @return Device - retrieved Device
     */
    @RequestMapping(value = "/rest/devices/serialNo/{serialNo}/machineCode/{machineCode}", method = RequestMethod.GET)
    public Device getDevice(@PathVariable("serialNo") String serialNo,
                            @PathVariable("machineCode") String machineCode) {

        DeviceId id = new DeviceId(serialNo, machineCode);
        Device device = DeviceRepository.findOne(id);

        if (null == device) {
            throw new MachineCodeAndSerialNoNotFound();
        }

        return device;
    }


    /**
     * Gets all Devices.
     *
     * @return the Devices
     */
    @RequestMapping(value = "/rest/devices", method = RequestMethod.GET)
    public List<Device> getDevices() {

        return DeviceRepository.findAll();
    }


    /**
     * Create a new Device and return in response with HTTP 201
     *
     * @param device       {@link RequestBody}
     * @param httpResponse {@link HttpServletResponse}
     * @param request      {@link WebRequest}
     * @return Device
     */
    @RequestMapping(value = {"/rest/devices"}, method = {RequestMethod.POST})
    public Device createDevice(@RequestBody Device device, HttpServletResponse httpResponse, WebRequest request) {

        if(!device.getSerialNo().matches("[A-Za-z0-9]*-[A-Za-z0-9]*")) {
            throw new SerialNoFormatIncorrectException();
        }
        if(device.getMachineCode().trim().isEmpty()) {
            throw new MachineCodeBlankException();
        }
        Device createdDevice = DeviceRepository.save(device);
        httpResponse.setStatus(HttpStatus.CREATED.value());
        httpResponse.setHeader("Location",
                String.format("%s/rest/devices/%s",
                request.getContextPath(),
                device.toString()));

        return createdDevice;
    }


    /**
     * Update Device with given Device id.
     *
     * @param Device      the Device
     * @param serialNo    {@link DeviceId}
     * @param machineCode {@link DeviceId}
     */
    @RequestMapping(value = {"/rest/devices/serialNo/{serialNo}/machineCode/{machineCode}"},
            method = {RequestMethod.PUT})
    public void updateDevice(@RequestBody Device Device,
                             @PathVariable("serialNo") String serialNo,
                             @PathVariable("machineCode") String machineCode,
                             HttpServletResponse httpResponse) {

        if (!DeviceRepository.exists(new DeviceId(serialNo, machineCode))) {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
        } else {
            DeviceRepository.save(Device);
            httpResponse.setStatus(HttpStatus.NO_CONTENT.value());
        }
    }


    /**
     * Deletes the Device with given Device id if it exists and returns HTTP204.
     *
     * @param serialNo    {@link DeviceId}
     * @param machineCode {@link DeviceId}
     */
    @RequestMapping(value = "/rest/devices/serialNo/{serialNo}/machineCode/{machineCode}",
            method = RequestMethod.DELETE)
    public void removeDevice(@PathVariable("serialNo") String serialNo,
                             @PathVariable("machineCode") String machineCode,
                             HttpServletResponse httpResponse) {
        DeviceId id = new DeviceId(serialNo, machineCode);
        if (DeviceRepository.exists(id)) {
            DeviceRepository.delete(id);
        }

        httpResponse.setStatus(HttpStatus.NO_CONTENT.value());
    }

}