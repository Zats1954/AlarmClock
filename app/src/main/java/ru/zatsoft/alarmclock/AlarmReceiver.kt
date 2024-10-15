package ru.zatsoft.alarmclock

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi

class AlarmReceiver: BroadcastReceiver() {
    var ringtone: Ringtone? = null
    override fun onReceive(context: Context?, intent: Intent?) {
        val audioManager = context?.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.ringerMode = AudioManager.RINGER_MODE_NORMAL
        var notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        ringtone  = RingtoneManager.getRingtone(context, notification)
        if(ringtone == null){
            notification = RingtoneManager.getDefaultUri((RingtoneManager.TYPE_RINGTONE))
            ringtone = RingtoneManager.getRingtone(context, notification)
        }
        if(ringtone != null){
            ringtone?.play()
        }
        val intentRing: Intent = Intent(context.applicationContext, AlarmActivity::class.java)
        intentRing.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intentRing)
    }

}
