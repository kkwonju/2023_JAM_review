package kkwo.JAM.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {
	public static String getNowDateTimeStr() {
		// 현재 날짜/시간
		LocalDateTime now = LocalDateTime.now();
		// 포맷팅
		String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		return formatedNow;
	}
}
