package com.mmhb.farketmez.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.mmhb.farketmez.dto.EmailReceiverDTO;
import com.mmhb.farketmez.dto.ResetPasswordDTO;
import com.mmhb.farketmez.exception.OperationNotAllowedException;
import com.mmhb.farketmez.model.User;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ForgotPasswordService {
	private final UserService userService;
	private final SettingsService settingService;
	private static Map<String, String> resetRequests = new HashMap<>();

	public void sendResetPasswordEmail(EmailReceiverDTO emailReceiver) throws Exception {
		if (userService.findByMail(emailReceiver.getMail()) == null)
			throw new OperationNotAllowedException("User with " + emailReceiver.getMail() + " does not exist!");

		String resetHash = UUID.randomUUID().toString();
		resetRequests.put(resetHash, emailReceiver.getMail());
		JavaMailSender mailSender = getJavaMailSender();
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
		String subject = "You may reset your password.";
		String resetPasswordUrl = settingService.findValueByKey("app_host") + ":"
				+ settingService.findValueByKey("app_port") + "/forgot-password/update?hash=" + resetHash;
		String mailText = "Please click the link below to reset your password. <br> <a href=\"" + resetPasswordUrl
				+ "\">Reset Password</a>";
		System.out.println("SMTP Host: " + settingService.findValueByKey("mail_host"));
		System.out.println("SMTP Port: " + settingService.findValueByKey("mail_port"));
		System.out.println("SMTP Username: " + settingService.findValueByKey("mail_username"));
		messageHelper.setSubject(subject);
		messageHelper.setText(mailText, true);
		messageHelper.setTo(emailReceiver.getMail());
		mailSender.send(message);
	}

	public void updatePassword(ResetPasswordDTO resetPasswordDto) throws Exception {
		String hash = resetPasswordDto.getHash();
		if (!resetPasswordDto.getPassword().equals(resetPasswordDto.getConfirmPassword()))
			throw new OperationNotAllowedException("Password and confirm password do not match.");

		if (!resetRequests.containsKey(hash))
			throw new OperationNotAllowedException("Invalid or expired reset token.");

		String email = resetRequests.get(hash);
		User user = userService.findByMail(email);
		if (user == null) {
			throw new OperationNotAllowedException("User not found.");
		}

		user.setPassword(resetPasswordDto.getPassword());
		userService.updateUser(user);
		resetRequests.remove(hash);
	}

	private JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(settingService.findValueByKey("mail_host"));
		mailSender.setPort(Integer.parseInt(settingService.findValueByKey("mail_port")));
		mailSender.setUsername(settingService.findValueByKey("mail_username"));
		mailSender.setPassword(settingService.findValueByKey("mail_password"));

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");

		return mailSender;
	}

	@Scheduled(fixedDelay = 300000)
	private void clearResetRequests() {
		resetRequests.clear();
	}
}
