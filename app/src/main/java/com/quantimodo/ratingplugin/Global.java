package com.quantimodo.ratingplugin;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Calendar;
import java.util.Date;

public class Global
{
	public static final String BUGSENSE_KEY = "3a3d937d";

	public static final String QUANTIMODO_SCOPES = "writemeasurements";

	public static Date rangeStart;
	public static Date rangeEnd;
	public static Date rangeEndRequested;

	// Preferences
	public static int moodInterval;

	// Flags
	public static boolean welcomeCompleted;


	public static void init(Context context)
	{
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

		Global.moodInterval = Integer.valueOf(prefs.getString("moodInterval", "2"));
		Global.welcomeCompleted = prefs.getBoolean("welcomeCompleted", false);

		final Calendar cal = Calendar.getInstance();
		if(rangeEnd == null)
		{
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);

			rangeEnd = rangeEndRequested = cal.getTime();
		}

		if(rangeStart == null)
		{
			cal.add(Calendar.WEEK_OF_YEAR, -1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			rangeStart = cal.getTime();
		}
	}
}