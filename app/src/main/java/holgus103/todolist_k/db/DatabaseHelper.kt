package holgus103.todolist_k.db

import android.database.sqlite.SQLiteDatabase
import android.provider.ContactsContract
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import holgus103.todolist_k.ToDoListK
import holgus103.todolist_k.db.dao.Entry
import java.security.KeyStore

/**
 * Created by holgus103 on 25/03/18.
 */

class DatabaseHelper : OrmLiteSqliteOpenHelper(ToDoListK.instance, "entries.db", null, 1){


    companion object {
        lateinit var instance: DatabaseHelper
    }

    init {
        instance = DatabaseHelper();
    }

    override fun onUpgrade(database: SQLiteDatabase?, connectionSource: ConnectionSource?, oldVersion: Int, newVersion: Int) {
        TableUtils.dropTable<Entry, Any>(connectionSource, Entry::class.java, true)
    }

    override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {
        TableUtils.createTableIfNotExists(connectionSource, Entry::class.java)
    }

}