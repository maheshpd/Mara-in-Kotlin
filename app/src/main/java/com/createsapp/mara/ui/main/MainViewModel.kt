package com.createsapp.mara.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.createsapp.mara.callback.MainLoadCallback
import com.createsapp.mara.model.MainModel
import com.createsapp.mara.utils.Common
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainViewModel : ViewModel(), MainLoadCallback {

    private var mainlist: MutableLiveData<List<MainModel>>? = null
    private var mainLoadCallbackListener: MainLoadCallback
    private lateinit var messageError: MutableLiveData<String>

    val populatList: LiveData<List<MainModel>>
        get() {
            if (mainlist == null) {
                mainlist = MutableLiveData()
                messageError = MutableLiveData()
                loadList()
            }
            return mainlist!!
        }

    init {
        mainLoadCallbackListener = this
    }

    private fun loadList() {
        var tempList = ArrayList<MainModel>()
        val mainRef = FirebaseDatabase.getInstance().getReference(Common.MainData)
        mainRef.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
                mainLoadCallbackListener.onMainLoadFailed(p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (itemSnapShort in p0!!.children) {
                    val model = itemSnapShort.getValue(MainModel::class.java)
                    tempList.add(model!!)
                }

                mainLoadCallbackListener.onmainLoadSuccess(tempList)
            }

        })
    }
    override fun onmainLoadSuccess(lainLoadList: List<MainModel>) {
        mainlist!!.value = lainLoadList
    }

    override fun onMainLoadFailed(message: String) {
        messageError.value = message
    }
}