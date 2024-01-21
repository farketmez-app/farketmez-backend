package com.mmhb.farketmez.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mmhb.farketmez.dto.EmailReceiverDTO;
import com.mmhb.farketmez.service.ForgotPasswordService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/forgot-password")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ForgotPasswordController {

	private final ForgotPasswordService forgotPasswordService;

	@PostMapping()
	public ResponseEntity<String> sendPasswordResetEmail(@RequestBody EmailReceiverDTO mail) throws Exception {
		forgotPasswordService.sendResetPasswordEmail(mail);
		return ResponseEntity.ok("Reset password e-mail sent successfully!");
	}
}
