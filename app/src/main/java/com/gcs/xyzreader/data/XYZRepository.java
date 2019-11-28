package com.gcs.xyzreader.data;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gcs.xyzreader.models.XYZJson;
import com.gcs.xyzreader.service.RestApiService;
import com.gcs.xyzreader.service.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class XYZRepository {

    private XYZDao dao;
    private LiveData<List<XYZJson>> allJson;

    private MutableLiveData<List<XYZJson>> jsonListMuatable = new MutableLiveData<>();
    private Application application;

    public XYZRepository(Application application){
        XYZDatabase database = XYZDatabase.getInstance(application);
        dao = database.xyzDao();
        allJson = dao.getAll();
        this.application = application;
    }

    public LiveData<List<XYZJson>> getJsonListMuatable(){
        if (isNetworkConnected()){
            RestApiService apiService = RetrofitInstance.getApiService();
            Call<List<XYZJson>> call = apiService.getJson();
            call.enqueue(new Callback<List<XYZJson>>() {
                @Override
                public void onResponse(Call<List<XYZJson>> call, Response<List<XYZJson>> response) {
                    if (!response.isSuccessful()){
                        return;
                    }
                    List<XYZJson> listJson = response.body();
                    if (listJson != null){
                        jsonListMuatable.setValue(listJson);
                        allJson = jsonListMuatable;
                        deleteAllJson();
                        insertAll(listJson);
                    }
                }

                @Override
                public void onFailure(Call<List<XYZJson>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }

        return allJson;
    }


    public void insert(XYZJson json){
        new InsertTask(dao).execute(json);
    }

    public void insertAll(List<XYZJson> list){
        new InsertAllTask(dao, list).execute();
    }

    public void update(XYZJson json){
        new UpdateTask(dao).execute(json);
    }

    public void delete(XYZJson json){
        new DeleteTask(dao).execute(json);
    }

    public void deleteAllJson(){
        new DeleteAllTask(dao).execute();
    }

    public LiveData<List<XYZJson>> getAllJsonLD(){
        return allJson;
    }

    public List<XYZJson> getByID(int id){
        List<XYZJson> list = new ArrayList<>();
        try {
            list = new GetByIdTask(dao).execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (list != null){
            return list;
        }
        else {
            return null;
        }
    }

    public MutableLiveData<List<XYZJson>> getAllJsonMLD(){
        return jsonListMuatable;
    }



    private static class InsertTask extends AsyncTask<XYZJson, Void, Void>{

        private XYZDao dao;

        private InsertTask(XYZDao dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(XYZJson... xyzJsons) {
            dao.insert(xyzJsons[0]);
            return null;
        }
    }

    private static class InsertAllTask extends AsyncTask<Void, Void, Void>{

        private XYZDao dao;
        private List<XYZJson> list;

        private InsertAllTask(XYZDao dao, List<XYZJson> list){
            this.dao = dao;
            this.list = list;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i=0; i<list.size(); i++){
                dao.insert(list.get(i));
            }
            return null;
        }
    }


    private static class UpdateTask extends AsyncTask<XYZJson, Void, Void>{

        private XYZDao dao;

        private UpdateTask(XYZDao dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(XYZJson... xyzJsons) {
            dao.update(xyzJsons[0]);
            return null;
        }
    }


    private static class DeleteTask extends AsyncTask<XYZJson, Void, Void>{

        private XYZDao dao;

        private DeleteTask(XYZDao dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(XYZJson... xyzJsons) {
            dao.delete(xyzJsons[0]);
            return null;
        }
    }


    private static class DeleteAllTask extends AsyncTask<Void, Void, Void>{

        private XYZDao dao;

        private DeleteAllTask(XYZDao dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }


    private static class GetByIdTask extends AsyncTask<Integer, Void, List<XYZJson>>{

        private XYZDao dao;

        public GetByIdTask(XYZDao dao){
            this.dao = dao;
        }



        @Override
        protected List<XYZJson> doInBackground(Integer... integers) {
            return dao.getById(integers[0]);
        }
    }


    public boolean isNetworkConnected(){
        ConnectivityManager cm = (ConnectivityManager)application.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT < 23){
            NetworkInfo ni = cm.getActiveNetworkInfo();

            if (ni != null){
                return (ni.isConnected() && (ni.getType() == ConnectivityManager.TYPE_WIFI || ni.getType() == ConnectivityManager.TYPE_MOBILE));
            }
            else {
                Network n = cm.getActiveNetwork();
                if (n != null) {
                    NetworkCapabilities nc = cm.getNetworkCapabilities(n);
                    return (nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI));
                }
            }
        }

        else {
            Network n = cm.getActiveNetwork();

            if (n != null) {
                NetworkCapabilities nc = cm.getNetworkCapabilities(n);

                return (nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI));
            }
        }

        return false;
    }

}
