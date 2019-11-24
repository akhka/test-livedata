package com.gcs.testuielements;

import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.gcs.testuielements.adapters.JsonItemAdapter;
import com.gcs.testuielements.models.XYZJson;
import com.gcs.testuielements.viewmodel.JsonViewModel;

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
    }

    public void prepareRecyclerView(List<XYZJson> list){
        adapter = new JsonItemAdapter(this, list);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation);
        recyclerView.setLayoutAnimation(animation);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


}
