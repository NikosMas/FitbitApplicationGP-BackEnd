package com.fitbit.grad.services.notification;

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

import com.fitbit.grad.config.MailInfoProperties;
import com.fitbit.grad.controller.HeartRateNotificationController;
import com.fitbit.grad.domain.HeartRateCategoryEnum;
import com.fitbit.grad.domain.HeartRateValue;
import com.fitbit.grad.repository.HeartRateZoneRepository;
import com.fitbit.grad.services.builders.ClearAllBuilderService;
import com.vaadin.ui.VerticalLayout;

/**
 * Service about filtering heart rate data from database according to info given at {@link HeartRateNotificationController}
 * 
 * @author nikos_mas, alex_kak
 */

@Service
public class HeartRateFilterService {

	private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");

	@Autowired
	private MailInfoProperties properties;

	@Autowired
	private HeartRateZoneRepository heartRepository;

	@Autowired
	private ClearAllBuilderService clearFieldsService;

	@Autowired
	private HeartRateNotificationService sendMailService;

	public void heartRateSelect(String mail, Long minutes, HeartRateCategoryEnum category, VerticalLayout content) {
		try {
			File peaksfile = new File(properties.getFileName());
			FileOutputStream stream;

			stream = new FileOutputStream(peaksfile);

			OutputStreamWriter peakswrite = new OutputStreamWriter(stream);
			Writer w = new BufferedWriter(peakswrite);

			w.write("These are Heart-Rate data when the user's heart-rate was at " + category + " zone!" + '\n' + '\n');
			heartRepository.findByMinutesGreaterThanAndNameIs(minutes, category.d()).forEach(d -> {

				try {
					w.write("In " + d.getDate() + " for : "
							+ d.getMinutes() + " minutes" + '\n');
				} catch (IOException e) {
					LOG.error("Something went wrong: ", e);
					clearFieldsService.removeAll(content);
				}
			});
			
			HeartRateValue heartRateZone = heartRepository.findDistinctByName(category.d());
			Long min = heartRateZone.getMin();
			Long max = heartRateZone.getMax();
			
			w.close();
			sendMailService.email(mail, minutes, category, min, max);

		} catch (MessagingException | IOException e) {
			LOG.error("Something went wrong: ", e);
			clearFieldsService.tryLater(content);
		}
	}
}
