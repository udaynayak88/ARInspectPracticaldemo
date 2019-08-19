package net.gahfy.mvvmposts.ui.post

import android.arch.lifecycle.MutableLiveData
import net.gahfy.mvvmposts.base.BaseViewModel
import net.gahfy.mvvmposts.model.Data


class PostViewModel:BaseViewModel() {
    private val postTitle = MutableLiveData<String>()
    private val postBody = MutableLiveData<String>()
    private val ImageUrl = MutableLiveData<String>()

    fun bind(post: Data.Row){
        postTitle.value = post.title
        postBody.value = post.description
        ImageUrl.value = post.imageHref
    }

    fun getPostTitle():MutableLiveData<String>{
        return postTitle
    }

    fun getPostBody():MutableLiveData<String>{
        return postBody
    }

    fun getImageUrl():MutableLiveData<String> {
        return ImageUrl
    }

}