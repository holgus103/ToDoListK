package holgus103.todolist_k.dialogs

import android.content.Context
import android.view.View
import android.widget.TextView
import holgus103.todolist_k.R

/**
 * Created by holgus103 on 27/03/18.
 */
class ConfirmDeletionDialog(ctx: Context, msg: Int, successCallback : (c: CustomDialogBase) -> Unit)
    : CustomDialogBase(ctx, msg, successCallback) {


    override fun getView(): View = this.v!!;

    override fun createView() {
        this.v = TextView(this.ctx);
        this.v?.text = this.ctx.getString(R.string.delete_message);
    }

    var v: TextView? = null;


}