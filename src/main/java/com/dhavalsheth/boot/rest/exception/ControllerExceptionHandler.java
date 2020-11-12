package com.dhavalsheth.boot.rest.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {
	DeviceApiError _er001 = new DeviceApiError(
			"The machine code is incorrect. Check the Machine code you provided and try again.",
			"ER001",
			"machine.code.invalid"
	);

	DeviceApiError _er002 = new DeviceApiError(
			"The machine code does not match our records.",
			"ER002",
			"machine.code.not.found"
	);
	DeviceApiError _er003 = new DeviceApiError(
			"The serial number entered can include a - z, A - Z, 0 - 9 and hyphen. Please correct your entry.",
			"ER003",
			"serial.number.invalid"
	);
	DeviceApiError _er004 = new DeviceApiError(
			"The serial number does not match our records.",
			"ER004",
			"serial.number.not.found"
	);
	DeviceApiError _er005 = new DeviceApiError(
			"Something went wrong",
			"ER005",
			"something.went.wrong"
	);

	@ExceptionHandler(SerialNoFormatIncorrectException.class)
	public ResponseEntity<Object> handleNotFound(SerialNoFormatIncorrectException ex) {
		log.error("SerialNoFormatIncorrectException " + ex);
		DeviceApiErrorEnvelope errorEnvelope = new DeviceApiErrorEnvelope(HttpStatus.BAD_REQUEST, _er003);
		return new ResponseEntity<>(errorEnvelope, new HttpHeaders(), errorEnvelope.getStatus());
	}

	@ExceptionHandler(MachineCodeAndSerialNoNotFound.class)
	public ResponseEntity<Object> handleNotFound(MachineCodeAndSerialNoNotFound ex) {
		log.error("DeviceNotFoundException " + ex);
		ArrayList<DeviceApiError> errors = new ArrayList<>();
		errors.add(_er002);
		errors.add(_er004);
		DeviceApiErrorEnvelope errorEnvelope = new DeviceApiErrorEnvelope(HttpStatus.BAD_REQUEST, errors);
		return new ResponseEntity<>(errorEnvelope, new HttpHeaders(), errorEnvelope.getStatus());
	}
	@ExceptionHandler(MachineCodeBlankException.class)
	public ResponseEntity<Object> handleNotFound(MachineCodeBlankException ex) {
		log.error("MachineCodeBlankException " + ex);
		ArrayList<DeviceApiError> errors = new ArrayList<>();
		errors.add(_er001);
		DeviceApiErrorEnvelope errorEnvelope = new DeviceApiErrorEnvelope(HttpStatus.BAD_REQUEST, errors);
		return new ResponseEntity<>(errorEnvelope, new HttpHeaders(), errorEnvelope.getStatus());
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
		log.error("Exception " + ex);
		log.error("Request " + request);
		ArrayList<DeviceApiError> errors = new ArrayList<>();
		errors.add(_er005);
		DeviceApiErrorEnvelope errorEnvelope = new DeviceApiErrorEnvelope(HttpStatus.BAD_REQUEST, errors);
		return new ResponseEntity<>(errorEnvelope, new HttpHeaders(), errorEnvelope.getStatus());
	}}

class DeviceApiError {
	@Getter
	@Setter
	private String message;
	@Getter
	@Setter
	private String errorCode;
	@Getter
	@Setter
	private String resourceKey;

	public DeviceApiError(final String message, final String errorCode, final String resourceKey) {
		super();
		this.message = message;
		this.errorCode = errorCode;
		this.resourceKey = resourceKey;
	}
}

class DeviceApiErrorEnvelope {

	@Getter
	@Setter
	private HttpStatus status;

	@Getter
	@Setter
	ArrayList<DeviceApiError> errors;

	DeviceApiErrorEnvelope(HttpStatus status, DeviceApiError error) {
		this.status = status;
		errors = new ArrayList<>();
		errors.add(error);
	}

	DeviceApiErrorEnvelope(HttpStatus status, ArrayList<DeviceApiError> errors) {
		this.status = status;
		this.errors = errors;
	}
}