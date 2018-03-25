package holgus103.todolist_k

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import holgus103.todolist_k.db.dao.Entry

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), DialogInterface.OnClickListener {

    private var tx: EditText? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        recycler_view.apply {
//            adapter = Adap
        }
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
            DialogInterface.BUTTON_POSITIVE -> ToDoListK.instance.dao.add(Entry(title = tx?.text.toString()));
            DialogInterface.BUTTON_NEGATIVE -> dialog?.cancel();
        }
    }
}
