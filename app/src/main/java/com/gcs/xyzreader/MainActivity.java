package com.gcs.xyzreader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.gcs.xyzreader.adapters.JsonItemAdapter;
import com.gcs.xyzreader.models.XYZJson;
import com.gcs.xyzreader.viewmodel.JsonViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private JsonViewModel viewModel;
    private JsonItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(this).get(JsonViewModel.class);
        getJsonData();
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getJsonData();
            }
        });
    }

    public void getJsonData(){
        swipeRefresh.setRefreshing(true);
        viewModel.getAllJson().observe(this, new Observer<List<XYZJson>>() {
            @Override
            public void onChanged(List<XYZJson> xyzJsons) {
                swipeRefresh.setRefreshing(false);
                prepareRecyclerView(xyzJsons);
            }
        });
        if (!isNetworkConnected()){
            Snackbar.make(getWindow().getDecorView().getRootView(), "Check Internet Connection!", Snackbar.LENGTH_SHORT).show();
        }
    }

    public void prepareRecyclerView(List<XYZJson> list){
        adapter = new JsonItemAdapter(this, list);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
       /* LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation);
        recyclerView.setLayoutAnimation(animation);*/
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    public boolean isNetworkConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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
