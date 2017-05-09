package com.grad.services.builders;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vaadin.server.FileResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBoxGroup;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Image;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ClearAllService {

	private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");

	/**
	 * @param dateFields
	 * @param textFields
	 * @param buttons
	 * @param multiCheckBox
	 * @param bar
	 */
	public void clearAll(List<DateField> dateFields, List<TextField> textFields, List<Button> buttons,
			CheckBoxGroup<String> multiCheckBox, ProgressBar bar) {

		dateFields.stream().forEach(DateField::clear);
		textFields.stream().forEach(TextField::clear);
		buttons.stream().forEach(button -> button.setEnabled(true));
		multiCheckBox.clear();
		multiCheckBox.setEnabled(true);
		bar.setValue(0);
	}

	/**
	 * @param content
	 */
	public void removeAll(VerticalLayout content) {
		content.removeAllComponents();

		Image image = new Image();
		image.setSource(new FileResource(new File("src/main/resources/images/ThankYou.png")));
		image.setSizeFull();

		content.addComponent(image);
		LOG.info("Exit application. Thank you!!");
	}

}
