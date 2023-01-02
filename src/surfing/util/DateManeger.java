package surfing.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateManeger {
	
	public static String getLocalTime() {
		// 메서드 실행에 대한 완료시간 스탬프 변수 선언
		LocalTime now = LocalTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH시 mm분 ss초");
		String formatedNow = now.format(formatter);

		return formatedNow;
	}
}
