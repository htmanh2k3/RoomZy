package com.app.roomzy.Models;

import com.google.gson.annotations.SerializedName;

public class BookingResponse {
    @SerializedName("message")
    private String message;

    @SerializedName("bookingId")
    private String bookingId;

    public BookingResponse(String message, String bookingId) {
        this.message = message;
        this.bookingId = bookingId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }
}
