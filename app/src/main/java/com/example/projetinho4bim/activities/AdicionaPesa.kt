package com.example.projetinho4bim.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import androidx.appcompat.app.AppCompatActivity
import com.example.projetinho4bim.DataBase
import com.example.projetinho4bim.R
import java.util.Calendar

class AdicionaPesa: AppCompatActivity() {
    private lateinit var dbHelper: DataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addpesa)

        dbHelper = DataBase(this)

        val titleInput = findViewById<TextInputEditText>(R.id.titulo)
        val descriptionInput = findViewById<TextInputEditText>(R.id.descricao)
        val spinnerType = findViewById<Spinner>(R.id.spinner)
        val spinnerType2 = findViewById<Spinner>(R.id.spinnertags)
        val textViewDate = findViewById<TextView>(R.id.tvDispDtInst)
        val buttonPickDate = findViewById<Button>(R.id.btnPickDate)
        val buttonAdd = findViewById<Button>(R.id.bttnadd)

        val types = arrayOf("Tranquilo", "Sustinho", "Assustador", "HORROR")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, types)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerType.adapter = adapter

        val types2 = arrayOf("Monstros", "Mar", "Notas", "Bolos")
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, types2)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerType2.adapter = adapter2

        buttonPickDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    textViewDate.text = formattedDate
                }, year, month, day)
            datePickerDialog.show()
        }

        buttonAdd.setOnClickListener {
            val title = titleInput.text.toString().trim()
            val description = descriptionInput?.text.toString().trim()
            val type = spinnerType.selectedItem.toString()
            val date = textViewDate.text.toString().trim()
            val type2 = spinnerType2.selectedItem.toString()

            if (title.isNotEmpty() && date != "Data de Instalação") {
                val result = dbHelper.insertData(title, description, type, date, type2)
                if (result != -1L) {
                    Toast.makeText(this, "Dados inseridos com sucesso!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Erro ao inserir dados", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, preencha o título e selecione uma data", Toast.LENGTH_SHORT).show()
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
