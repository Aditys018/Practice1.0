package com.aditys.themusicalchemy

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity :  AppCompatActivity(), ItemClickListener {

    private lateinit var dataList: List<Data>
    lateinit var myrecyclerview:RecyclerView
    lateinit var myAdapter: MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myrecyclerview= findViewById(R.id.recyclerView)

        val recyclerView:RecyclerView = findViewById(R.id.recyclerView)
        val adapter = MyAdapter(this, dataList ,this)
        recyclerView.adapter=adapter

        val retrofitBuilder= Retrofit.Builder()
            .baseUrl("https://deezerdevs-deezer.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retrofitData= retrofitBuilder.getData("eminem")

        retrofitData.enqueue(object : Callback<MyData?> {

            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                val dataList=response.body()?.data!!
//                val textView= findViewById<TextView>(R.id.helloText)
//                textView.text= dataList.toString()

                myAdapter= MyAdapter(this@MainActivity, dataList)
                myrecyclerview.adapter=myAdapter
                myrecyclerview.layoutManager=LinearLayoutManager(this@MainActivity)
                Log.d("TAG: onResponse", "onResponse: " +response.body())
            }

            override fun onFailure(call: Call<MyData?>, t: Throwable) {
               Log.d("TAG: onFailure", "onFailure: " + t.message)
            }
        })
    }

    override fun onItemClick(position: Int) {
        // Handle item click, e.g., navigate to another activity
        Toast.makeText(this, "Item clicked at position: $dataList", Toast.LENGTH_SHORT).show()
    }
}