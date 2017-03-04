package com.grad.heart;

import java.util.Date;
import java.util.List;
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
import org.springframework.stereotype.Service;

/**
 * set up mail texts subject and other properties and send it.
 * this mail includes heart data of heart-zone at peak
 * 
 * @author nikos_mas
 *
 */

@Service
public class FitbitHeartSendEmail {

	// fill with your data 
	private static final String USERNAME = "   ";
	private static final String PASSWORD = "   ";
	private static final String SEND_TO = "   ";
	private static final String SEND_FROM = "   ";
	
	static Logger log = LoggerFactory.getLogger("Fitbit application");
	
	public void email(List<String> peakDates) throws MessagingException {
		
		final Date date = new Date();
		final String username = USERNAME;
		final String password = PASSWORD;
		final String sendto = SEND_TO;
		final String sendfrom = SEND_FROM;
		final String subject = "Fitbit app Info mails";
		final String file = "heartRatePeaks.txt";
		final String text = "Goodmorning, " +'\n'+'\n'
							+ "these dates declared in this file describe "
							+ "the Heart-Rate of the user which was at its Peak which means between 160 and 220 "
							+ "for more than 35 minutes during these days per day." +'\n'
							+ "Check it out please as soon as possible and take care." +'\n'+'\n'
							+ "Hope we've helped. Keep on";
		
		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}});
		Multipart multipart = new MimeMultipart();
		BodyPart messageBodyPart = new MimeBodyPart();
		Message message = new MimeMessage(session);
		DataSource source = new FileDataSource(file);
		
		message.setFrom(new InternetAddress(sendfrom));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendto));
		message.setSubject(subject);
		message.setSentDate(date);
        messageBodyPart.setText(text);
        multipart.addBodyPart(messageBodyPart);
        messageBodyPart = new MimeBodyPart();
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(file);
        multipart.addBodyPart(messageBodyPart);
        message.setContent(multipart);
		Transport.send(message);
		
		log.info("-> MAIL SUCCESSFULLY SENT <-");    
	}
}
