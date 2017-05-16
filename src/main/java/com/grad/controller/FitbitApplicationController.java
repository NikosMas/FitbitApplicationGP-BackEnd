package com.grad.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.grad.domain.HeartRateCategory;
import com.grad.services.builders.ButtonsBuilderService;
import com.grad.services.builders.CheckBoxBuilderService;
import com.grad.services.builders.FieldsBuilderService;
import com.grad.services.builders.OtherWidgetsBuilderService;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBoxGroup;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Image;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * @author nikosmas
 *
 */
public class FitbitApplicationController {

	@Title("FitbitApplication")
	@SpringUI(path = "home")
	public static class VaadinUI extends UI {

		private static final long serialVersionUID = 1L;
		private List<DateField> dateFields = new ArrayList<DateField>();
		private List<TextField> textFields = new ArrayList<TextField>();
		private List<Button> buttons = new ArrayList<Button>();

		@Autowired
		private FieldsBuilderService fieldsService;

		@Autowired
		private ButtonsBuilderService buttonsService;

		@Autowired
		private CheckBoxBuilderService checkBoxService;

		@Autowired
		private OtherWidgetsBuilderService widgetService;

		@Override
		public void init(VaadinRequest request) {
			VerticalLayout content = new VerticalLayout();
			setContent(content);
			setResponsive(true);

			Image image = new Image();
			ProgressBar bar = new ProgressBar(0.0f);
			ComboBox<HeartRateCategory> select = new ComboBox<>("Select Heart rate category");
			CheckBoxGroup<String> multiCheckBox = new CheckBoxGroup<>("User data categories");
			widgetService.other(image, bar, select, multiCheckBox);

			DateField startDate = new DateField();
			fieldsService.dateBuilder(startDate);
			dateFields.add(startDate);

			DateField endDate = new DateField();
			fieldsService.dateBuilder(endDate);
			dateFields.add(endDate);

			TextField heartRate = new TextField();
			fieldsService.heartRateBuilder(heartRate);
			textFields.add(heartRate);

			TextField mail = new TextField();
			fieldsService.mailBuilder(mail);
			textFields.add(mail);

			TextField clientId = new TextField();
			fieldsService.clientIdBuilder(clientId);
			textFields.add(clientId);

			TextField clientSecret = new TextField();
			fieldsService.clientSecretBuilder(clientSecret);
			textFields.add(clientSecret);

			Button collections = new Button();
			buttonsService.collectionsBuilder(collections, bar);
			buttons.add(collections);

			Button authorizationCode = new Button();
			buttonsService.authorizationBuilder(authorizationCode, bar, clientId, clientSecret);
			buttons.add(authorizationCode);

			Button submitDates = new Button();
			checkBoxService.submitDates(submitDates, bar, startDate, endDate, multiCheckBox);
			buttons.add(submitDates);

			Button heartRateMail = new Button();
			buttonsService.heartRateMailBuilder(heartRateMail, bar, mail, heartRate, multiCheckBox, startDate, endDate,
					select, content);
			buttons.add(heartRateMail);

			Button submitCheckBoxButton = new Button();
			buttons.add(submitCheckBoxButton);

			checkBoxService.checkBoxButton(multiCheckBox, submitCheckBoxButton, bar, startDate, endDate, heartRateMail,
					content, select);

			Button exit = new Button();
			buttonsService.exitBuilder(exit, content);
			buttons.add(exit);

			Button complete = new Button();
			buttons.add(complete);
			buttonsService.completeBuilder(complete, bar, dateFields, textFields, buttons, multiCheckBox, select);

			widgetService.contentSetters(content, image, bar, select, multiCheckBox, startDate, endDate, heartRate,
					mail, clientId, clientSecret, collections, authorizationCode, submitDates, heartRateMail,
					submitCheckBoxButton, exit, complete);

		}
	}
}
