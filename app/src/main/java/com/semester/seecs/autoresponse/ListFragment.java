package com.semester.seecs.autoresponse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Set;
import java.util.TreeSet;

public class ListFragment extends Fragment implements Adapter.ItemClickListener {

    private static final int REQUEST_CODE = 22311;

    private RecyclerView recyclerView;
    private FloatingActionButton actionButton;

    private Set<Model> modelSet = new TreeSet<Model>();
    private Adapter adapter;
    private DataHolder dataHolder;

    private Handler handler = new Handler();
    private Runnable postRunnable= new Runnable() {
        @Override
        public void run() {
            dataHolder.saveModels(modelSet);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataHolder = DataHolder.getInstance(getContext());
        modelSet.addAll(dataHolder.getModels());
        adapter = new Adapter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        actionButton = view.findViewById(R.id.floating_btn);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCreateResponseFragment(null);
            }
        });
        recyclerView.setAdapter(adapter);
        adapter.setData(modelSet);
    }

    public void addCreateResponseFragment(Model model){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment fragment = CreateResponseFragment.newInstance(model);
        fragment.setTargetFragment(ListFragment.this, REQUEST_CODE);
        ft.add(android.R.id.content, fragment).commit();
        ft.addToBackStack(null);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){

          Model model = (Model) data.getSerializableExtra(CommonKeys.KEY_EXTRA_MODEL);
          modelSet.add(model);
          adapter.setData(modelSet);
          handler.postDelayed(postRunnable, 500);

        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        dataHolder.saveModels(modelSet);
    }

    @Override
    public void onItemClick(Model model) {
        addCreateResponseFragment(model);
    }

    @Override
    public void onItemRemove(Model model) {
        modelSet.remove(model);
        adapter.setData(modelSet);
        handler.postDelayed(postRunnable, 500);
    }

    @Override
    public void onStatusChange(Model model) {
        handler.postDelayed(postRunnable, 500);
    }

}
