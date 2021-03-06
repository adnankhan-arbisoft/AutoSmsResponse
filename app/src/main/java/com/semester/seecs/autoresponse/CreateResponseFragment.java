package com.semester.seecs.autoresponse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class CreateResponseFragment extends Fragment {

    public static final String TAG = CreateResponseFragment.class.getSimpleName();

    public static final int START_TIME_REQUEST_CODE = 11232;
    public static final int END_TIME_REQUEST_CODE = 34341;

    private TextView startTimeView, endTimeView;
    private EditText message;
    private Switch status;
    private Button done;

    private Model model;

    public static Fragment newInstance(Model model) {
        Fragment fragment = new CreateResponseFragment();
        if (model != null) {
            Bundle args = new Bundle();
            args.putSerializable(CommonKeys.KEY_EXTRA_MODEL, model);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            model = (Model) args.getSerializable(CommonKeys.KEY_EXTRA_MODEL);
        } else {
            model = new Model();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_response, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        startTimeView = view.findViewById(R.id.start_time);
        endTimeView = view.findViewById(R.id.end_time);
        message = view.findViewById(R.id.message);
        status = view.findViewById(R.id.status);
        done = view.findViewById(R.id.done);

        startTimeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(START_TIME_REQUEST_CODE, model.getStartTime());
            }
        });

        endTimeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(END_TIME_REQUEST_CODE, model.getEndTime());
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageText = message.getText().toString();
                boolean checked = status.isChecked();
                if (model.getStartTime() == null || model.getEndTime() == null || messageText.isEmpty()) {
                    Snackbar.make(getView(), "Fill in all the fields", Snackbar.LENGTH_LONG).show();
                }else if( model.getStartTime().equals(model.getEndTime())){
                    Snackbar.make(getView(), "You can't select same time!", Snackbar.LENGTH_LONG)
                            .show();

                }
                else {
                    model.setMessage(messageText);
                    model.setActive(checked);
                    Intent intent = new Intent();
                    intent.putExtra(CommonKeys.KEY_EXTRA_MODEL, model);
                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity
                            .RESULT_OK, intent);
                    getFragmentManager().popBackStack();
                }
            }
        });

        Bundle args = getArguments();
        if(args != null){
            initValues(args);
        }
    }

    private void initValues(Bundle args){
        startTimeView.setText(model.getStartTime().getReadableTime());
        endTimeView.setText(model.getEndTime().getReadableTime());
        message.setText(model.getMessage());
        status.setChecked(model.isActive());
    }

    public void showTimePickerDialog(int requestCode, Model.Time time) {
        DialogFragment newFragment = TimePickerFragment.newInstance(time);
        newFragment.setTargetFragment(this, requestCode);
        newFragment.show(getFragmentManager(), "timePicker");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            switch (requestCode) {

                case START_TIME_REQUEST_CODE:
                    Model.Time startTime = (Model.Time) data.getSerializableExtra(
                            CommonKeys.KEY_EXTRA_TIME);
                    startTimeView.setText(startTime.getReadableTime());
                    model.setStartTime(startTime);
                    break;

                case END_TIME_REQUEST_CODE:
                    Model.Time endTime = (Model.Time) data.getSerializableExtra(
                            CommonKeys.KEY_EXTRA_TIME);
                    endTimeView.setText(endTime.getReadableTime());
                    model.setEndTime(endTime);
                    break;
            }
        }
    }
}
