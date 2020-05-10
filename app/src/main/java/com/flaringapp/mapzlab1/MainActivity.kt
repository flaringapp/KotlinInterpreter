package com.flaringapp.mapzlab1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.flaringapp.mapzlab1.interpreter.AndroidFileAdapter
import com.flaringapp.mapzlab1.interpreter.Client
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        val adapter = AndroidFileAdapter()

        val code = adapter.readInputFile(this)

        val tree = Client.run(code)

        treeView.setData(tree)
    }
}
