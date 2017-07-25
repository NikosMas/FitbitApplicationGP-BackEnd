package com.grad.controller;

import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;

import com.grad.services.builders.ButtonsBuilderService;
import com.grad.services.builders.CheckBoxBuilderService;
import com.grad.services.builders.ContentBuilderService;
import com.grad.services.builders.FieldsBuilderService;
import com.vaadin.annotations.Title;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBoxGroup;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Image;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * controller at /fitbitApp/userData waiting the user to fill the form about
 * user data
 * 
 * @author nikos_mas, alex_kak
 */

public class UserDataController {

	@Title("User Data")
	@SpringUI(path = "fitbitApp/userData")
	public static class VaadinUI extends UI {

		private static final long serialVersionUID = 1L;

		@Autowired
		private FieldsBuilderService fieldsService;

		@Autowired
		private ButtonsBuilderService buttonsService;

		@Autowired
		private CheckBoxBuilderService checkBoxService;

		@Autowired
		private ContentBuilderService contentService;

		@Override
		public void init(VaadinRequest request) {
			VerticalLayout content = new VerticalLayout();
			setContent(content);
			setResponsive(true);

			Image image = new Image();
			image.setSource(new FileResource(new File("src/main/resources/images/FitbitLogo.png")));

			CheckBoxGroup<String> multiCheckBox = new CheckBoxGroup<>();
			checkBoxService.checkBoxBuilder(multiCheckBox);

			DateField startDate = new DateField();
			fieldsService.dateBuilder(startDate);

			DateField endDate = new DateField();
			fieldsService.dateBuilder(endDate);

			TextField heartRate = new TextField();
			fieldsService.heartRateBuilder(heartRate);

			Button submitDates = new Button();
			checkBoxService.submitDates(submitDates, startDate, endDate, multiCheckBox);

			Button submitCheckBoxButton = new Button();
			checkBoxService.checkBoxButton(multiCheckBox, submitCheckBoxButton, startDate, endDate, content);

			// business part with redirection is here because of private {@link
			// Page} at {@link UI}
			Button exit = new Button();
			exit.setIcon(VaadinIcons.ROTATE_LEFT);
			exit.setCaption("Exit");
			exit.setWidth("150");
			exit.addClickListener(click -> {
				getPage().setLocation("finalize");
				getSession().close();
			});

			Button stepBackward = new Button();
			stepBackward.setIcon(VaadinIcons.ARROW_BACKWARD);
			stepBackward.setCaption("Back");
			stepBackward.setWidth("150");
			stepBackward.addClickListener(click -> {
				getPage().setLocation("dashboard");
				getSession().close();
			});

			/**
			 * business part with redirection is here because of private {@link Page} at
			 * {@link UI}
			 */
			Button stepForward = new Button();
			stepForward.setIcon(VaadinIcons.ARROW_FORWARD);
			stepForward.setCaption("Continue");
			stepForward.addClickListener(click -> {
				if (buttonsService.continueBuilder(stepForward, request, null, submitCheckBoxButton,
						multiCheckBox)) {
					getPage().setLocation("heartRateNotification");
					getSession().close();
				}else {
					getPage().setLocation("finalize");
					getSession().close();
				}
			});

			contentService.userDataContentBuilder(content, image, multiCheckBox, startDate, endDate, heartRate,
					submitDates, submitCheckBoxButton, exit, stepForward, stepBackward);
		}
	}
}
