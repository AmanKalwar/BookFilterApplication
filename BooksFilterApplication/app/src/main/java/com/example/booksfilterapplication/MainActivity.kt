package com.example.booksfilterapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val authorInputView = findViewById<TextInputLayout>(R.id.AuthorInput)
        val countryInputView = findViewById<TextInputLayout>(R.id.CountryInput)
        val resultCount = findViewById<TextView>(R.id.ResultCountTextView)
        val resultOne = findViewById<TextView>(R.id.Result1TextView)
        val resultTwo = findViewById<TextView>(R.id.Result2TextView)
        val resultThree = findViewById<TextView>(R.id.Result3TextView)
        val applyFilterButton = findViewById<Button>(R.id.button)
        val titles = mutableListOf<String>()

        applyFilterButton.setOnClickListener {
            titles.clear()
            resultCount.text = ""
            resultOne.text = ""
            resultTwo.text = ""

            val myApplication = application as MyApplication
            val httpApiService = myApplication.httpApiService
            CoroutineScope(Dispatchers.Default).launch {
                val decodedJsonResult = httpApiService.getBooksData()

                for (item in decodedJsonResult) {
                    if (item.author.lowercase() == authorInputView.editText?.text.toString()
                            .lowercase() && item.country.lowercase() == countryInputView.editText?.text.toString()
                            .lowercase()
                    ) {
                        titles.add(item.title)
                    }
                }

                    withContext(Dispatchers.Main) {
                        resultCount.text = "Result: " + titles.count().toString()
                        if (titles.count() > 0)
                            resultOne.text = "Result: " + titles[0]
                        if (titles.count() > 1)
                            resultTwo.text = "Result: " + titles[1]
                        if (titles.count() > 2)
                            resultThree.text = "Result: " + titles[2]
                    }

            }

        }
    }
}


