package ru.zatsoft.alarmclock

import android.app.AlarmManager
import android.app.AlarmManager.RTC_WAKEUP
import android.app.PendingIntent
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import ru.zatsoft.alarmclock.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    companion object {
        var clearTime = true
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var toolBar: Toolbar
    private var calendar: Calendar? = null
    private var timePicker: MaterialTimePicker? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        toolBar = binding.toolbarMain
        setSupportActionBar(toolBar)
        title = " "

        binding.btnAlarm.setOnClickListener {
            clearTime = false /* Время булильника показывать */
            timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Выберете время будильника")
                .build()
            timePicker?.addOnPositiveButtonClickListener {
                calendar = Calendar.getInstance()
                calendar?.set(Calendar.HOUR_OF_DAY, timePicker?.hour ?: 0)
                calendar?.set(Calendar.MINUTE, timePicker?.minute ?: 0)
                calendar?.set(Calendar.SECOND, 0)
                calendar?.set(Calendar.MILLISECOND, 0)

                val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
                alarmManager.setExact(
                    RTC_WAKEUP,
                    calendar?.timeInMillis!!,
                    getAlarmPendingIntent()!!
                )
                Toast.makeText(
                    this,
                    "Будильник установлен на  ${calendar?.time?.let { it1 -> dateFormat.format(it1) }}",
                    Toast.LENGTH_LONG
                ).show()
                binding.tvEx.text = calendar?.time?.let { it1 -> dateFormat.format(it1).toString() }
                binding.tvEx.visibility = View.VISIBLE
            }
            timePicker?.show(supportFragmentManager, "tag_picker")
        }
    }

    override fun onResume() {
        super.onResume()
        if (clearTime) {
            binding.tvEx.visibility = View.GONE
        }
    }

    private fun getAlarmPendingIntent(): PendingIntent? {
        val intent = Intent(this, AlarmReceiver::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        return PendingIntent.getBroadcast(
            this, 1, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.exit)
            finish()
        return super.onOptionsItemSelected(item)
    }

}