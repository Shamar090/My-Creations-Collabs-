package com.techelevator.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BasicLogger {

	private static PrintWriter pw = null;

	public static void log(String message) {
		try {
			if (pw == null) {
				File logDir = new File("logs");
				if (!logDir.exists()) {
					logDir.mkdirs();
				}
				String logFilename = "logs/" + LocalDate.now().format(DateTimeFormatter.ISO_DATE) + ".log";
				pw = new PrintWriter(new FileOutputStream(logFilename, true));
			}
			pw.println(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + " " + message);
			pw.flush();
		}
		catch (IOException e) {
			throw new BasicLoggerException(e.getMessage());
		}
	}
}
