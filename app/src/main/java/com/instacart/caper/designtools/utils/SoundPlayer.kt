package com.instacart.caper.designtools.utils

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.util.Log
import com.instacart.caper.designtools.R

object SoundPlayer {
    private const val TAG = "SoundPlayer"
    private var mediaPlayer: MediaPlayer? = null
    private var audioManager: AudioManager? = null
    private var audioFocusRequest: AudioFocusRequest? = null

    /**
     * Convenience method to play the error sound
     */
    fun playError(context: Context) {
        playSound(context, R.raw.sound_error)
    }

    /**
     * Plays a sound from the raw resources folder
     * @param context The application context
     * @param soundResourceId The resource ID of the sound file (e.g., R.raw.add_to_cart)
     */
    fun playSound(context: Context, soundResourceId: Int) {
        try {
            Log.d(TAG, "Attempting to play sound with resource ID: $soundResourceId")

            // Get AudioManager
            if (audioManager == null) {
                audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            }

            // Request audio focus
            val focusResult = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val audioAttributes = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()

                audioFocusRequest =
                    AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
                        .setAudioAttributes(audioAttributes)
                        .build()

                audioManager?.requestAudioFocus(audioFocusRequest!!)
            } else {
                @Suppress("DEPRECATION")
                audioManager?.requestAudioFocus(
                    null,
                    AudioManager.STREAM_NOTIFICATION,
                    AudioManager.AUDIOFOCUS_GAIN_TRANSIENT
                )
            }

            if (focusResult != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                Log.w(TAG, "Audio focus not granted")
            }

            // Release previous media player if it exists
            mediaPlayer?.release()

            // Create new media player with the sound resource
            mediaPlayer = MediaPlayer.create(context, soundResourceId).apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                )
            }

            if (mediaPlayer == null) {
                Log.e(TAG, "Failed to create MediaPlayer - resource not found or invalid")
                abandonAudioFocus()
                return
            }

            Log.d(TAG, "MediaPlayer created successfully, volume: ${mediaPlayer?.getVolume()}")

            mediaPlayer?.apply {
                setVolume(1.0f, 1.0f)
                setOnCompletionListener { mp ->
                    Log.d(TAG, "Sound playback completed")
                    mp.release()
                    mediaPlayer = null
                    abandonAudioFocus()
                }
                setOnErrorListener { mp, what, extra ->
                    Log.e(TAG, "MediaPlayer error: what=$what, extra=$extra")
                    mp.release()
                    mediaPlayer = null
                    abandonAudioFocus()
                    true
                }
                start()
                Log.d(TAG, "Sound playback started, is playing: ${isPlaying}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error playing sound: ${e.message}", e)
            e.printStackTrace()
            abandonAudioFocus()
        }
    }

    private fun abandonAudioFocus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            audioFocusRequest?.let {
                audioManager?.abandonAudioFocusRequest(it)
            }
        } else {
            @Suppress("DEPRECATION")
            audioManager?.abandonAudioFocus(null)
        }
    }

    private fun MediaPlayer.getVolume(): Pair<Float, Float> {
        return try {
            // MediaPlayer doesn't have a public getVolume, this is for debugging
            1.0f to 1.0f
        } catch (e: Exception) {
            0f to 0f
        }
    }

    /**
     * Release the media player resources
     * Call this when the app is being destroyed
     */
    fun release() {
        Log.d(TAG, "Releasing media player resources")
        mediaPlayer?.release()
        mediaPlayer = null
        abandonAudioFocus()
    }
}
