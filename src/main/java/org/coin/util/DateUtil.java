package org.coin.util;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateUtil {

	/**
	 * convert zoned datetime string to local date string
	 */
	public static String convertZT2String(String zonedDtStr) {
		String dateStr = "";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		try {
			ZonedDateTime zonedDateTime = ZonedDateTime.parse(zonedDtStr);
			dateStr = zonedDateTime.toLocalDateTime().format(dtf);
		} catch (Exception e) {
			log.error("convertZT2String error!!", e);
		}
		return dateStr;
	}

	/**
	 * convert local datetime to local date string
	 */
	public static String convertLT2String(LocalDateTime ldt) {
		String dateStr = "";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		try {
			dateStr = ldt.format(dtf);
		} catch (Exception e) {
			log.error("convertLT2String error!!", e);
		}
		return dateStr;
	}

	public static void main(String[] args) {
		System.out.println(convertZT2String("2022-04-30T08:13:00+00:00"));
	}

}
