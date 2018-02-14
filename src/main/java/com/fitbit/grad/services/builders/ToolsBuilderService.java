package com.fitbit.grad.services.builders;

import com.fitbit.grad.models.HeartRateCategoryEnum;
import com.vaadin.server.FileResource;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Image;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.fitbit.grad.models.HeartRateCategoryEnum.*;

/**
 * Service about some Vaadin tools building
 *
 * @author nikos_mas, alex_kak
 */

@Service
public class ToolsBuilderService {

    public void imageBuilder(Image image, File file) {
        image.setSource(new FileResource(file));
        image.setWidth("300");
        image.setHeight("150");
    }

    public void comboBoxBuilder(ComboBox<HeartRateCategoryEnum> select) {
        List<HeartRateCategoryEnum> planets = new ArrayList<>();
        planets.add(OUT_OF_RANGE);
        planets.add(FAT_BURN);
        planets.add(CARDIO);
        planets.add(PEAK);

        select.setCaption("Select Heart rate category");
        select.setItems(planets);
        select.setItemCaptionGenerator(HeartRateCategoryEnum::d);
        select.setPlaceholder("heart-rate category");
        select.setWidth("250");
        select.setEmptySelectionAllowed(false);
    }

}
