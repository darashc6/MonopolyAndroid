package constants

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.darash.monopoly.R


/**
 * Class where all the constant functions are stored
 */
class Functions {
    companion object {

        /**
         * Function where an AlertDialog show when the user is about to exit the app
         */
        fun exitApplicaction(context: Context) {
            val dialog = AlertDialog.Builder(context)
            dialog.setIcon(R.drawable.ic_warning_black_24dp)
            dialog.setTitle(context.getString(R.string.exit_application_title))
            dialog.setMessage(context.getString(R.string.exit_application_message))

            dialog.setNegativeButton(context.getString(R.string.exit_application_negative), object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    Toast.makeText(context, context.getString(R.string.exit_application_negative_toast), Toast.LENGTH_LONG).show()
                }
            })

            dialog.setPositiveButton(context.getString(R.string.exit_application_positive), object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    Toast.makeText(context, context.getString(R.string.exit_application_positive_toast), Toast.LENGTH_LONG).show()
                    val activity: Activity = context as Activity
                    activity.finishAffinity()
                }
            })

            dialog.show()
        }
    }
}