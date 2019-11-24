package com.gcs.testuielements.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gcs.testuielements.data.XYZRepository;
import com.gcs.testuielements.models.XYZJson;

import java.util.List;

public class JsonViewModel extends AndroidViewModel {

    private XYZRepository repository;
    private LiveData<List<XYZJson>> allJsons;

    public JsonViewModel(@NonNull Application application){
        super(application);
        repository = new XYZRepository(application);
        allJsons = repository.getAllJsonLD();
    }

    public LiveData<List<XYZJson>> getAllJson(){
        return repository.getJsonListMuatable();
    }
}
