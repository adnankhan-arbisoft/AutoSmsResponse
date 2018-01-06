package com.semester.seecs.autoresponse;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog
        .OnTimeSetListener {

    public static TimePickerFragment newInstance(Model.Time time) {
        TimePickerFragment fragment = new TimePickerFragment();
        if (time != null) {
            Bundle args = new Bundle();
            args.putSerializable(CommonKeys.KEY_EXTRA_TIME, time);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Model.Time time;

        Bundle args = getArguments();
        if(args != null){
            time = (Model.Time) args.getSerializable(CommonKeys.KEY_EXTRA_TIME);
        } else {
            // Use the current time as the default values for the picker
            time = new Model.Time();
            final Calendar c = Calendar.getInstance();
            time.hourOfDay = c.get(Calendar.HOUR_OF_DAY);
            time.minute = c.get(Calendar.MINUTE);

        }
        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, time.hourOfDay, time.minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        Model.Time time = new Model.Time(hourOfDay, minute);

        Intent intent = new Intent();
        intent.putExtra(CommonKeys.KEY_EXTRA_TIME, time);

        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
    }
}