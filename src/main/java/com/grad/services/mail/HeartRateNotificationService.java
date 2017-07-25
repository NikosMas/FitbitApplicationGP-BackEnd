package com.grad.services.mail;

import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.grad.config.MailInfoProperties;
import com.grad.controller.HeartRateNotificationController;
import com.grad.domain.HeartRateCategoryEnum;

/**
 * Service about sending email information about user's heart rate according to info given at {@link HeartRateNotificationController}
 * 
 * @author nikos_mas, alex_kak
 */

@Service
public class HeartRateNotificationService {

	@Autowired
	private MailInfoProperties mailProp;

	public void email(String mail, Long minutes, HeartRateCategoryEnum category, Long min, Long max)
			throws MessagingException {

		final String subject = mailProp.getMailSubject();
		final String text = "Goodmorning, " + '\n' + '\n' + "These dates declared in this file describe "
				+ "the Heart-Rate of the user which was at its " + category.d() + " which means between "
				+ min + " and " + max + " " + "for more than " + minutes + " minutes during these days per day." + '\n'
				+ "Check it out please as soon as possible and take care." + '\n' + '\n' + "Hope we've helped. Keep on";

		Properties properties = new Properties();
		properties.put("mail.smtp.starttls.enable", mailProp.getMailSmtpStartEnable());
		properties.put("mail.smtp.auth", mailProp.getMailSmtpAuth());
		properties.put("mail.smtp.host", mailProp.getMailSmtpHost());
		properties.put("mail.smtp.port", mailProp.getMailSmtpPort());

		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailProp.getUsername(), mailProp.getPassword());
			}
		});

		Multipart multipart = new MimeMultipart();
		BodyPart messageBodyPart = new MimeBodyPart();
		Message message = new MimeMessage(session);
		DataSource source = new FileDataSource(mailProp.getFileName());

		message.setFrom(new InternetAddress(mailProp.getSendFrom()));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
		message.setSubject(subject);
		message.setSentDate(new Date());
		messageBodyPart.setText(text);
		multipart.addBodyPart(messageBodyPart);

		messageBodyPart = new MimeBodyPart();
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(mailProp.getFileName());
		multipart.addBodyPart(messageBodyPart);
		message.setContent(multipart);

		Transport.send(message);
	}
}
