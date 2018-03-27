package holgus103.todolist_k.dialogs

import android.content.Context
import android.text.Editable
import android.text.SpannableStringBuilder
import android.view.View
import android.view.WindowId
import android.widget.EditText
import holgus103.todolist_k.ToDoListK
import holgus103.todolist_k.db.dao.Entry

/**
 * Created by holgus103 on 27/03/18.
 */
class AddEntryDialog(ctx: Context, value: String, msg: Int, successCallback : (c: CustomDialogBase) -> Unit)
    : CustomDialogBase(ctx, msg, successCallback) {

    override fun getView(): View = this.tx as View;

    override fun createView() {
        this.tx = EditText(this.ctx);
        this.tx!!.text = SpannableStringBuilder(value);
        this.tx!!.left = 16;
    }

    val value = value;
    var tx: EditText? = null;
}
