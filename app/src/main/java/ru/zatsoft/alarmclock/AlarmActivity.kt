package ru.zatsoft.alarmclock

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import ru.zatsoft.alarmclock.databinding.ActivityAlarmBinding
import kotlin.system.exitProcess

class AlarmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlarmBinding
    private lateinit var toolBar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolBar = binding.toolbarMain
        setSupportActionBar(toolBar)
        title = " "

        binding.gifView.setImageResource(R.drawable.alarm)
        binding.btnAlarm.setOnClickListener {
            MainActivity.clearTime = true
            finish()
            exitProcess(0)
        }
    }
}