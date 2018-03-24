package holgus103.todolist_k.db

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.util.*

@DatabaseTable(tableName = "Entries")
data class Entry(

        @DatabaseField(generatedId = true)
        var id: Int? = null,

        @DatabaseField
        var title:String = "",

        @DatabaseField
        var comment:String = "",

        @DatabaseField
        var created: Date = Date()
)