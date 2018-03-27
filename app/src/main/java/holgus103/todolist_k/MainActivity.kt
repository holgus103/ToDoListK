package holgus103.todolist_k

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.TextView
import holgus103.todolist_k.db.dao.Entry
import holgus103.todolist_k.db.dao.EntryDao
import holgus103.todolist_k.dialogs.AddEntryDialog
import holgus103.todolist_k.dialogs.ConfirmDeletionDialog
import holgus103.todolist_k.views.EntryView
import kotlinx.android.synthetic.main.activity_main.*
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {


    private class ViewHolder(val v : View) : RecyclerView.ViewHolder(v) {

        val title = v.findViewById<TextView>(R.id.title);
        val done = v.findViewById<CheckBox>(R.id.done);
        val view = v as EntryView;


    }

    private inner class RecycleViewAdapter(data: List<Entry>) : RecyclerView.Adapter<ViewHolder>(), View.OnTouchListener, CompoundButton.OnCheckedChangeListener {

        override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
            if(buttonView?.parent is EntryView){
                val p = buttonView?.parent as EntryView
                val entry = this.data[p.index];
                entry.done = isChecked;
                ToDoListK.instance.dao.update(entry);
            }
        }

        private var pressDownTime: Long = 0;

        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            if(v is TextView){
                when (event!!.action) {
                    MotionEvent.ACTION_DOWN -> this.pressDownTime = System.currentTimeMillis()
                    MotionEvent.ACTION_UP -> {
                        if (System.currentTimeMillis() - pressDownTime > TIMEOUT) {
                            if(v.parent is EntryView) {
                                val p = v.parent as EntryView;
                                this@MainActivity.deleteEntry(this.data[p.index]);
                            }
                        }
                    }
                }
                return true;
            }
            return false;
        }

        var data = data;

        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
            if(holder?.v is EntryView) {
                holder?.title!!.text = this.data[position].title;
                holder?.view.index = position;
                holder?.title!!.setOnTouchListener(this)
                holder?.done.setOnCheckedChangeListener(this)
                holder?.done!!.isChecked = this.data[position].done;
            }
        }

        override fun getItemCount() = data.size;

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder =
            ViewHolder(
                    LayoutInflater
                            .from(parent!!.context)
                            .inflate(R.layout.entry, parent, false)
            )

    }

    private fun deleteEntry(entry: Entry) {
        ConfirmDeletionDialog(this,
                R.string.delete_message_title,
                {
                    ToDoListK.instance.dao.delete(entry);
                    this.refreshData()
                }
        ).show()
    }

    companion object {
        const val TIMEOUT = 1000;
    }

    private var tx: EditText? = null;

    private var data: MutableList<Entry>? = null;

    private fun refreshData() {
        recycler_view.adapter = RecycleViewAdapter(ToDoListK.instance.dao.getAllOrdered(EntryDao.TITLE)!!);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        recycler_view.adapter = RecycleViewAdapter(ToDoListK.instance.dao.getAllOrdered(EntryDao.TITLE)!!);
        recycler_view.setHasFixedSize(true);
        recycler_view.layoutManager = LinearLayoutManager(this);

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun addNewEntry(v: View){
        AddEntryDialog(this,
                R.string.add_entry_message,
                { c ->
                    if(c is AddEntryDialog) {
                        ToDoListK.instance.dao.add(Entry(title = c.tx?.text.toString()));
                        this.refreshData()
                    }
                }
        ).show();
    }
}
