package com.grad.controller;

import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;

import com.grad.domain.HeartRateCategory;
import com.grad.services.builders.ButtonsBuilderService;
import com.grad.services.builders.ContentBuilderService;
import com.grad.services.builders.FieldsBuilderService;
import com.grad.services.builders.ToolsBuilderService;
import com.vaadin.annotations.Title;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Image;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * @author nikosmas
 *
 */
public class HeartRateFilterController {

	@Title("Heart Rate Mail")
	@SpringUI(path = "fitbitApp/heartRateFilter")
	public static class VaadinUI extends UI {

		private static final long serialVersionUID = 1L;

		@Autowired
		private FieldsBuilderService fieldsService;

		@Autowired
		private ButtonsBuilderService buttonsService;

		@Autowired
		private ToolsBuilderService toolsService;
		
		@Autowired
		private ContentBuilderService contentService;

		@Override
		public void init(VaadinRequest request) {
			VerticalLayout content = new VerticalLayout();
			setContent(content);
			setResponsive(true);

			Image image = new Image();
			image.setSource(new FileResource(new File("src/main/resources/images/FitbitLogo.png")));

			ComboBox<HeartRateCategory> select = new ComboBox<>();
			toolsService.comboBoxBuilder(select);

			TextField mail = new TextField();
			fieldsService.mailBuilder(mail);

			TextField heartRate = new TextField();
			fieldsService.heartRateBuilder(heartRate);

			Button heartRateMail = new Button();
			buttonsService.heartRateMailBuilder(heartRateMail, mail, heartRate, select, content);

			Button exit = new Button();
			buttonsService.exitBuilder(exit, content);
			
			contentService.heartRateFilterContentBuilder(content, image, select, heartRate, mail, heartRateMail, exit);
		}
	}
}
