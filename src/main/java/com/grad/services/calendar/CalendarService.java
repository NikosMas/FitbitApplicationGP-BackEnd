package com.grad.services.calendar;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class CalendarService {

	public List<Map<String, String>> getDates(LocalDate startDate, LocalDate endDate) {

		long between = ChronoUnit.DAYS.between(startDate, endDate);
		List<Map<String, String>> dates = new ArrayList<Map<String, String>>();

		while (between > 30) {
			
			endDate = startDate.plusDays(30);

			Map<String, String> date = new HashMap<>();
			date.put("StartDate", startDate.toString());
			date.put("EndDate", endDate.toString());
			dates.add(date);
			between -= 30;
			
			startDate = endDate.plusDays(1);
		}

		if (between > 0) {
			Map<String, String> date = new HashMap<>();
			date.put("StartDate", startDate.toString());
			date.put("EndDate", startDate.plusDays(between).toString());
			dates.add(date);
		}

		return dates;
	}
}
