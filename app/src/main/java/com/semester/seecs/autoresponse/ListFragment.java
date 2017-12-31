package com.semester.seecs.autoresponse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private static final int REQUEST_CODE = 22311;

    private RecyclerView recyclerView;
    private FloatingActionButton actionButton;

    private List<Model> modelList = new ArrayList<>();

    private Adapter adapter = new Adapter();

    private DataHolder dataHolder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataHolder = DataHolder.getInstance(getContext());
        modelList = dataHolder.getModelList();
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
                FragmentManager fm = getFragmentManager();

                FragmentTransaction ft = fm.beginTransaction();

                CreateResponseFragment fragment = new CreateResponseFragment();

                fragment.setTargetFragment(ListFragment.this, REQUEST_CODE);

                ft.add(android.R.id.content, fragment).commit();

                ft.addToBackStack(null);

            }
        });
        recyclerView.setAdapter(adapter);
        adapter.setData(modelList);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){

          Model model = (Model) data.getSerializableExtra(CommonKeys.KEY_EXTRA_MODEL);
          modelList.add(model);

          adapter.setData(modelList);

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dataHolder.saveModelList(modelList);
    }
}
