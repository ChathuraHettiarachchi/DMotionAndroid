package com.choota.dmotion.presentation.common.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatButton
import com.choota.dmotion.R

object NetworkStateDialog {
    lateinit var dialog: Dialog

    fun show(context: Context) {
        dialog = Dialog(context, R.style.AppCompatAlertDialogStyle)
        dialog.setContentView(R.layout.dialog_no_connection)
        ((dialog).findViewById<AppCompatButton>(R.id.btnOk)).setOnClickListener { dialog.dismiss() }

        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.show()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
    }
}