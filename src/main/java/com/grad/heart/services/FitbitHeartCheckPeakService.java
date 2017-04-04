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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grad.config.MailInfoProperties;
import com.grad.heart.repository.FitbitHeartZoneRepo;

/**
 * find the dates with over than 35 minutes per day above the peak heart-zone
 * 
 * @author nikos_mas
 *
 */

@Service
public class FitbitHeartCheckPeakService {

	@Autowired
	private MailInfoProperties properties;

	@Autowired
	private FitbitHeartZoneRepo repository;

	@Autowired
	private FitbitHeartSendEmailService sendmail;

	public void heartRateSelect() throws IOException, MessagingException {

		File peaksfile = new File(properties.getFileName());
		FileOutputStream stream = new FileOutputStream(peaksfile);
		OutputStreamWriter peakswrite = new OutputStreamWriter(stream);
		Writer w = new BufferedWriter(peakswrite);
		List<String> peakDates = new ArrayList<String>();

		w.write("These are Heart-Rate data during December 2015 and March 2016 when the user's heart-rate was at its Peak!"
				+ '\n' + '\n');

		repository.findByMinutesGreaterThanAndNameIs(40l, "Peak").forEach(peak -> {

			try {

				w.write("In " + peak.getDate() + " your heart rate was at Peak zone for : " + peak.getMinutes() + " " + '\n');

			} catch (IOException e) {}
		});

		w.close();
		sendmail.email(peakDates);
	}
}
