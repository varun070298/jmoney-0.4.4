/*
 *
 *  JMoney - A Personal Finance Manager
 *  Copyright (c) 2002 Johann Gyger <johann.gyger@switzerland.org>
 *
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *
 */

package net.sf.jmoney;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

public class VerySimpleDateFormat {

	public static final String[] DATE_PATTERNS =
		{
			"dd.MM.yyyy",
			"dd/MM/yyyy",
			"dd-MM-yyyy",
			"MM/dd/yyyy",
			"MM-dd-yyyy",
			"yyyy.dd.MM",
			"yyyy.MM.dd",
			"yyyy.MM.dd.",
			"yyyy/MM/dd",
			"yyyy-MM-dd" };

	private SimpleDateFormat formatter;
	
	private int dayIndex, monthIndex, yearIndex;

	public VerySimpleDateFormat(String pattern) {
		formatter = new SimpleDateFormat(pattern);
		dayIndex = pattern.indexOf("dd");
		monthIndex = pattern.indexOf("MM");
		yearIndex = pattern.indexOf("yyyy");
	}

	public String format(Date d) {
		return (d == null) ? "" : formatter.format(d);
	}

	public Date parse(String dateString) {
		try {
			Calendar cl = Calendar.getInstance();
			int day = cl.get(Calendar.DATE);
			int month = cl.get(Calendar.MONTH);
			int year = cl.get(Calendar.YEAR);
			cl.clear();
			StringTokenizer st = new StringTokenizer(dateString, "/.-");
			switch (st.countTokens()) {
				case 1 :
					day = Integer.parseInt(st.nextToken());
					break;
				case 2 :
					if (dayIndex < monthIndex) {
						day = Integer.parseInt(st.nextToken());
						month = Integer.parseInt(st.nextToken()) - 1;
					} else if (dayIndex > monthIndex) {
						month = Integer.parseInt(st.nextToken()) - 1;
						day = Integer.parseInt(st.nextToken());
					}
					break;
				case 3 :
					String d = null, m = null, y = null;
					if (dayIndex < monthIndex && monthIndex < yearIndex) {
						d = st.nextToken();
						m = st.nextToken();
						y = st.nextToken();
					} else if (monthIndex < dayIndex && dayIndex < yearIndex) {
						m = st.nextToken();
						d = st.nextToken();
						y = st.nextToken();
					} else if (
						yearIndex < monthIndex && monthIndex < dayIndex) {
						y = st.nextToken();
						m = st.nextToken();
						d = st.nextToken();
					} else if (yearIndex < dayIndex && dayIndex < monthIndex) {
						y = st.nextToken();
						d = st.nextToken();
						m = st.nextToken();
					}
					day = Integer.parseInt(d);
					month = Integer.parseInt(m) - 1;
					year = Integer.parseInt(y);
					if (y.length() < 3)
						year += year < 30 ? 2000 : 1900;
					break;
				default :
					throw new IllegalArgumentException("No valid date");
			}
			cl.set(year, month, day);
			return cl.getTime();
		} catch (Exception e) {
			return null;
		}
	}
}
