package com.semester.seecs.autoresponse;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Model implements Serializable, Comparable<Model>{
   private String uniqueID;
   private Time startTime;
   private Time endTime;
   private String message;
   private boolean active;

    public Model(Time startTime, Time endTime, String message, boolean active) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.message = message;
        this.active = active;
        uniqueID = UUID.randomUUID().toString();
    }

    public Model() {
        uniqueID = UUID.randomUUID().toString();
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Model model = (Model) o;

        return uniqueID.equals(model.uniqueID);
    }

    @Override
    public int hashCode() {
        return uniqueID.hashCode();
    }



    @Override
    public String toString() {
        return "Model{" + "startTime=" + startTime + ", endTime=" + endTime + ", message='" +
                message + '\'' + ", active=" + active + '}';
    }

    @Override
    public int compareTo(@NonNull Model model) {
          return uniqueID.compareTo(model.getUniqueID());
    }

    public static class Time implements Serializable {
        public int hourOfDay;
        public int minute;

        public Time(int hourOfDay, int minute) {
            this.hourOfDay = hourOfDay;
            this.minute = minute;
        }

        public Time() {
        }

        public void setHourOfDay(int hourOfDay) {
            this.hourOfDay = hourOfDay;
        }

        public void setMinute(int minute) {
            this.minute = minute;
        }

        public String getReadableTime() {

            String _24HourTime = hourOfDay + ":" + minute;
            try {
                SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
                SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
                Date _24HourDt = _24HourSDF.parse(_24HourTime);
                return _12HourSDF.format(_24HourDt);

            } catch (ParseException e) {
                e.printStackTrace();
                return _24HourTime;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            Time time = (Time) o;

            if (hourOfDay != time.hourOfDay)
                return false;
            return minute == time.minute;
        }

        @Override
        public int hashCode() {
            int result = hourOfDay;
            result = 31 * result + minute;
            return result;
        }

        @Override
        public String toString() {
            return "Time{" + "hourOfDay=" + hourOfDay + ", minute=" + minute + '}';
        }
    }
}
