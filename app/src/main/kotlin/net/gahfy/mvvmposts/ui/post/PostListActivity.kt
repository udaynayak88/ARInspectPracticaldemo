package net.gahfy.mvvmposts.ui.post

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import net.gahfy.mvvmposts.R

import net.gahfy.mvvmposts.databinding.ActivityPostListBinding
import net.gahfy.mvvmposts.injection.ViewModelFactory
import android.view.Menu

import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_post_list.*


class PostListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostListBinding
    private lateinit var viewModel: PostListViewModel
    private var errorSnackbar: Snackbar? = null


    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_list)
        setSupportActionBar(toolbar)

        binding.postList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        viewModel = ViewModelProviders.of(this, ViewModelFactory(this)).get(PostListViewModel::class.java)
        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })
        viewModel.title.observe(this, Observer { title ->
            setTitleOnTop(title)
        })

        binding.viewModel = viewModel
    }

    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }

    private fun setTitleOnTop(title: String?) {

        supportActionBar?.title = title

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_add -> {
//                Toast.makeText(this, "refreshed", Toast.LENGTH_SHORT).show()
                true
            }
            else ->{
                super.onOptionsItemSelected(item)
            }
        }
    }

}