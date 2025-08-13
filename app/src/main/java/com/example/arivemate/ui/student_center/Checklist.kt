package com.example.arivemate.ui.student_center

import android.os.Bundle
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.arivemate.R
import org.json.JSONArray
import org.json.JSONObject

class Checklist : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_checklist)
        val country = intent.getStringExtra("country") ?: return
        val layout = findViewById<LinearLayout>(R.id.layoutChecklist)

        val json = assets.open("checklists.json").bufferedReader().use { it.readText() }
        val rootArray = JSONArray(json)

// Find the JSONObject inside the array for the given country
        var countryData: JSONObject? = null
        for (i in 0 until rootArray.length()) {
            val obj = rootArray.getJSONObject(i)
            if (obj.has(country)) {
                countryData = obj.getJSONObject(country)
                break
            }
        }
        if (countryData == null) return // country not found

        val categories = countryData.keys()
        while (categories.hasNext()) {
            val category = categories.next()

            val title = TextView(this)
            title.text = "📋 $category"
            title.textSize = 18f
            title.setPadding(0, 16, 0, 8)
            layout.addView(title)

            val items = countryData.getJSONArray(category)
            for (i in 0 until items.length()) {
                val checkBox = CheckBox(this)
                checkBox.text = items.getString(i)
                layout.addView(checkBox)
            }
        }
    }
}