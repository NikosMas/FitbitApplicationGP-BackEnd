package com.grad.services.calendar;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.grad.controller.UserDataController;

/**
 * Service about dates given from the user at {@link UserDataController}.
 * 
 * @author nikos_mas, alex_kak
 */

@Service
public class CalendarService {

	/**
	 * Takes the starting & the ending date and split the range of them to 90-days partitions
	 * if it's possible
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String, String>> getDates(LocalDate startDate, LocalDate endDate) {

		long addedDays = 0;
		long between = ChronoUnit.DAYS.between(startDate, endDate);
		List<Map<String, String>> dates = new ArrayList<Map<String, String>>();

		while (between > 90) {

			endDate = startDate.plusDays(90);

			Map<String, String> date = new HashMap<>();
			date.put("StartDate", startDate.toString());
			date.put("EndDate", endDate.toString());
			dates.add(date);
			between -= 90;

			startDate = endDate.plusDays(1);
			addedDays += 1;
		}

		if (between > 0) {
			Map<String, String> date = new HashMap<>();
			date.put("StartDate", startDate.toString());
			date.put("EndDate", startDate.plusDays(between).minusDays(addedDays).toString());
			dates.add(date);
		}
		return dates;
	}

	/**
	 * Convert a list of maps with dates to a list of dates
	 * 
	 * @param dates
	 * @return
	 */
	private List<String> mapToListDates(List<Map<String, String>> dates) {

		return dates.stream().map(date -> {

			return date.entrySet().stream().map(v -> v.getValue()).collect(Collectors.toList());

		}).collect(Collectors.toList()).stream().flatMap(result -> result.stream()).collect(Collectors.toList());
	}

	/**
	 * @param dates
	 * @return
	 */
	public List<String> months(List<Map<String, String>> dates) {
		List<String> packOfMonths = new ArrayList<>();
		String months;

		int sizeOfDatesList = mapToListDates(dates).size();

		if (sizeOfDatesList > 2) {
			for (int i = 0; i < sizeOfDatesList; i += 2) {
				months = mapToListDates(dates).get(i) + "/" + mapToListDates(dates).get(i + 1) + ".json";
				packOfMonths.add(months);
			}
		} else {
			months = mapToListDates(dates).get(0) + "/" + mapToListDates(dates).get(1) + ".json";
			packOfMonths.add(months);
		}

		return packOfMonths;
	}
}
