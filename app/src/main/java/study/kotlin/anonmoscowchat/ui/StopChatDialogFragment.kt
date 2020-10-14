package study.kotlin.anonmoscowchat.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import study.kotlin.anonmoscowchat.commons.constants.ChatActivityConstants

class StopChatDialogFragment(private val chatActivity: ChatActivity, val mode : Int) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        when(mode){
            ChatActivityConstants.STOP_CHAT_DIALOG_MODE->{
                builder.setMessage("Вы действительно хотите завершить диалог?")
                    .setPositiveButton("Да"){ dialogInterface: DialogInterface, i: Int ->
                    chatActivity.onClickStopChat()
                }
                builder.setNegativeButton("Нет"){ dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.cancel()
                }
            }
            ChatActivityConstants.CHAT_STOPPED_DIALOG_MODE-> {
                 builder.setMessage("Ваш собеседник прекратил диалог")
                    .setPositiveButton("Ок"){ dialogInterface: DialogInterface, _: Int ->
                        dialogInterface.cancel()
                    }
            }
        }
        return builder.create()
    }
}