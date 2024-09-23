package com.example.myapplication.dopplertest

import android.content.Context
import android.media.AudioManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.myapplication.dopplertest.jasperlu.doppler.Doppler
import java.time.LocalDateTime

private const val TAG: String = "DopplerTestMainActivity";
@Composable
fun PlayVideoCompose(mDoppler: Doppler, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var gestureState by remember {
        mutableStateOf("")
    }
    Toast.makeText(context, "PlayVideoCompose $gestureState", Toast.LENGTH_SHORT).show()
    val dopplerText = remember {
        mutableStateListOf<String>()
    }
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri("android.resource://" + context.packageName + '/' + R.raw.rush_e))
            prepare()
            playWhenReady = true
            repeatMode = Player.REPEAT_MODE_ONE
        }
    }
    exoPlayer.volume = 0f
    PlayerSurface(
        player = exoPlayer,
        surfaceType = SURFACE_TYPE_SURFACE_VIEW,
        modifier
    )

    mDoppler.setOnGestureListener(object : Doppler.OnGestureListener{
        override fun onPush() {
            Log.d(TAG, "onPush: ");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dopplerText.add(LocalDateTime.now().toString() + "onPush")
            };
            gestureState = "onPush"
            setVolume(context, AudioManager.ADJUST_RAISE)
            Toast.makeText(context, "currentPosition: " + exoPlayer.currentPosition + exoPlayer.isCurrentMediaItemSeekable, Toast.LENGTH_SHORT).show()
            exoPlayer.seekTo(exoPlayer.currentMediaItemIndex, exoPlayer.currentPosition + 1500)

        }

        override fun onPull() {
            Log.d(TAG, "onPull: ");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dopplerText.add(LocalDateTime.now().toString() + "onPull")
            };
            gestureState = "onPull"

            setVolume(context, AudioManager.ADJUST_LOWER)
        }

        override fun onTap() {
            Log.d(TAG, "onTap: ");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dopplerText.add(LocalDateTime.now().toString() + "onTap")
            };
            gestureState = "onTap"
        }

        override fun onDoubleTap() {
            Log.d(TAG, "onDoubleTap: ");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dopplerText.add(LocalDateTime.now().toString() + "onDoubleTap")
            };
            gestureState = "onDoubleTap"
        }

        override fun onNothing() {
            // Log.d(TAG, "onNothing: ");
        }

    });
    mDoppler.setOnReadCallback(object : Doppler.OnReadCallback{
        override fun onBandwidthRead(leftBandwidth: Int, rightBandwidth: Int) {
            // Log.d(TAG, "leftBandwidth: $leftBandwidth");
            // Log.d(TAG, "rightBandwidth: $rightBandwidth");
        }

        override fun onBinsRead(bins: DoubleArray?) {
            // Log.d(TAG, "bins: $bins");
        }

    })
}

fun setVolume(context: Context, direction: Int) {
    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
    when (direction) {
        AudioManager.ADJUST_LOWER -> audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND)
        AudioManager.ADJUST_RAISE -> audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND)
        else -> throw IllegalArgumentException("Invalid volume adjustment direction")
    }
}
