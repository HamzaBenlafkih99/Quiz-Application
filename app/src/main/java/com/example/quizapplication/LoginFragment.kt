package com.example.quizapplication


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_login, container, false)

        val btn_start = view.findViewById<Button>(R.id.btn_start);
        val et_name = view.findViewById<EditText>(R.id.et_name);

        btn_start.setOnClickListener {
            if (et_name.text.toString().isEmpty()) {

                Toast.makeText(activity, "Please enter your name", Toast.LENGTH_SHORT).show()
            } else {

                (activity as MainActivity?)!!.goToHome(et_name.text.toString())

            }
        }

        return view
    }





}