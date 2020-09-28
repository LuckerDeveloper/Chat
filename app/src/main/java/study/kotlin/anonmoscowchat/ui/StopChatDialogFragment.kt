package study.kotlin.anonmoscowchat.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class StopChatDialogFragment(private val chatActivity: ChatActivity) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage("Вы действительно хотите завершить диалог?")
        builder.setPositiveButton("Да"){ dialogInterface: DialogInterface, i: Int ->
            chatActivity.stopChat()
        }
        builder.setNegativeButton("Нет"){ dialogInterface: DialogInterface, i: Int ->
            dialogInterface.cancel()
        }

        return builder.create()

    }
}