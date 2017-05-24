package com.grad.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.grad.services.builders.ButtonsBuilderService;
import com.grad.services.builders.FieldsBuilderService;
import com.vaadin.annotations.Title;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * @author nikosmas
 *
 */
public class DashboardController {

	@Title("FitbitApplication")
	@SpringUI(path = "fitbitApp/dashboard")
	public static class VaadinUI extends UI {

		private static final long serialVersionUID = 1L;
		private List<TextField> textFields = new ArrayList<TextField>();
		private List<Button> buttons = new ArrayList<Button>();

		@Autowired
		private FieldsBuilderService fieldsService;

		@Autowired
		private ButtonsBuilderService buttonsService;

		@Override
		public void init(VaadinRequest request) {
			VerticalLayout content = new VerticalLayout();
			setContent(content);
			setResponsive(true);

			Image clientIdImage = new Image();
			clientIdImage.setSource(new FileResource(new File("src/main/resources/images/clientid.gif")));
			clientIdImage.setWidth("300");
			clientIdImage.setHeight("150");
			
			Image clientSecretImage = new Image();
			clientSecretImage.setSource(new FileResource(new File("src/main/resources/images/clientSecret.gif")));
			clientSecretImage.setWidth("300");
			clientSecretImage.setHeight("150");

			TextField clientId = new TextField();
			fieldsService.clientIdBuilder(clientId);
			textFields.add(clientId);

			TextField clientSecret = new TextField();
			fieldsService.clientSecretBuilder(clientSecret);
			textFields.add(clientSecret);

			Button collections = new Button();
			buttonsService.collectionsBuilder(collections);
			buttons.add(collections);

			Button authorizationCode = new Button();
			buttonsService.authorizationBuilder(authorizationCode, clientId, clientSecret);
			buttons.add(authorizationCode);

			Button exit = new Button();
			buttonsService.exitBuilder(exit, content);
			buttons.add(exit);

			
			content.addComponent(new Label("Push to start creating the collections into Mongo database"));
			content.addComponent(collections);
			content.addComponent(new Label("\n"));
			content.addComponent(clientIdImage);
			content.addComponent(clientId);
			content.addComponent(new Label("\n"));
			content.addComponent(clientSecretImage);
			content.addComponent(clientSecret);
			content.addComponent(new Label("\n"));
			content.addComponent(new Label(
					"Push to start connecting with Fitbit API for recieving the authorization code required to next calls to the API"));
			content.addComponent(authorizationCode);
			content.addComponent(new Label("\n"));
			content.addComponent(new Button("Continue to user data receiving process", event -> {
				getPage().setLocation("userData");
				getSession().close();
			}));
			content.addComponent(new Label("\n"));
			content.addComponent(new Label("\n"));
			content.addComponent(new Label("Push to exit and stop all processes"));
			content.addComponent(exit);

		}
	}
}
