package holgus103.todolist_k.views

import android.os.Bundle
import android.preference.PreferenceFragment
import android.view.ContextMenu
import android.view.View
import holgus103.todolist_k.R

class AppSettingsFragment : PreferenceFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preference)
    }
}