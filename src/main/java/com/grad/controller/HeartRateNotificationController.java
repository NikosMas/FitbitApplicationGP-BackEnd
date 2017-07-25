package com.grad.controller;

import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;

import com.grad.domain.HeartRateCategoryEnum;
import com.grad.services.builders.ButtonsBuilderService;
import com.grad.services.builders.ContentBuilderService;
import com.grad.services.builders.FieldsBuilderService;
import com.grad.services.builders.ToolsBuilderService;
import com.vaadin.annotations.Title;
import com.vaadin.icons.VaadinIcons;
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
 * controller at /fitbitApp/heartRateFilter waiting the user to fill the form
 * about heart rate & mail info
 * 
 * @author nikos_mas, alex_kak
 */

public class HeartRateNotificationController {

	@Title("Heart Rate Notification")
	@SpringUI(path = "fitbitApp/heartRateNotification")
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

			ComboBox<HeartRateCategoryEnum> select = new ComboBox<>();
			toolsService.comboBoxBuilder(select);

			TextField mail = new TextField();
			fieldsService.mailBuilder(mail);

			TextField heartRate = new TextField();
			fieldsService.heartRateBuilder(heartRate);

			Button heartRateMail = new Button();
			buttonsService.heartRateMailBuilder(heartRateMail, mail, heartRate, select, content);

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

			contentService.heartRateFilterContentBuilder(content, image, select, heartRate, mail, heartRateMail, exit);
		}
	}
}
