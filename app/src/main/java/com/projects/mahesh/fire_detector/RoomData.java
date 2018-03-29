package com.projects.mahesh.fire_detector;

public class RoomData {
    String room_no;
    String temperature;

    public RoomData(String room_no, String temperature) {
        this.room_no = room_no;
        this.temperature = temperature;
    }

    public String getRoom_no() {
        return room_no;
    }

    public String getTemperature() {
        return temperature;
    }
}
