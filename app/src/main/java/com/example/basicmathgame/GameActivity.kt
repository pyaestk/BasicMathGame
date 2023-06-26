package com.example.basicmathgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.util.*
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    private lateinit var textScore: TextView
    private lateinit var textLife: TextView
    private lateinit var textTime: TextView
    private lateinit var textQuestion: TextView
    private lateinit var editTextAnswer: TextView
    private lateinit var textAlert: TextView
    private lateinit var buttonOk: Button
    private lateinit var buttonNext: Button

    private var correctAnswer = 0
    private var userScore = 0
    private var userLife = 3

    private lateinit var timer: CountDownTimer
    private val startTimerInMillis: Long = 30000
    private var timeLeftInMillis: Long = startTimerInMillis

    private var currentOperator: MainActivity.Operator = MainActivity.Operator.ADDITION

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        supportActionBar!!.title = "Math Game"

        textScore = findViewById(R.id.textViewScore)
        textLife =  findViewById(R.id.textViewLife)
        textTime = findViewById(R.id.textViewTime)
        textQuestion = findViewById(R.id.textViewQ)
        editTextAnswer = findViewById(R.id.editTextAnswer)
        textAlert = findViewById(R.id.textAlert)
        buttonNext = findViewById(R.id.buttonExit)
        buttonOk = findViewById(R.id.buttonPg)

        val intent = intent
        if (intent.hasExtra("operator")) {
            currentOperator = intent.getSerializableExtra("operator") as MainActivity.Operator
        }

        gameContinue()

        buttonOk.setOnClickListener {
            val input = editTextAnswer.text.toString()
            if (input == "") {
                textAlert.text = "Please write your answer first"
                buttonOk.visibility = View.VISIBLE
            } else {
                textAlert.text = ""
                pauseTimer()
                val userAnswer = input.toInt()
                if (userAnswer == correctAnswer) {
                    userScore += 10
                    textQuestion.text = "Your answer is correct"
                    textScore.text = userScore.toString()
                } else {
                    userLife -= 1
                    textQuestion.text = "Your answer is wrong"
                    textLife.text = userLife.toString()
                }
                buttonOk.visibility = View.INVISIBLE
            }

        }

        buttonNext.setOnClickListener {
            textAlert.text = ""
            pauseTimer()
            resetTimer()
            editTextAnswer.setText("")

            if (userLife == 0) {
                textAlert.text = "Game Over"
                val intent = Intent(this@GameActivity, ResultActivity::class.java)
                intent.putExtra("score", userScore)
                startActivity(intent)
                finish()
            } else {
                gameContinue()
            }
            buttonOk.visibility = View.VISIBLE
        }
    }

    private fun gameContinue() {
        val number1 = Random.nextInt(0, 100)
        val number2 = Random.nextInt(0, 100)

        when (currentOperator) {
            MainActivity.Operator.ADDITION -> {
                supportActionBar!!.title = "Addition"
                textQuestion.text = "$number1 + $number2"
                correctAnswer = number1 + number2
            }
            MainActivity.Operator.SUBTRACTION -> {
                supportActionBar!!.title = "Subtraction"
                textQuestion.text = "$number1 - $number2"
                correctAnswer = number1 - number2
            }
            MainActivity.Operator.MULTIPLICATION -> {
                supportActionBar!!.title = "Multiplication"
                textQuestion.text = "$number1 x $number2"
                correctAnswer = number1 * number2
            }
//            MainActivity.Operator.DIVISION -> {
//                supportActionBar!!.title = "Division"
//                textQuestion.text = "$number1 / $number2"
//                correctAnswer = number1 / number2
//            }
        }

        startTimer()
    }

    private fun startTimer() {
        timer = object : CountDownTimer(timeLeftInMillis, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateText()
            }

            override fun onFinish() {
                pauseTimer()
                resetTimer()
                updateText()

                userLife--
                textLife.text = userLife.toString()
                textQuestion.text = "Sorry, time is up!"
            }

        }.start()
    }

    private fun updateText() {
        val remainingTime: Int = (timeLeftInMillis / 1000).toInt()
        textTime.text = String.format(Locale.getDefault(), "%02d", remainingTime)
    }

    private fun pauseTimer() {
        timer.cancel()
    }

    private fun resetTimer() {
        timeLeftInMillis = startTimerInMillis
        updateText()
    }
}
