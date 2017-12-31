package com.semester.seecs.autoresponse;

import java.io.Serializable;

public class Model implements Serializable{
   private Time startTime;
   private Time endTime;
   private String message;
   private boolean active;

    public Model(Time startTime, Time endTime, String message, boolean active) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.message = message;
        this.active = active;
    }

    public Model() {
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

    @Override
    public String toString() {
        return "Model{" + "startTime=" + startTime + ", endTime=" + endTime + ", message='" +
                message + '\'' + ", active=" + active + '}';
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

        public String getReadableTime(){
            return hourOfDay+":"+minute;
        }

        @Override
        public String toString() {
            return "Time{" + "hourOfDay=" + hourOfDay + ", minute=" + minute + '}';
        }
    }
}
