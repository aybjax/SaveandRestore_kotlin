package com.aybjax.saveandrestore

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.*
import kotlin.reflect.typeOf

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
        private const val DISCOUNT_CONFIRMATION_MESSAGE = "DISCOUNT_CONFIRMATION_MESSAGE"
        const val DISCOUNT_CODE = "DISCOUNT_CODE"
    }

    private val discount_button: Button
        get() = findViewById(R.id.discount_button)
    private val first_name: EditText
        get() = findViewById(R.id.first_name)
    private val last_name: EditText
        get() = findViewById(R.id.last_name)
    private val email: EditText
        get() = findViewById(R.id.email)
    val discount_code_confirmation: TextView
        get() = findViewById(R.id.discount_code_confirmation)
    val discount_code: TextView get() = findViewById(R.id.discount_code)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate")

        discount_button.setOnClickListener {
            val first_name = first_name.text.toString().trim()
            val last_name = last_name.text.toString().trim()
            val email = email.text.toString()

            if (first_name.isEmpty() || last_name.isEmpty() || email.isEmpty()){
                Toast.makeText(this, getString(R.string.add_text_validation), Toast.LENGTH_LONG).show()
            }
            else {
                val full_name = first_name.plus(" ").plus(last_name)

                discount_code_confirmation.text =
                    getString(R.string.discount_code_confirmation, full_name)
                // Generates discount code
                discount_code.text = UUID.randomUUID().toString().take(8).uppercase()

                hideKeyboard()
                clearInputFields()
            }
        }
    }

    private fun clearInputFields() {
        first_name.text.clear()
        last_name.text.clear()
        email.text.clear()
    }

    private fun hideKeyboard() {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }

    /* Save State */

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d(TAG, "onRestoreInstanceState")

        discount_code.text = savedInstanceState.getString(DISCOUNT_CODE, "")
        discount_code_confirmation.text = savedInstanceState.getString(DISCOUNT_CONFIRMATION_MESSAGE, "")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState")

        outState.putString(DISCOUNT_CODE, discount_code.text.toString())
        outState.putString(DISCOUNT_CONFIRMATION_MESSAGE, discount_code_confirmation.text.toString())
    }
}