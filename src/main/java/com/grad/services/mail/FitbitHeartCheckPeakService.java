package com.grad.services.mail;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.grad.config.MailInfoProperties;
import com.grad.heart.repository.FitbitHeartZoneRepo;

/**
 * @author nikos_mas
 */

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class FitbitHeartCheckPeakService {

	private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");

	@Autowired
	private MailInfoProperties properties;

	@Autowired
	private FitbitHeartZoneRepo heartRepository;

	@Autowired
	private FitbitHeartSendEmailService sendMailService;

	public void heartRateSelect(String mail, String minutes) {
		try {
			File peaksfile = new File(properties.getFileName());
			FileOutputStream stream;

			stream = new FileOutputStream(peaksfile);

			OutputStreamWriter peakswrite = new OutputStreamWriter(stream);
			Writer w = new BufferedWriter(peakswrite);

			w.write("These are Heart-Rate data during December 2015 and March 2016 when the user's heart-rate was at its Peak!"
					+ '\n' + '\n');

			heartRepository.findByMinutesGreaterThanAndNameIs(Long.valueOf(minutes), "Peak").forEach(peak -> {

				try {
					w.write("In " + peak.getDate() + " your heart rate was at Peak zone for : " + peak.getMinutes()
							+ " minutes" + '\n');
				} catch (IOException e) {
					LOG.error(e.toString());
				}

			});

			w.close();
			sendMailService.email(mail, minutes);

		} catch (MessagingException | IOException e) {
			LOG.error(e.toString());
		}
	}
}
