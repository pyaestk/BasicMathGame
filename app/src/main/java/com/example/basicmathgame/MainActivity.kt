package com.example.basicmathgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var addition: Button
    private lateinit var subtraction: Button
    private lateinit var multiplication: Button
//    private lateinit var division: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addition = findViewById(R.id.buttonAdd)
        subtraction = findViewById(R.id.buttonSub)
        multiplication = findViewById(R.id.buttonMulti)
        //division = findViewById(R.id.buttondivi)

        addition.setOnClickListener {
            startGame(Operator.ADDITION)
        }

        subtraction.setOnClickListener {
            startGame(Operator.SUBTRACTION)
        }

        multiplication.setOnClickListener {
            startGame(Operator.MULTIPLICATION)
        }

//        division.setOnClickListener {
//            startGame(Operator.DIVISION)
//        }
    }

    private fun startGame(operator: Operator) {
        val intent = Intent(this@MainActivity, GameActivity::class.java)
        intent.putExtra("operator", operator)
        startActivity(intent)
    }

    enum class Operator {
        ADDITION,
        SUBTRACTION,
        MULTIPLICATION,
        //DIVISION
    }
}
