package holgus103.todolist_k

import android.app.Application
import android.provider.ContactsContract
import holgus103.todolist_k.db.DatabaseHelper
import holgus103.todolist_k.db.dao.EntryDao

/**
 * Created by holgus103 on 25/03/18.
 */
class ToDoListK : Application(){

//    private object Holder { val INSTANCE = ToDoListK()}
    companion object {
        lateinit var ins: ToDoListK;
        val instance : ToDoListK
            get() {
                return ins!!;
            }

        }

    init{
        ins = this
    }

    lateinit var helper: DatabaseHelper;
    lateinit var dao: EntryDao;

    override fun onCreate() {
        super.onCreate()
        this.helper = DatabaseHelper(this)
        this.dao = EntryDao(this.helper);
    }



}