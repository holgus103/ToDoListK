package holgus103.todolist_k

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import holgus103.todolist_k.db.dao.Entry
import holgus103.todolist_k.db.dao.EntryDao

import kotlinx.android.synthetic.main.activity_main.*
import org.w3c.dom.Text

class MainActivity : AppCompatActivity(), DialogInterface.OnClickListener {


    private class ViewHolder(val v : View) : RecyclerView.ViewHolder(v) {

        val title = v.findViewById<TextView>(R.id.title);
        val done = v.findViewById<CheckBox>(R.id.done);

    }

    private class RecycleViewAdapter(data: List<Entry>) : RecyclerView.Adapter<ViewHolder>(), View.OnTouchListener {

        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            when(event.action){
//                this.timer
            }
        }

        var data = data;

        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
            holder?.title!!.text = this.data[position].title;
            holder?.title!!.setOnTouchListener(this)
            holder?.done!!.isChecked == this.data[position].done;
        }

        override fun getItemCount() = data.size;

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder =
            ViewHolder(
                    LayoutInflater
                            .from(parent!!.context)
                            .inflate(R.layout.entry, parent, false)
            )

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
        this.tx = EditText(this);
        AlertDialog.Builder(this)
                .setMessage(getString(R.string.add_entry_message))
                .setView(tx)
                .setPositiveButton(getString(R.string.ok), this)
                .setNegativeButton(getString(R.string.cancel), this)
                .show()
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        when(which){
            DialogInterface.BUTTON_POSITIVE -> {
                ToDoListK.instance.dao.add(Entry(title = tx?.text.toString()))
                this.refreshData();
            }
            DialogInterface.BUTTON_NEGATIVE -> dialog?.cancel();
        }
    }
}
