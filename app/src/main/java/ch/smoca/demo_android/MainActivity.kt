package ch.smoca.demo_android

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputWeightField: EditText = findViewById(R.id.weight_input_field)
        val inputHeightField: EditText = findViewById(R.id.height_input_field)
        val calculateButton: Button = findViewById(R.id.calculate_button)
        val bmiLabel: TextView = findViewById(R.id.bmi_label)
        val bmiOutput: TextView = findViewById(R.id.bmi_output)

        calculateButton.setOnClickListener {
            if (!inputWeightField.text.isEmpty() && !inputHeightField.text.isEmpty()) {

                val weight = inputWeightField.text.toString().toIntOrNull()
                val height = inputHeightField.text.toString().toIntOrNull()

                if (weight != null && height != null) {

                    val body = Body(weight, height)

                    bmiOutput.text = body.bmi.toString()
                    bmiLabel.visibility = View.VISIBLE
                    bmiOutput.visibility = View.VISIBLE
                }
            }
        }
    }
}
