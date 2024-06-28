package com.example.mega_sena

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.Random

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText: EditText = findViewById(R.id.edit_number)
        val txtResult: TextView = findViewById(R.id.txt_result)
        val btnGenerate: Button = findViewById(R.id.btn_generate)

        prefs = getSharedPreferences("db", MODE_PRIVATE)
        val result = prefs.getString("result", null)
        if (result != null) {
            txtResult.text = "Última aposta: $result"
        }

        btnGenerate.setOnClickListener {
            val text = editText.text.toString()
            numberGenerator(text, txtResult)
        }
    }

    private fun numberGenerator(text: String, txtResult: TextView) {

        if (text.isEmpty()) {
            Toast.makeText(this, "Por favor, insira um número entre 6 e 15", Toast.LENGTH_LONG).show()
            return
        }

        val qtd = text.toInt()
        if (qtd < 6 || qtd > 15) {
            Toast.makeText(this, "Por favor, insira um número entre 6 e 15", Toast.LENGTH_LONG).show()
            return
        }

        val numbers = mutableSetOf<Int>()
        val random = Random()

        while (numbers.size < qtd) {
            val number = random.nextInt(60) + 1
            numbers.add(number)
        }

        val resultText = numbers.joinToString(separator = " - ")
        txtResult.text = resultText //

        val editor = prefs.edit()
        editor.putString("result", resultText)
        editor.apply()
    }
}
