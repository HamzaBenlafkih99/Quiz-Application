package com.example.quizapplication


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class ResultFragment(val score:Int, val name:String) : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_result, container, false)
        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val tv_score = view.findViewById<TextView>(R.id.tv_score)
        val btn_finish = view.findViewById<Button>(R.id.btn_finish)
        // TODO (STEP 6: Hide the status bar and get the details from intent and set it to the UI. And also add a click event to the finish button.)
        // START

        tv_name.text = "Congratulations $name"

        val totalQuestions = 10
        val correctAnswers = score

        tv_score.text = "Your Score is $correctAnswers out of $totalQuestions."



        btn_finish.setOnClickListener {
            (activity as MainActivity).goToHome(name)
        }
        return view
    }


}