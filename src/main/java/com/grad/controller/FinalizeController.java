package com.grad.controller;

import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;

import com.grad.services.builders.ContentBuilderService;
import com.grad.services.collections.CollectionService;
import com.vaadin.annotations.Title;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Image;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * controller at /fitbitApp/finalize where user can restart the process
 * 
 * @author nikos_mas, alex_kak
 */

public class FinalizeController {

	@Title("Fitbit Application")
	@SpringUI(path = "fitbitApp/finalize")
	public static class VaadinUI extends UI {

		private static final long serialVersionUID = 1L;

		@Autowired
		private ContentBuilderService contentService;
		
		@Autowired
		private CollectionService collectionsService;

		@Override
		public void init(VaadinRequest request) {
			VerticalLayout content = new VerticalLayout();
			setContent(content);
			setResponsive(true);

			Image image = new Image();
			image.setSource(new FileResource(new File("src/main/resources/images/ThankYou.png")));

			RadioButtonGroup<String> group = new RadioButtonGroup<>();
			group.setItems("Same user", "Another user");
			group.setCaption("Choose the user you want");
			
//			Button restartTheSame = new Button();
//			restartTheSame.setIcon(VaadinIcons.ROTATE_LEFT);
//			restartTheSame.setCaption("Restart");
//			restartTheSame.setWidth("150");
//			restartTheSame.addClickListener(click -> {
//				collectionsService.collectionsCreate();
//				getPage().setLocation("userData");
//				getSession().close();
//			});
//			
			Button restart = new Button();
			restart.setIcon(VaadinIcons.ROTATE_LEFT);
			restart.setCaption("Restart");
			restart.setWidth("150");
			restart.addClickListener(click -> {
				
				if (group.getValue().equals("Same user")) {
					collectionsService.collectionsCreate();
					getPage().setLocation("userData");
					getSession().close();
				}else {
					getPage().setLocation("dashboard");
					getSession().close();
				}
			});
			
			contentService.finalizeContentBuilder(content, image, group, restart);
		}
	}
}
