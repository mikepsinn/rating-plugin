package com.quantimodo.ratingplugin.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.quantimodo.ratingplugin.Global;
import com.quantimodo.ratingplugin.R;
import com.quantimodo.ratingplugin.databases.MoodDatabaseHelper;
import com.quantimodo.ratingplugin.things.MoodThing;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends Activity
{
	public static ArrayList<MoodThing> moods;

	private static TextView tvTimeRangeStart;
	private static TextView tvTimeRangeEnd;

	private static Calendar startCalendar;
	private static Calendar endCalendar;

	@Override protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Global.init(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);

		MenuItem item = menu.add("");
		item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		View vwTimeRange = getLayoutInflater().inflate(R.layout.view_action_timerange, null);
		tvTimeRangeStart = (TextView) vwTimeRange.findViewById(R.id.tvTimeRangeStart);
		tvTimeRangeEnd = (TextView) vwTimeRange.findViewById(R.id.tvTimeRangeEnd);

		initTimeRangeView();

		item.setActionView(vwTimeRange);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent openPrefsIntent = new Intent(this, SettingsActivity.class);
			startActivity(openPrefsIntent);
			return true;
		default:
			return false;
		}
	}

	private void initTimeRangeView()
	{
		updateTimeRangeView();

		tvTimeRangeStart.setOnClickListener(onTimeRangeStartClicked);
		tvTimeRangeEnd.setOnClickListener(onTimeRangeEndClicked);
	}

	public static void updateTimeRangeView()
	{
		final Calendar nowCalendar = Calendar.getInstance();
		startCalendar = Calendar.getInstance();
		endCalendar = Calendar.getInstance();
		startCalendar.setTime(Global.rangeStart);
		endCalendar.setTime(Global.rangeEnd);

		SimpleDateFormat startDateFormat = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.MEDIUM);
		if(startCalendar.get(Calendar.YEAR) == nowCalendar.get(Calendar.YEAR))
		{
			startDateFormat.applyPattern(startDateFormat.toPattern().replaceAll("[^\\p{Alpha}]*y+[^\\p{Alpha}]*", ""));
		}

		tvTimeRangeStart.setText(startDateFormat.format(Global.rangeStart));

		if(endCalendar.get(Calendar.YEAR) == nowCalendar.get(Calendar.YEAR) && endCalendar.get(Calendar.DAY_OF_YEAR) == nowCalendar.get(Calendar.DAY_OF_YEAR))
		{
			tvTimeRangeEnd.setText(R.string.action_timerange_today);
		}
		else
		{
			SimpleDateFormat endDateFormat = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.MEDIUM);
			if(endCalendar.get(Calendar.YEAR) == nowCalendar.get(Calendar.YEAR))
			{
				endDateFormat.applyPattern(endDateFormat.toPattern().replaceAll("[^\\p{Alpha}]*y+[^\\p{Alpha}]*", ""));
			}
			tvTimeRangeEnd.setText(endDateFormat.format(Global.rangeEnd));
		}
	}

    View.OnClickListener onTimeRangeStartClicked = new View.OnClickListener()
	{
		@Override public void onClick(View view)
		{
			DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener()
			{
				@Override public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth)
				{
					Calendar nowCalendar = Calendar.getInstance();
					nowCalendar.set(Calendar.YEAR, year);
					nowCalendar.set(Calendar.MONTH, month);
					nowCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
					nowCalendar.set(Calendar.HOUR_OF_DAY, 0);
					nowCalendar.set(Calendar.MINUTE, 0);

					Global.rangeStart = nowCalendar.getTime();

					MoodDatabaseHelper database = new MoodDatabaseHelper(datePicker.getContext());


				}
			}, startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DAY_OF_MONTH));
			if(Build.VERSION.SDK_INT >= 11)
			{
				dialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
			}
			dialog.show();
		}
	};

	View.OnClickListener onTimeRangeEndClicked  =new View.OnClickListener()
	{
		@Override public void onClick(View view)
		{
			DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener()
			{
				@Override public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth)
				{
					Calendar nowCalendar = Calendar.getInstance();
					nowCalendar.set(Calendar.YEAR, year);
					nowCalendar.set(Calendar.MONTH, month);
					nowCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
					if(endCalendar.get(Calendar.YEAR) != nowCalendar.get(Calendar.YEAR) && endCalendar.get(Calendar.DAY_OF_YEAR) != nowCalendar.get(Calendar.DAY_OF_YEAR))
					{
						nowCalendar.set(Calendar.HOUR_OF_DAY, 0);
						nowCalendar.set(Calendar.MINUTE, 0);
					}
					else
					{
						nowCalendar.set(Calendar.HOUR_OF_DAY, 23);
						nowCalendar.set(Calendar.MINUTE, 59);
						nowCalendar.set(Calendar.SECOND, 59);
					}

					Global.rangeEnd = Global.rangeEndRequested = nowCalendar.getTime();

					MoodDatabaseHelper database = new MoodDatabaseHelper(datePicker.getContext());
				}
			}, endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DAY_OF_MONTH));
			if(Build.VERSION.SDK_INT >= 11)
			{
				dialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
			}
			dialog.show();
		}
	};
}
