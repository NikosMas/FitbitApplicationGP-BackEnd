package com.fitbit.grad.services.notification;

import com.fitbit.grad.config.MailInfoProperties;
import com.fitbit.grad.controller.tabs.HeartRateNotificationTab;
import com.fitbit.grad.models.HeartRateCategoryEnum;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final MailInfoProperties mailProp;

    @Autowired
    public HeartRateNotificationService(MailInfoProperties mailProp) {
        this.mailProp = mailProp;
    }

    public void email(String mail, Long minutes, HeartRateCategoryEnum category, Long min, Long max)
            throws MessagingException {

        final String subject = mailProp.getMailSubject();
        final String text = "Hello \n\nThese dates declared in this file describe "
                + "the Heart-Rate of the user that was at its " + category.d() + " which means between "
                + min + " and " + max + " " + "for more than " + minutes + " minutes during these days per day. " +
                "\n\nHope we've helped. Keep on \nSincerely, \nFitbit Application";

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

        // sends the notification mail
        Transport.send(message);
    }
}
