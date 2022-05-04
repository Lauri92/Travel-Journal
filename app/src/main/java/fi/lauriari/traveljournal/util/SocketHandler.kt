package fi.lauriari.traveljournal.util

import android.util.Log
import fi.lauriari.traveljournal.util.Constants.SERVER_BASE_URL
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

object SocketHandler {

    var socketInstance: Socket? = null

    @Synchronized
    fun setSocket(groupId: String) {
        try {
            Log.d("messagetest", "Connecting...")
            val connectionOptions = IO.Options()
            connectionOptions.query = "groupId=$groupId"
            socketInstance = IO.socket(SERVER_BASE_URL, connectionOptions)
            Log.d("messagetest", "Connected!")
        } catch (e: URISyntaxException) {

        }
    }

    @Synchronized
    fun getSocket(): Socket? {
        return socketInstance
    }

    @Synchronized
    fun establishConnection() {
        socketInstance?.connect()
    }

    @Synchronized
    fun closeConnection() {
        socketInstance?.disconnect()
    }
}