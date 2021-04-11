package com.example.quizapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class HomeFragment : Fragment(), RecyclerViewAdapter.Clicklistener {
    private lateinit var adapter: RecyclerViewAdapter
    val listData: ArrayList<Chapitre> = ArrayList()
    val db by lazy { DatabaseHandler(context!!) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_home, container, false)
        initRecyclerView(view)
        return view
    }

    private fun initRecyclerView(view: View){
        var recyclerview = view.findViewById<RecyclerView>(R.id.chapitres_view)
        recyclerview.layoutManager = LinearLayoutManager(activity)
        adapter = RecyclerViewAdapter(getItemsList(), this)
        recyclerview.adapter = adapter
    }

    override fun onItemClick(dataModel: Chapitre) {
        val transaction = activity?.supportFragmentManager!!.beginTransaction()
        var fragment: Fragment = QuizQuestionsFragment.newInstance(dataModel.id)
        transaction.replace(R.id.fragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    fun getItemsList(): ArrayList<Chapitre> {


        if(db.viewChapitre().size < 1){
            listData
        }
        return db.viewChapitre()
    }

}