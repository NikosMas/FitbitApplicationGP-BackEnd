package com.fitbit.grad.services.notification;

import com.fitbit.grad.controller.tabs.HeartRateNotificationTab;
import com.fitbit.grad.models.HeartRateCategoryEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

/**
 * Service about sending email information about user's heart rate according to info given at {@link HeartRateNotificationTab}
 *
 * @author nikos_mas, alex_kak
 */

@Service
public class HeartRateNotificationService {

    private final Environment env;

    @Autowired
    public HeartRateNotificationService(Environment env) {
        this.env = env;
    }

    public void email(String mail, Long minutes, HeartRateCategoryEnum category, Long min, Long max)
            throws MessagingException {

        final String subject = env.getProperty("mailInfo.mailSubject");
        final String text = "Hello \n\nThese dates declared in this file describe "
                + "the Heart-Rate of the user that was at its " + category.d() + " which means between "
                + min + " and " + max + " " + "for more than " + minutes + " minutes during these days per day. " +
                "\n\nHope we've helped. Keep on \nSincerely, \nFitbit Application";

        Properties properties = new Properties();
        properties.put("mail.smtp.starttls.enable", env.getProperty("mailInfo.mailSmtpStartEnable"));
        properties.put("mail.smtp.auth", env.getProperty("mailInfo.mailSmtpAuth"));
        properties.put("mail.smtp.host", env.getProperty("mailInfo.mailSmtpHost"));
        properties.put("mail.smtp.port", env.getProperty("mailInfo.mailSmtpPort"));

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(env.getProperty("mailInfo.username"), env.getProperty("mailInfo.password"));
            }
        });

        Multipart multipart = new MimeMultipart();
        BodyPart messageBodyPart = new MimeBodyPart();
        Message message = new MimeMessage(session);
        DataSource source = new FileDataSource(env.getProperty("mailInfo.fileName"));

        message.setFrom(new InternetAddress(env.getProperty("mailInfo.sendFrom")));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
        message.setSubject(subject);
        message.setSentDate(new Date());
        messageBodyPart.setText(text);
        multipart.addBodyPart(messageBodyPart);

        messageBodyPart = new MimeBodyPart();
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(env.getProperty("mailInfo.fileName"));
        multipart.addBodyPart(messageBodyPart);
        message.setContent(multipart);

        // sends the notification mail
        Transport.send(message);
    }
}
