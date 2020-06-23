package com.akagami.absensipramuka.ui.absensi;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AbsensiViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AbsensiViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}