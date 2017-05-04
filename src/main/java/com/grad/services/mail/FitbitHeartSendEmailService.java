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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grad.config.MailInfoProperties;

/**
 * @author nikos_mas
 */

@Service
public class FitbitHeartSendEmailService {

	private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");
	
	@Autowired
	private MailInfoProperties appProperties;

	public void email(String mail, String minutes) throws MessagingException {
		
		final String subject = "Fitbit app Info mails";
		final String text = "Goodmorning, " +'\n'+'\n'
							+ "These dates declared in this file describe "
							+ "the Heart-Rate of the user which was at its Peak which means between 160 and 220 "
							+ "for more than "+minutes+" minutes during these days per day." +'\n'
							+ "Check it out please as soon as possible and take care." +'\n'+'\n'
							+ "Hope we've helped. Keep on";

		Properties properties = new Properties();
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");

		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(appProperties.getUsername(), appProperties.getPassword());
			}
		});
		
		Multipart multipart = new MimeMultipart();
		BodyPart messageBodyPart = new MimeBodyPart();
		Message message = new MimeMessage(session);
		DataSource source = new FileDataSource(appProperties.getFileName());

		message.setFrom(new InternetAddress(appProperties.getSendFrom()));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
		message.setSubject(subject);
		message.setSentDate(new Date());
		messageBodyPart.setText(text);
		multipart.addBodyPart(messageBodyPart);

		messageBodyPart = new MimeBodyPart();
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(appProperties.getFileName());
		multipart.addBodyPart(messageBodyPart);
		message.setContent(multipart);
		
		Transport.send(message);
	}
}
