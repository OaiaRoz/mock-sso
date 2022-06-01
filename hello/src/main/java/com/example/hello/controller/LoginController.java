package com.example.hello.controller;

import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/")
public class LoginController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	
	@PostMapping(path = "login", produces = "application/json")
	public ResponseEntity<?> login(
			@RequestParam(name = "from", required = false) String from, 
			@RequestParam(name = "SAMLResponse", required = false) String SAMLResponse) throws Exception 
	{
		if (SAMLResponse == null) {
			ResponseEntity<String> response = new ResponseEntity<>("SAMLResponse not available", HttpStatus.UNAUTHORIZED);
	        return response;
		}
		
		LOGGER.info("from=" + from);
		LOGGER.info("SAMLResponse=" + SAMLResponse);
		
		LOGGER.info("Base64 decoding and inflating SAML message");
		byte[] decodedBytes = Base64.getDecoder().decode(SAMLResponse);
		String samlDecoded = new String(decodedBytes);
		
		LOGGER.info("SAML-Decoded :: " + samlDecoded);
		
		ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.OK);
        return response;
	}

}
