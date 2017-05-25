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
import com.grad.config.MailInfoProperties;
import com.grad.domain.FitbitHeartRate;
import com.grad.domain.HeartRateCategory;
import com.grad.heart.repository.FitbitHeartZoneRepo;
import com.grad.services.builders.ClearAllService;
import com.vaadin.ui.VerticalLayout;

/**
 * @author nikos_mas
 */

@Service
public class FitbitHeartCheckPeakService {

	private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");

	@Autowired
	private MailInfoProperties properties;

	@Autowired
	private FitbitHeartZoneRepo heartRepository;

	@Autowired
	private ClearAllService clearFieldsService;

	@Autowired
	private FitbitHeartSendEmailService sendMailService;

	public void heartRateSelect(String mail, Long minutes, HeartRateCategory category, VerticalLayout content) {
		try {
			File peaksfile = new File(properties.getFileName());
			FileOutputStream stream;

			stream = new FileOutputStream(peaksfile);

			OutputStreamWriter peakswrite = new OutputStreamWriter(stream);
			Writer w = new BufferedWriter(peakswrite);

			w.write("These are Heart-Rate data when the user's heart-rate was at " + category + " zone!" + '\n' + '\n');
			heartRepository.findByMinutesGreaterThanAndNameIs(minutes, category.description()).forEach(d -> {

				try {
					w.write("In " + d.getDate() + " for : "
							+ d.getMinutes() + " minutes" + '\n');
				} catch (IOException e) {
					LOG.error(e.toString());
					clearFieldsService.removeAll(content);
				}
			});
			
			FitbitHeartRate heartRateZone = heartRepository.findDistinctByName(category.description());
			Long min = heartRateZone.getMin();
			Long max = heartRateZone.getMax();
			
			w.close();
			sendMailService.email(mail, minutes, category, min, max);

		} catch (MessagingException | IOException e) {
			LOG.error(e.toString());
			clearFieldsService.tryLater(content);
		}
	}
}
