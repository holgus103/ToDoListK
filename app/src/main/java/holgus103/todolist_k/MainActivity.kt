package holgus103.todolist_k

import android.app.AlertDialog
import android.app.Fragment
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
import holgus103.todolist_k.views.AppSettingsFragment
import holgus103.todolist_k.views.EntryView
import holgus103.todolist_k.views.MainFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


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
            R.id.action_settings -> {
                val transaction = fragmentManager.beginTransaction()
                transaction.hide(fragmentManager.findFragmentById(R.id.main_fragment));
                transaction.add(R.id.fragment_content, AppSettingsFragment(), "settings");
                transaction.addToBackStack("settings");
                transaction.commitAllowingStateLoss();

//                        .replace(R.id.fragment_content, AppSettingsFragment()).commit()
                true;
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
