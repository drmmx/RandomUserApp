package com.drmmx.devmax.randomuserapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.drmmx.devmax.randomuserapp.R;
import com.drmmx.devmax.randomuserapp.adapter.UserAdapter;
import com.drmmx.devmax.randomuserapp.model.RandomUser;
import com.drmmx.devmax.randomuserapp.retrofit.RandomUserAPI;
import com.drmmx.devmax.randomuserapp.retrofit.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "_MainActivity";

    private RandomUserAPI userAPI;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private UserAdapter adapter;

    private boolean isGridLayoutEnabled;

    private int pageNumber = 1;
    private int itemCount = 20;

    //RxJava
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        //Retrofit init
        Retrofit retrofit = RetrofitClient.getInstance();
        userAPI = retrofit.create(RandomUserAPI.class);

        //RecyclerView init
        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        //Load data
        fetchData();
    }

    private void fetchData() {
        compositeDisposable.add(userAPI.getUsers("axon", "gender,name,dob,phone,email,picture", pageNumber, itemCount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RandomUser>() {
                    @Override
                    public void accept(RandomUser users) {
                        adapter = new UserAdapter(MainActivity.this, users.getResults());
                        recyclerView.setAdapter(adapter);
                        progressBar.setVisibility(View.GONE);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.d(TAG, "error: " + throwable.getMessage());
                    }
                }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.changeLayoutMenu:
                if (!isGridLayoutEnabled) {
                    recyclerView.setLayoutManager(gridLayoutManager);
                    isGridLayoutEnabled = true;
                } else {
                    recyclerView.setLayoutManager(linearLayoutManager);
                    isGridLayoutEnabled = false;
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        pageNumber = 1;
        super.onStop();
    }

    /*@Override
    protected void onResume() {
        super.onResume();
        if (!isGridLayoutEnabled) {
            recyclerView.setLayoutManager(linearLayoutManager);
        } else {
            recyclerView.setLayoutManager(gridLayoutManager);
        }
    }*/
}
