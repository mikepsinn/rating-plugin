package com.quantimodo.ratingplugin.activities;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.Toast;

import com.quantimodo.ratingplugin.Global;
import com.quantimodo.ratingplugin.R;
import com.quantimodo.ratingplugin.databases.MoodDatabaseHelper;
import com.quantimodo.ratingplugin.receivers.MoodTimeReceiver;
import com.quantimodo.ratingplugin.things.Question;


@SuppressWarnings("deprecation")
public class SettingsActivity extends PreferenceActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		addPreferencesFromResource(R.xml.settings);

		initRatingPreferences();
		initQuantimodoPreferences();
		initDataPreferences();
	}

	@Override
	protected void onResume()
	{
		super.onResume();

		Global.init(this);
	}

	@Override
	protected void onPause()
	{
		super.onPause();

		Global.init(this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case android.R.id.home:
			Intent intent = new Intent(this, MainActivity.class);
			NavUtils.navigateUpTo(this, intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void initRatingPreferences()
	{
		ListPreference listPreference = (ListPreference) findPreference("moodInterval");
		String currentValue = listPreference.getEntries()[Global.moodInterval].toString();
		listPreference.setSummary(currentValue);
		listPreference.setOnPreferenceChangeListener(onMoodIntervalChanged);

		Question[] questions = Question.getAllQuestions(this);
		CharSequence[] questionEntries = new CharSequence[questions.length - 1];
		CharSequence[] questionEntryValues = new CharSequence[questions.length - 1];

		// We're not listing "Mood", so we start at 1
		for(int i = 1; i< questions.length; i++)
		{
			Question question = questions[i];

			int startLocation = question.title.indexOf("{") + 1;
			int endLocation = question.title.indexOf("}", startLocation);

			questionEntries[i - 1] = question.title.substring(startLocation, endLocation);
			questionEntryValues[i - 1] = String.valueOf(question.resultType);
		}

		//
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String[] selectionValues = prefs.getStringSet("requiredRatings", null).toArray(new String[]{});
		String summary = "";
		if(selectionValues.length > 0)
		{
			for (String selectionValue : selectionValues)
			{
				for (int n = 0; n < questionEntryValues.length; n++)
				{
					CharSequence questionEntryValue = questionEntryValues[n];
					if (questionEntryValue.equals(selectionValue))
					{
						CharSequence selectedQuestion = questionEntries[n];
						summary = summary.concat(selectedQuestion + ", ");
						break;
					}
				}
			}
			summary = summary.substring(0, summary.length() - 2);
		}
		else
		{
			summary = getString(R.string.pref_rating_required_none);
		}




	}

	private void initQuantimodoPreferences()
	{


		CheckBoxPreference syncMoodPreference = (CheckBoxPreference) findPreference("syncMoods");


		Preference preference = findPreference("linkQuantimodoAccount");
		preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
		{
			@Override public boolean onPreferenceClick(Preference preference)
			{
				return false;
			}
		});

			preference.setSummary(R.string.pref_quantimodo_account_notinstalled);
			syncMoodPreference.setEnabled(false);

	}

	private void initDataPreferences()
	{
		Preference preference = findPreference("exportData");
		if (preference != null)
		{
			preference.setOnPreferenceClickListener(onExportDataPreferenceClicked);
		}
	}

	private Preference.OnPreferenceClickListener onExportDataPreferenceClicked = new Preference.OnPreferenceClickListener()
	{
		@Override
		public boolean onPreferenceClick(Preference preference)
		{
			final Context context = preference.getContext();

			final ProgressDialog dialog = new ProgressDialog(context);
			dialog.setMessage(context.getString(R.string.pref_data_exporting));
			dialog.show();

			final Handler handler = new Handler();
			Runnable run = new Runnable()
			{
				@Override public void run()
				{
					MoodDatabaseHelper helper = new MoodDatabaseHelper(context);
					final Uri exportedFileUri = helper.exportCsv();

					handler.post(new Runnable()
					{
						@Override public void run()
						{
							dialog.dismiss();


							if(exportedFileUri == null)
							{
								Toast.makeText(context, "Something went wrong exporting your ratings", Toast.LENGTH_SHORT).show();
							}
							else
							{
								final Intent sharingIntent = new Intent();
								sharingIntent.setAction(Intent.ACTION_SEND);
								sharingIntent.setType("text/csv");
								sharingIntent.putExtra(Intent.EXTRA_STREAM, exportedFileUri);
								context.startActivity(Intent.createChooser(sharingIntent, "Share with..."));
							}
						}
					});
				}
			};
			new Thread(run).start();

			return false;
		}
	};

	private Preference.OnPreferenceChangeListener onMoodIntervalChanged = new Preference.OnPreferenceChangeListener()
	{
		@Override public boolean onPreferenceChange(Preference preference, Object o)
		{
			Global.moodInterval = Integer.valueOf((String) o);

			String currentValue = ((ListPreference) preference).getEntries()[Global.moodInterval].toString();
			preference.setSummary(currentValue);

			MoodTimeReceiver.setAlarm(preference.getContext(), Global.moodInterval);

			return true;
		}
	};
}
