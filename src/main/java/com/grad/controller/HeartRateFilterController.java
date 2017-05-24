package com.grad.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.grad.domain.HeartRateCategory;
import com.grad.services.builders.ButtonsBuilderService;
import com.grad.services.builders.FieldsBuilderService;
import com.vaadin.annotations.Title;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * @author nikosmas
 *
 */
public class HeartRateFilterController {

	@Title("FitbitApplication")
	@SpringUI(path = "fitbitApp/heartRateFilter")
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

			Image image = new Image();
			ComboBox<HeartRateCategory> select = new ComboBox<>("Select Heart rate category");

			image.setSource(new FileResource(new File("src/main/resources/images/FitbitLogo.png")));

			List<HeartRateCategory> planets = new ArrayList<>();
			planets.add(HeartRateCategory.OUT_OF_RANGE);
			planets.add(HeartRateCategory.FAT_BURN);
			planets.add(HeartRateCategory.CARDIO);
			planets.add(HeartRateCategory.PEAK);

			select.setItems(planets);
			select.setItemCaptionGenerator(HeartRateCategory::description);
			select.setPlaceholder("heart-rate category");
			select.setWidth("250");
			select.setEmptySelectionAllowed(false);

			TextField mail = new TextField();
			fieldsService.mailBuilder(mail);
			textFields.add(mail);

			TextField heartRate = new TextField();
			fieldsService.heartRateBuilder(heartRate);
			textFields.add(heartRate);

			Button heartRateMail = new Button();
			buttonsService.heartRateMailBuilder(heartRateMail, mail, heartRate, select, content);
			buttons.add(heartRateMail);

			Button exit = new Button();
			buttonsService.exitBuilder(exit, content);
			buttons.add(exit);

			content.addComponent(image);
			content.addComponent(new Label("Complete the next 3 fields to continue with e-mail process"));
			content.addComponent(mail);
			content.addComponent(new Label("\n"));
			content.addComponent(select);
			content.addComponent(new Label("\n"));
			content.addComponent(heartRate);
			content.addComponent(new Label("\n"));
			content.addComponent(heartRateMail);
			content.addComponent(new Label("\n"));
			content.addComponent(new Label("\n"));
			content.addComponent(new Label("Push to exit and stop all processes"));
			content.addComponent(exit);
		}
	}
}
