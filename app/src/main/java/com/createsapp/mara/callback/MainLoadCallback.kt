package com.createsapp.mara.callback

import com.createsapp.mara.model.MainModel

interface MainLoadCallback {
    fun onmainLoadSuccess(lainLoadList: List<MainModel>)
    fun onMainLoadFailed(message:String)
}