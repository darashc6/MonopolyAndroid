package services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast

/**
 * Class acting as a custom servive
 */
class MyService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    /**
     * Once the service starts, a toast shows indicating that the service has started
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "El servicio ha empezado", Toast.LENGTH_LONG).show()
        return START_NOT_STICKY
    }
}
