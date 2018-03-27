package holgus103.todolist_k.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView

/**
 * Created by holgus103 on 27/03/18.
 */
class EntryView : LinearLayout {
    var index: Int = 0;


    constructor(ctx: Context) : super(ctx)

    constructor(ctx: Context, att: AttributeSet) : super(ctx, att)

    constructor(ctx: Context, att: AttributeSet, ref: Int) : super(ctx, att, ref)

}
