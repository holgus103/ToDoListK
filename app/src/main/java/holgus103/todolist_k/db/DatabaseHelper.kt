package holgus103.todolist_k.db

import android.database.sqlite.SQLiteDatabase
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import holgus103.todolist_k.ToDoListK
import holgus103.todolist_k.db.dao.Entry

/**
 * Created by holgus103 on 25/03/18.
 */

class DatabaseHelper(app : ToDoListK) : OrmLiteSqliteOpenHelper(app.applicationContext, "entries.db", null, 1){

    override fun onUpgrade(database: SQLiteDatabase?, connectionSource: ConnectionSource?, oldVersion: Int, newVersion: Int) {
        TableUtils.dropTable<Entry, Any>(connectionSource, Entry::class.java, true)
    }

    override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {
        TableUtils.createTableIfNotExists(connectionSource, Entry::class.java)
    }

}