package holgus103.todolist_k.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.widget.EditText
import holgus103.todolist_k.R

/**
 * Created by holgus103 on 27/03/18.
 */
abstract class CustomDialogBase(ctx: Context, msg: Int, successCallback : (c: CustomDialogBase) -> Unit)
    : DialogInterface.OnClickListener {

    abstract fun createView();
    abstract fun getView() : View;

    val successCallback = successCallback;
    val ctx = ctx;
    val msg = msg;

    override fun onClick(dialog: DialogInterface?, which: Int) {
        when(which){
            DialogInterface.BUTTON_POSITIVE -> this.successCallback(this);
            DialogInterface.BUTTON_NEGATIVE -> dialog?.cancel();
        }
    }

    fun show() {
        this.createView()
        AlertDialog.Builder(this.ctx)
                .setMessage(this.ctx.getString(msg))
                .setView(this.getView())
                .setPositiveButton(this.ctx.getString(R.string.ok), this)
                .setNegativeButton(this.ctx.getString(R.string.cancel), this)
                .show()
    }
}