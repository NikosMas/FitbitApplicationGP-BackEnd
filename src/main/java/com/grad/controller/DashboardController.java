package com.grad.controller;

import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;

import com.grad.services.builders.ButtonsBuilderService;
import com.grad.services.builders.ContentBuilderService;
import com.grad.services.builders.FieldsBuilderService;
import com.grad.services.builders.ToolsBuilderService;
import com.vaadin.annotations.Title;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Image;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * controller at /fitbitApp/dashboard waiting the user to fill the form about authorization info
 * 
 * @author nikos_mas, alex_kak
 */

public class DashboardController {

	@Title("Home")
	@SpringUI(path = "fitbitApp/dashboard")
	public static class VaadinUI extends UI {

		private static final long serialVersionUID = 1L;

		@Autowired
		private FieldsBuilderService fieldsService;

		@Autowired
		private ButtonsBuilderService buttonsService;

		@Autowired
		private ToolsBuilderService imageService;

		@Autowired
		private ContentBuilderService contentService;

		
		@Override
		public void init(VaadinRequest request) {
			VerticalLayout content = new VerticalLayout();
			setContent(content);
			setResponsive(true);
			
			Image image = new Image();
			image.setSource(new FileResource(new File("src/main/resources/images/FitbitLogo.png")));

			Image clientIdImage = new Image();
			imageService.imageBuilder(clientIdImage, new File("src/main/resources/images/clientid.gif"));

			Image clientSecretImage = new Image();
			imageService.imageBuilder(clientSecretImage, new File("src/main/resources/images/clientSecret.gif"));

			TextField clientId = new TextField();
			fieldsService.clientIdBuilder(clientId);

			TextField clientSecret = new TextField();
			fieldsService.clientSecretBuilder(clientSecret);

			Button collections = new Button();
			buttonsService.collectionsBuilder(collections);

			Button authorizationCode = new Button();
			buttonsService.authorizationBuilder(authorizationCode, clientId, clientSecret, collections);

			Button exit = new Button();
			buttonsService.exitBuilder(exit, content);

			// business part with redirection is here because of private {@link
			// Page} at {@link UI}
			Button continueProcess = new Button();
			continueProcess.setCaption("Continue to user data receiving process");
			continueProcess.addClickListener(click -> {
				if (buttonsService.continueBuilder(continueProcess, request, authorizationCode, null, null)) {
					getPage().setLocation("userData");
					getSession().close();
				}
			});

			contentService.dashboardContentBuilder(content, image, clientIdImage, clientSecretImage, clientId, clientSecret,
					collections, authorizationCode, exit, continueProcess);
		}
	}

}
