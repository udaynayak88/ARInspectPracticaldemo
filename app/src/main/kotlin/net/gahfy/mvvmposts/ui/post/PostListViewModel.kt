package net.gahfy.mvvmposts.ui.post

import android.arch.lifecycle.MutableLiveData
import android.view.View
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import net.gahfy.mvvmposts.R
import net.gahfy.mvvmposts.base.BaseViewModel
import net.gahfy.mvvmposts.model.Data
import net.gahfy.mvvmposts.model.Post
import net.gahfy.mvvmposts.model.PostDao
import net.gahfy.mvvmposts.network.PostApi
import javax.inject.Inject

class PostListViewModel(private val postDao: PostDao):BaseViewModel(){
    @Inject
    lateinit var postApi: PostApi
    val postListAdapter: PostListAdapter = PostListAdapter()

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage:MutableLiveData<Int> = MutableLiveData()
    val title:MutableLiveData<String> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadPosts() }

    private lateinit var subscription: Disposable

    init{
        loadPosts()
    }


    fun refreshButtonClicked() {
//        text.set("Button clicked!")
//        Log.d("signInClick","signInClick");
        loadPosts()
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    private fun loadPosts(){
        subscription = postApi.getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrievePostListStart() }
                .doOnTerminate { onRetrievePostListFinish() }
                .subscribe(
                        { result -> onRetrievePostListSuccess(result) },
                        { onRetrievePostListError() }
                )
    }

    private fun onRetrievePostListStart(){
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrievePostListFinish(){
        loadingVisibility.value = View.GONE
    }

    private fun onRetrievePostListSuccess(postList:Data){

        postListAdapter.updatePostList(postList.rows)
        title.value = postList.title
    }

    private fun onRetrievePostListError(){
        errorMessage.value = R.string.post_error
    }
}