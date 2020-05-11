package com.flaringapp.mapzlab1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    private fun init() {
        val text = AndroidFileAdapter.readInputFile(this)
        codeEditor.setText(text.joinToString("\n"))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.next -> onNextPressed()
            else -> return super.onOptionsItemSelected(item)
        }

        return true
    }

    private fun onNextPressed() {
        val code = codeEditor.getText().split('\n')
        TreeActivity.open(this, code)
    }
}
