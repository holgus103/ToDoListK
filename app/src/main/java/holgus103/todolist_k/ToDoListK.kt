package holgus103.todolist_k

import android.app.Application
import holgus103.todolist_k.db.dao.EntryDao

/**
 * Created by holgus103 on 25/03/18.
 */
class ToDoListK : Application(){

    companion object {
        lateinit var instance: ToDoListK
    }

    init {
        instance = this;
    }

    val dao = EntryDao();

}