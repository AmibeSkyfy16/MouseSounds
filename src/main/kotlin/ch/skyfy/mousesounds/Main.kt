package ch.skyfy.mousesounds

import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.NativeHookException
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener
import com.goxr3plus.streamplayer.enums.Status
import com.goxr3plus.streamplayer.stream.StreamPlayer
import com.goxr3plus.streamplayer.stream.StreamPlayerEvent
import com.goxr3plus.streamplayer.stream.StreamPlayerListener


class Main : NativeMouseInputListener {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val main = Main()
        }
    }

    val map: MutableList<StreamPlayer> = mutableListOf()

    init {
        try {
            GlobalScreen.registerNativeHook()
        } catch (ex: NativeHookException) {
            System.err.println("There was a problem registering the native hook.")
            System.err.println(ex.message)
            System.exit(1)
        }
        GlobalScreen.addNativeMouseListener(this)


//        streamPlayer.status

//        addStreamPlayerListener(this)
    }

    override fun nativeMouseClicked(e: NativeMouseEvent) {

        val streamPlayer = StreamPlayer()
        streamPlayer.addStreamPlayerListener(object : StreamPlayerListener{
            override fun opened(dataSource: Any?, properties: MutableMap<String, Any>?) {

            }

            override fun progress(nEncodedBytes: Int, microsecondPosition: Long, pcmData: ByteArray?, properties: MutableMap<String, Any>?) {

            }

            override fun statusUpdated(event: StreamPlayerEvent?) {
                if(event?.playerStatus == Status.STOPPED || event?.playerStatus == Status.SEEKED){
                    map.remove(streamPlayer)
                }
            }
        })
        map.add(streamPlayer)

        streamPlayer.open(javaClass.classLoader.getResourceAsStream("mouse-click.mp3"))
        streamPlayer.play()
    }

}