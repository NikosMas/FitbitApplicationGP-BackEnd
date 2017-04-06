package com.grad.heart.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grad.config.MailInfoProperties;
import com.grad.heart.repository.FitbitHeartZoneRepo;
import com.grad.heart.services.mail.FitbitHeartSendEmailService;

/**
 * @author nikos_mas
 */

@Service
public class FitbitHeartCheckPeakService {

	@Autowired
	private MailInfoProperties properties;

	private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");
	
	private FitbitHeartZoneRepo heartRepository;
	private FitbitHeartSendEmailService sendMailService;

	public FitbitHeartCheckPeakService(FitbitHeartSendEmailService sendMailService, FitbitHeartZoneRepo heartRepository) {

		this.heartRepository = heartRepository;
		this.sendMailService = sendMailService;
	}

	public void heartRateSelect() throws IOException, MessagingException {

		File peaksfile = new File(properties.getFileName());
		FileOutputStream stream = new FileOutputStream(peaksfile);
		OutputStreamWriter peakswrite = new OutputStreamWriter(stream);
		Writer w = new BufferedWriter(peakswrite);
		List<String> peakDates = new ArrayList<String>();

		w.write("These are Heart-Rate data during December 2015 and March 2016 when the user's heart-rate was at its Peak!"
				+ '\n' + '\n');

		heartRepository.findByMinutesGreaterThanAndNameIs(40l, "Peak").forEach(peak -> {

			try {

				w.write("In " + peak.getDate() + " your heart rate was at Peak zone for : " + peak.getMinutes() + " minutes"
						+ '\n');

			} catch (IOException e) {
				System.err.println(e);
			}
		});

		w.close();
		sendMailService.email(peakDates);
	}
}
