package holgus103.todolist_k.db.dao

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.util.*

@DatabaseTable(tableName = "Entries")
data class Entry(

        @DatabaseField(generatedId = true, columnName = EntryDao.ID)
        var id: Int? = null,

        @DatabaseField(columnName = EntryDao.TITLE)
        var title: String = "",

        @DatabaseField(columnName = EntryDao.CREATED)
        var created: Date = Date(),

        @DatabaseField(columnName = EntryDao.DONE)
        var done: Boolean = false
)