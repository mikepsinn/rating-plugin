package com.quantimodo.ratingplugin;

import android.app.backup.BackupAgentHelper;
import android.app.backup.SharedPreferencesBackupHelper;
import com.quantimodo.ratingplugin.databases.DatabaseBackupHelper;
import com.quantimodo.ratingplugin.databases.MoodDatabaseHelper;

public class MoodiModoBackupAgent extends BackupAgentHelper
{
	static final String DATABASES_KEY = "MoodDatabase";
	static final String PREFS_KEY = "Preferences";

	@Override
	public void onCreate()
	{
		DatabaseBackupHelper dbHelper = new DatabaseBackupHelper(this, MoodDatabaseHelper.DATABASE_NAME);
		addHelper(DATABASES_KEY, dbHelper);

		SharedPreferencesBackupHelper spHelper = new SharedPreferencesBackupHelper(this, "com.quantimodo.ratingplugin_preferences");
		addHelper(PREFS_KEY, spHelper);
	}
}