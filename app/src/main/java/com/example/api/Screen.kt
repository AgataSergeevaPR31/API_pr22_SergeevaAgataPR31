package com.example.api

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject

class Screen : AppCompatActivity() {
    lateinit var city: EditText
    lateinit var nameofcity1: TextView
    lateinit var temper: TextView
    lateinit var temper1: TextView
    lateinit var pascal:   TextView
    lateinit var pascal1:   TextView
    lateinit var scorost: TextView
    lateinit var scorost1: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen)
        city = findViewById<EditText>(R.id.city)

        nameofcity1 = findViewById(R.id.nameofcity1)

        temper = findViewById(R.id.temper)
        temper1 = findViewById(R.id.temper1)

        pascal = findViewById(R.id.pascal)
        pascal1 = findViewById(R.id.pascal1)

        scorost = findViewById(R.id.scorost)
        scorost1 = findViewById(R.id.scorost1)

        hide()
    }

    private fun hide()
    {
        nameofcity1.visibility = View.GONE

        temper.visibility = View.GONE
        temper1.visibility = View.GONE

        pascal.visibility = View.GONE
        pascal1.visibility = View.GONE

        scorost.visibility = View.GONE
        scorost1.visibility = View.GONE
    }

    private fun show()
    {
        nameofcity1.visibility = View.VISIBLE

        temper.visibility = View.VISIBLE
        temper1.visibility = View.VISIBLE

        pascal.visibility = View.VISIBLE
        pascal1.visibility = View.VISIBLE

        scorost.visibility = View.VISIBLE
        scorost1.visibility = View.VISIBLE
    }

    fun goon(view: View) {
        if (city.text.toString().isNotEmpty() && city.text.toString() != null) {
            var key = "0de4963a58131c332d763af70d124109"
            var url =
             "https://api.openweathermap.org/data/2.5/weather?q=" + city.text.toString() + "&appid=" + key + "&units=metric&lang=ru"
        val queue = Volley.newRequestQueue(this)
            val stringRequest = StringRequest(
                Request.Method.GET,
                url,
                {
                    response ->
                    val obj = JSONObject(response)

                    val cityName = obj.getString("name")
                    nameofcity1.text=cityName

                    val tempOBJ = obj.getJSONObject("main")
                    val temp = tempOBJ.getDouble("temp")
                    temper1.text="$temp °C"

                    val airOBJ = obj.getJSONObject("main")
                    val pressure = airOBJ.getDouble("pressure")
                    pascal1.text=pressure.toString()

                    val windObject = obj.getJSONObject("wind")
                    val windSpeed = windObject.getDouble("speed")
                    scorost1.text = "$windSpeed м/с"

                    show()
                },
                {
                    hide()

                    val sn =  Snackbar.make(view,"Неправильно введены данные о городе.", Snackbar.LENGTH_LONG)
                    sn.setActionTextColor(Color.WHITE)
                    sn.setBackgroundTint(Color.RED)
                    sn.show()
                }
            )
            queue.add(stringRequest)
        }
        else
        {
            hide()

            val sn =  Snackbar.make(view,"Введите город.", Snackbar.LENGTH_LONG)
            sn.setActionTextColor(Color.WHITE)
            sn.setBackgroundTint(Color.RED)
            sn.show()
        }
    }
}
