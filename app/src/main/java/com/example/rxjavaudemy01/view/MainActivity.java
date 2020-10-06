package com.example.rxjavaudemy01.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.rxjavaudemy01.R;
import com.example.rxjavaudemy01.adapter.AdapterMain;
import com.example.rxjavaudemy01.model.infoMain;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterMain adapterNav;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        
    }
    private void init() {
        recyclerView = findViewById(R.id.recyclerView);
        adapterNav = new AdapterMain(this, getdata());
        recyclerView.setAdapter(adapterNav);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<infoMain> getdata() {
        List<infoMain> data = new ArrayList<>();
        String title[] = {"Part01: Starter(just, Disposable,Observable,Observer)",
        "Part02: DisposableObserver","Part03:CompositeDisposable","Part04:(just,String[])"};


        for (int i = 0; i < title.length ; i++) {

            infoMain cur = new infoMain();
            cur.title = title[i];
            data.add(cur);

        }
        return data;

    }
}