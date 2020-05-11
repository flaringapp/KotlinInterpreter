package com.flaringapp.mapzlab1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.flaringapp.mapzlab1.interpreter.optimizer.StatementsOptimizer.optimize
import com.flaringapp.mapzlab1.interpreter.parser.Code
import com.flaringapp.mapzlab1.interpreter.parser.CodeParser
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_tree.*
import java.lang.Exception

class TreeActivity : AppCompatActivity(R.layout.activity_tree) {

    companion object {
        private const val KEY_INPUT = "key_input"

        fun open(context: Context, input: List<String>) {
            val intent = Intent(context, TreeActivity::class.java)
            intent.putExtra(KEY_INPUT, input.toTypedArray())
            context.startActivity(intent)
        }
    }

    private lateinit var input: Code

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        init()
    }

    private fun init() {
        input = intent.getStringArrayExtra(KEY_INPUT)!!.toList()

        val code = try {
             CodeParser.parse(input)
        } catch (e: Exception) {
            e.printStackTrace()
            Snackbar.make(root, e.message.toString(), Snackbar.LENGTH_INDEFINITE).show()
            return
        }

        val optimizedCode = CodeParser.parse(input).optimize()

        treeViewUnoptimized.setData(code)
        treeViewOptimized.setData(optimizedCode)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.next -> onNextPressed()
            android.R.id.home -> finish()
            else -> return super.onOptionsItemSelected(item)
        }

        return true
    }

    private fun onNextPressed() {
        RunActivity.open(this, input)
    }
}