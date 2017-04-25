package com.grad.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.grad.auth.services.AuthCodeRequestService;
import com.grad.collections.CreateCollectionsService;
import com.grad.data.services.FitbitDataStoreService;
import com.grad.heart.services.FitbitHeartCheckPeakService;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class HomeController {

	@Theme("valo")
	@Title("FitbitApplication")
	@SpringUI(path = "home")
	public static class VaadinUI extends UI {

		private FitbitHeartCheckPeakService heartService;
		private FitbitDataStoreService callsService;
		private CreateCollectionsService collectionsService;
		private AuthCodeRequestService codeService;

		@Autowired
		public VaadinUI(FitbitHeartCheckPeakService heartService, FitbitDataStoreService callsService,
				CreateCollectionsService collectionsService, AuthCodeRequestService codeService) {

			this.callsService = callsService;
			this.codeService = codeService;
			this.collectionsService = collectionsService;
			this.heartService = heartService;
		}

		private static final long serialVersionUID = -8439638005240753411L;
		private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");

		@Override
		public void init(VaadinRequest request) {

			// Create the content root layout for the UI
			VerticalLayout content = new VerticalLayout();
			setContent(content);

			// Display the greeting
			content.addComponent(new Label("This Application retrieves user data from Fitbit API"));

			ProgressBar bar = new ProgressBar(0.0f);
			bar.setWidth("500");
			content.addComponent(bar);

			content.addComponent(new Button("Push to create the collections into database", click -> {
				collectionsService.collectionsCreate();
				Notification.show("Collections created!");
				float current = bar.getValue();
				if (current < 1.0f)
					bar.setValue(current + 0.50f);
			}));

			content.addComponent(
					new Button("Push to connect with Fitbit API for complete authorization operations", click -> {
						try {
							codeService.codeRequest();
							Notification.show("Authorization code saved into Redis database!");
							float current = bar.getValue();
							if (current < 1.0f)
								bar.setValue(current + 0.50f);

						} catch (IOException | InterruptedException | URISyntaxException e) {
							LOG.error(e.getMessage());
						}
					}));

//			content.addComponent(new Button("Increase", click -> {
//				float current = bar.getValue();
//				if (current < 1.0f)
//					bar.setValue(current + 0.10f);
//			}));
		}

	}
}
