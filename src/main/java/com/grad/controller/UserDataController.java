package com.grad.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.grad.services.builders.ButtonsBuilderService;
import com.grad.services.builders.CheckBoxBuilderService;
import com.grad.services.builders.FieldsBuilderService;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBoxGroup;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * @author nikosmas
 *
 */
public class UserDataController {

	@Title("FitbitApplication")
	@SpringUI(path = "fitbitApp/userData")
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

		@Override
		public void init(VaadinRequest request) {
			VerticalLayout content = new VerticalLayout();
			setContent(content);
			setResponsive(true);

			Image image = new Image();
			CheckBoxGroup<String> multiCheckBox = new CheckBoxGroup<>("User data categories");
			multiCheckBox.setItems("Sleep data", "Profile data", "Activities data", "Lifetime activities data",
					"Frequent activities data", "HeartRate data");
			multiCheckBox.setEnabled(false);

			DateField startDate = new DateField();
			fieldsService.dateBuilder(startDate);
			dateFields.add(startDate);

			DateField endDate = new DateField();
			fieldsService.dateBuilder(endDate);
			dateFields.add(endDate);

			TextField heartRate = new TextField();
			fieldsService.heartRateBuilder(heartRate);
			textFields.add(heartRate);

			Button submitDates = new Button();
			checkBoxService.submitDates(submitDates, startDate, endDate, multiCheckBox);
			buttons.add(submitDates);

			Button submitCheckBoxButton = new Button();
			buttons.add(submitCheckBoxButton);

			checkBoxService.checkBoxButton(multiCheckBox, submitCheckBoxButton, startDate, endDate, content);

			Button exit = new Button();
			buttonsService.exitBuilder(exit, content);
			buttons.add(exit);

			content.addComponent(image);
			content.addComponent(new Label("\n"));
			content.addComponent(new Label("Pick the date range that the application will use for the data calls"));
			content.addComponent(startDate);
			content.addComponent(endDate);
			content.addComponent(new Label("\n"));
			content.addComponent(submitDates);
			content.addComponent(new Label("\n"));
			content.addComponent(new Label("Pick the categories you want to retrieve data from fitbit API"));
			content.addComponent(multiCheckBox);
			content.addComponent(new Label("\n"));
			content.addComponent(submitCheckBoxButton);
			content.addComponent(new Label("\n"));
			content.addComponent(new Button("Continue heart-rate filtering and mail process", event -> {
				getPage().setLocation("heartRateFilter");
				getSession().close();
			}));
			content.addComponent(new Label("\n"));
			content.addComponent(new Label("\n"));
			content.addComponent(new Label("Push to exit and stop all processes"));
			content.addComponent(exit);

		}
	}
}
