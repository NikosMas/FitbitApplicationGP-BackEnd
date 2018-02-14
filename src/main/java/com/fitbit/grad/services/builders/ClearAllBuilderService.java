package com.fitbit.grad.services.builders;

import com.vaadin.server.FileResource;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

/**
 * Service about clearing all Vaadin contents
 *
 * @author nikos_mas, alex_kak
 */

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ClearAllBuilderService {

    private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");

    public void tryLater(VerticalLayout content) {
        content.removeAllComponents();

        Image image = new Image();
        image.setSource(new FileResource(new File("src/main/resources/images/Later.png")));

        content.addComponent(image);
        LOG.info("Please try later. Application exited");
    }

}
