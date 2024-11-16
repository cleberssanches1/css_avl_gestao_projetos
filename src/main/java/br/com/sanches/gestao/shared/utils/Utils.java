package br.com.sanches.gestao.shared.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;

public class Utils {

	private Utils() {}
	
	public static LocalDateTime getInstant() {
        return LocalDateTime.now(ZoneId.of(TimeZone.getTimeZone(Constants.SAO_PAULO_TIMEZONE_ID).getID()));
    }
	
}
