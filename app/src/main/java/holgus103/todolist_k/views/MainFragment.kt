package holgus103.todolist_k.views

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.TextView
import holgus103.todolist_k.R
import holgus103.todolist_k.ToDoListK
import holgus103.todolist_k.db.dao.Entry
import holgus103.todolist_k.db.dao.EntryDao
import holgus103.todolist_k.dialogs.AddEntryDialog
import holgus103.todolist_k.dialogs.ConfirmDeletionDialog
import kotlinx.android.synthetic.main.fragment_main.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MainFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MainFragment : android.app.Fragment() {


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
                        if(v.parent is EntryView) {
                            val p = v.parent as EntryView;
                            if (System.currentTimeMillis() - pressDownTime > TIMEOUT) {
                                this@MainFragment.deleteEntry(this.data[p.index]);
                            }
                            else{
                                this@MainFragment.editEntry(this.data[p.index])
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

    private fun editEntry(entry: Entry) {
        AddEntryDialog(this.activity,
                entry.title,
                R.string.add_entry_message,
                { c ->
                    if(c is AddEntryDialog) {
                        entry.title = c.tx!!.text.toString();
                        ToDoListK.instance.dao.update(entry);
                        this.refreshData()
                    }
                }
        ).show();
    }

    private fun deleteEntry(entry: Entry) {
        ConfirmDeletionDialog(this.activity,
                R.string.delete_message_title,
                {
                    ToDoListK.instance.dao.delete(entry);
                    this.refreshData()
                }
        ).show()
    }

    private var tx: EditText? = null;

    private var data: MutableList<Entry>? = null;

    private fun refreshData() {
        val m = PreferenceManager.getDefaultSharedPreferences(this.activity);
        val column = m.getString(getString(R.string.sorting_key), EntryDao.TITLE)
        recycler_view.adapter = RecycleViewAdapter(ToDoListK.instance.dao.getAllOrdered(column)!!);
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.fab.setOnClickListener { v-> this.addNewEntry(v) }
        this.refreshData();
        recycler_view.setHasFixedSize(true);
        recycler_view.layoutManager = LinearLayoutManager(this.activity);

    }


    fun addNewEntry(v: View){
        AddEntryDialog(this.activity,
                "",
                R.string.add_entry_message,
                { c ->
                    if(c is AddEntryDialog) {
                        ToDoListK.instance.dao.add(Entry(title = c.tx?.text.toString()));
                        this.refreshData();
                        this.view.requestFocus();
                    }
                }
        ).show();
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }


    companion object {
        const val TIMEOUT = 1000;
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                MainFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
