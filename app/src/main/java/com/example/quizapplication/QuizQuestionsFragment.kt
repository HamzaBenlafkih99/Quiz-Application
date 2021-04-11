package com.example.quizapplication

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat

private const val ARG_PARAM = "identifiant"
class QuizQuestionsFragment : Fragment(), View.OnClickListener {
    private var identifiant: Int = 0
    var list = ArrayList<Question>()
    private var mCurrentPosition: Int = 1 // Default and the first question position
    private var mQuestionsList: ArrayList<Question>? = null

    private var mSelectedOptionPosition: Int = 0
    private var mCorrectAnswers: Int = 0

    private var mUserName: String? = null

    lateinit var tv_option_one: TextView
    lateinit var tv_option_two: TextView
    lateinit var tv_option_three: TextView
    lateinit var tv_option_four: TextView
    lateinit var btn_submit : Button
    lateinit var progressBar: ProgressBar
    lateinit var tv_progress: TextView
    lateinit var tv_question: TextView
    lateinit var btn_return: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            identifiant = it.getInt(ARG_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_quiz_questions, container, false)

        tv_option_one = view.findViewById(R.id.tv_option_one)
        tv_option_two = view.findViewById(R.id.tv_option_two)
        tv_option_three = view.findViewById(R.id.tv_option_three)
        tv_option_four = view.findViewById(R.id.tv_option_four)
        btn_submit = view.findViewById(R.id.btn_submit)
        progressBar = view.findViewById(R.id.progressBar)
        tv_progress = view.findViewById(R.id.tv_progress)
        tv_question = view.findViewById(R.id.tv_question)
        btn_return = view.findViewById(R.id.btn_return)

        btn_return.setOnClickListener {
            if (mCurrentPosition != 1){
                mCurrentPosition--

                setQuestion()
            }
        }

        val db by lazy { DatabaseHandler(context!!) }
        mQuestionsList = db.viewQuestion(identifiant).shuffled() as ArrayList<Question>

        setQuestion()

        tv_option_one.setOnClickListener(this)
        tv_option_two.setOnClickListener(this)
        tv_option_three.setOnClickListener(this)
        tv_option_four.setOnClickListener(this)
        btn_submit.setOnClickListener(this)
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(identifiant: Int) =
            QuizQuestionsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM, identifiant)
                }
            }
    }

    private fun setQuestion() {

        val question = mQuestionsList!!.get(mCurrentPosition - 1) // Getting the question from the list with the help of current position.

        defaultOptionsView()

        if (mCurrentPosition == mQuestionsList!!.size) {
            btn_submit.text = "FINISH"
        } else {
            btn_submit.text = "SUIVANT"
        }

        progressBar.progress = mCurrentPosition
        tv_progress.text = "$mCurrentPosition" + "/" + progressBar.getMax()

        tv_question.text = question.question
        tv_option_one.text = question.optionOne
        tv_option_two.text = question.optionTwo
        tv_option_three.text = question.optionThree
        tv_option_four.text = question.optionFour
        when(question.chosenAnswer){
            1 -> selectedOptionView(tv_option_one, 1)
            2 -> selectedOptionView(tv_option_two, 2)
            3 -> selectedOptionView(tv_option_three, 3)
            4 -> selectedOptionView(tv_option_four, 4)
        }

    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {

        defaultOptionsView()

        mSelectedOptionPosition = selectedOptionNum

        mQuestionsList!!.get(mCurrentPosition - 1).chosenAnswer = selectedOptionNum


        tv.setTextColor(
            Color.parseColor("#363A43")
        )
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = context?.let {
            ContextCompat.getDrawable(
                it,
                R.drawable.selected_option_border_bg
            )
        }
    }

    private fun defaultOptionsView() {

        val options = ArrayList<TextView>()
        options.add(0, tv_option_one)
        options.add(1, tv_option_two)
        options.add(2, tv_option_three)
        options.add(3, tv_option_four)

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = this!!.context?.let {
                ContextCompat.getDrawable(
                    it,
                    R.drawable.default_option_border_bg
                )
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.tv_option_one -> {

                selectedOptionView(tv_option_one, 1)
            }

            R.id.tv_option_two -> {

                selectedOptionView(tv_option_two, 2)
            }

            R.id.tv_option_three -> {

                selectedOptionView(tv_option_three, 3)
            }

            R.id.tv_option_four -> {

                selectedOptionView(tv_option_four, 4)
            }

            R.id.btn_return -> {
                if (mCurrentPosition != 1){
                    mCurrentPosition--

                    setQuestion()
                }
            }

            R.id.btn_submit -> {

                if(mCurrentPosition < mQuestionsList!!.size){
                    mCurrentPosition++

                    setQuestion()
                }else{
                    // TODO (STEP 5: Now remove the toast message and launch the result screen which we have created and also pass the user name and score details to it.)
                    // START
                    var score = 0

                    for (question in mQuestionsList!!){
                        if(question.chosenAnswer == question.correctAnswer){
                            score++;
                        }
                    }
                    (activity as MainActivity).setCscore(score)
                }
            }
        }
    }
}