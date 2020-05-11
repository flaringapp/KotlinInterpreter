package com.flaringapp.mapzlab1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.flaringapp.mapzlab1.interpreter.IntContext
import com.flaringapp.mapzlab1.interpreter.optimizer.StatementsOptimizer.optimize
import com.flaringapp.mapzlab1.interpreter.parser.Code
import com.flaringapp.mapzlab1.interpreter.parser.CodeParser
import kotlinx.android.synthetic.main.activity_run.*

class RunActivity : AppCompatActivity(R.layout.activity_run) {

    companion object {
        private const val KEY_CODE = "key_code"

        fun open(context: Context, code: Code) {
            val intent = Intent(context, RunActivity::class.java)
            intent.putExtra(KEY_CODE, code.toTypedArray())
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        init()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun init() {
        val input = intent.getStringArrayExtra(KEY_CODE)!!.toList()

        val code = CodeParser.parse(input)
            .optimize()

        val context = IntContext {
            codeViewer.setText("${codeViewer.getText()}\n$it")
        }

        code.execute(context)
    }

}