package com.ismynr.githubuserlist.view

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.ismynr.githubuserlist.R
import com.ismynr.githubuserlist.alarm.AlarmReceiver
import com.ismynr.githubuserlist.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    companion object {
        const val PREFS_NAME = "setting_pref"
        private const val DAILY = "daily"
    }

    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var mSharedPreferences: SharedPreferences
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (supportActionBar != null) supportActionBar?.title = "Settings"

        alarmReceiver = AlarmReceiver()
        mSharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        binding.switchDaily.isChecked = mSharedPreferences.getBoolean(DAILY, false)

        binding.switchDaily.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) alarmReceiver.setDailyAlarm(this, AlarmReceiver.TYPE_DAILY, getString(R.string.daily_msg))
            else alarmReceiver.cancelAlarm(this)
            saveChange(isChecked)
        }
    }

    private fun saveChange(value: Boolean) {
        val editor = mSharedPreferences.edit()
        editor.putBoolean(DAILY, value)
        editor.apply()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}