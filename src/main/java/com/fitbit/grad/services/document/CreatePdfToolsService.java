package com.fitbit.grad.services.document;

import com.fitbit.grad.models.CommonDataSample;
import com.fitbit.grad.models.HeartRateValue;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author nikos_mas, alex_kak
 */

@Service
public class CreatePdfToolsService {

    /**
     * @param table
     * @param categories
     */
    public void addTable(PdfPTable table, Stream categories) {
        categories.forEach(columnTitle -> {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setBorderWidth(2);
            header.setPhrase(new Phrase(String.valueOf(columnTitle)));
            table.addCell(header);
        });
    }

    /**
     * @param table
     * @param data
     */
    public void addRowsHeartRate(PdfPTable table, List<HeartRateValue> data) {
        for (HeartRateValue d : data) {
            table.addCell(d.getDate());
            table.addCell(d.getName());
            table.addCell(String.valueOf(d.getMinutes()));
            table.addCell(String.valueOf(d.getMin()));
            table.addCell(String.valueOf(d.getMax()));
            table.addCell(String.valueOf(d.getCaloriesOut()));
        }
    }

    /**
     * @param table
     * @param data
     */
    public void addRows(PdfPTable table, List<CommonDataSample> data) {
        for (CommonDataSample d : data) {
            table.addCell(d.getDateTime());
            table.addCell(d.getValue());
        }
    }
}
