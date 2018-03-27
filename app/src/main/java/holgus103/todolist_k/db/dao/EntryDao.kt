package holgus103.todolist_k.db.dao

import android.preference.PreferenceManager
import android.support.v7.widget.RecyclerView
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.stmt.QueryBuilder
import holgus103.todolist_k.db.DatabaseHelper

/**
 * Created by holgus103 on 25/03/18.
 */
class EntryDao(helper : DatabaseHelper){

    companion object {
        lateinit var dao: Dao<Entry, Int>
        const val ID: String = "id";
        const val TITLE: String = "title";
        const val CREATED: String = "created";
        const val DONE: String = "done";
    }

    init {
        dao = helper.getDao(Entry::class.java)
    }

    fun add(entry: Entry){
        dao.create(entry);
    }

    fun update(entry: Entry){
        dao.update(entry)
    }

    fun delete(entry: Entry){
        dao.delete(entry)
    }

    fun getAllOrdered(column: String, desc: Boolean = false): MutableList<Entry>? {
        return dao.queryBuilder().orderBy(column, !desc).query();
    }

}