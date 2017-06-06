package com.grad.services.builders;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.grad.domain.HeartRateCategory;
import com.vaadin.server.FileResource;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Image;

/**
 * Service about some Vaadin tools building 
 * 
 * @author nikos_mas, alex_kak
 */

@Service
public class ToolsBuilderService {

	/**
	 * @param image
	 * @param file
	 */
	public void imageBuilder(Image image, File file) {
		image.setSource(new FileResource(file));
		image.setWidth("300");
		image.setHeight("150");
	}
	
	/**
	 * @param select
	 */
	public void comboBoxBuilder(ComboBox<HeartRateCategory> select){
		
		List<HeartRateCategory> planets = new ArrayList<>();
		planets.add(HeartRateCategory.OUT_OF_RANGE);
		planets.add(HeartRateCategory.FAT_BURN);
		planets.add(HeartRateCategory.CARDIO);
		planets.add(HeartRateCategory.PEAK);

		select.setCaption("Select Heart rate category");
		select.setItems(planets);
		select.setItemCaptionGenerator(HeartRateCategory::desc);
		select.setPlaceholder("heart-rate category");
		select.setWidth("250");
		select.setEmptySelectionAllowed(false);
		
	}

}
