package com.example.projetinho4bim.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projetinho4bim.DataBase
import com.example.projetinho4bim.R
import java.util.Calendar

class AtualizaPesa : AppCompatActivity() {

    private var currentId: String? = null
    private var myDB: DataBase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.attu)

        currentId = intent.getStringExtra("bookId")
        myDB = DataBase(this)



        val buttonPickDate = findViewById<Button>(R.id.btnPickDate2)
        val textViewDate = findViewById<TextView>(R.id.tvDispDtInst2)
        val spinnerType = findViewById<Spinner>(R.id.spinner2)
        val spinnerType2 = findViewById<Spinner>(R.id.spinnertags2)
        val titleInput = findViewById<TextView>(R.id.Nomw)
        val descriptionInput = findViewById<TextView>(R.id.etDispDtInst)


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

        val btnUpdateData = findViewById<Button>(R.id.btnUpdateData)
        btnUpdateData.setOnClickListener {
            val newTitle = titleInput.text.toString().trim()
            val newType = spinnerType.selectedItem.toString()
            val newDate = textViewDate.text.toString().trim()
            val newDescription = descriptionInput.text.toString().trim()
            val newTags = spinnerType2.selectedItem.toString()

            if (newTitle.isNotEmpty() && newType.isNotEmpty() && newDate.isNotEmpty() && newDescription.isNotEmpty() && newTags.isNotEmpty()) {
                updateData(newTitle, newDate, newType, newDescription, newTags)
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
            val intent = Intent(this, PesaDetails::class.java)
            startActivity(intent)
        }
    }

    private fun updateData(newTitle: String, newDate: String, newType: String, newDescription: String, newTags: String) {
        currentId?.let {
            val isUpdated = myDB?.updateData(it, newTitle, newDate, newType, newDescription, newTags) == true
            if (isUpdated) {
                Toast.makeText(this, "Dados atualizados com sucesso.", Toast.LENGTH_SHORT).show()
                finish() // Opcional: pode voltar para a tela anterior após atualizar
            } else {
                Toast.makeText(this, "Falha ao atualizar dados.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
