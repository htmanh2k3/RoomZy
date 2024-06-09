package com.app.roomzy.Models;

import java.util.List;

public class Booking {
    private String bookingId;
    private String roomId;
    private String checkInDate;
    private String checkOutDate;
    private String voucherId;
    private VoucherDetails voucherDetails;
    private double totalPrice;
    private double finalPrice;
    private String status;
    private String bookingCode;
    private String checkInCode;
    private RoomInfo roomInfo;

    public Booking(String bookingId, String roomId, String checkInDate, String checkOutDate, String voucherId, VoucherDetails voucherDetails, double totalPrice, double finalPrice, String status, String bookingCode, String checkInCode, RoomInfo roomInfo) {
        this.bookingId = bookingId;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.voucherId = voucherId;
        this.voucherDetails = voucherDetails;
        this.totalPrice = totalPrice;
        this.finalPrice = finalPrice;
        this.status = status;
        this.bookingCode = bookingCode;
        this.checkInCode = checkInCode;
        this.roomInfo = roomInfo;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

    public VoucherDetails getVoucherDetails() {
        return voucherDetails;
    }

    public void setVoucherDetails(VoucherDetails voucherDetails) {
        this.voucherDetails = voucherDetails;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }

    public String getCheckInCode() {
        return checkInCode;
    }

    public void setCheckInCode(String checkInCode) {
        this.checkInCode = checkInCode;
    }

    public RoomInfo getRoomInfo() {
        return roomInfo;
    }

    public void setRoomInfo(RoomInfo roomInfo) {
        this.roomInfo = roomInfo;
    }

    // Getters and setters

    public static class VoucherDetails {
        private String discount;
        private double maxDiscountAmount;
        private String description;

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public double getMaxDiscountAmount() {
            return maxDiscountAmount;
        }

        public void setMaxDiscountAmount(double maxDiscountAmount) {
            this.maxDiscountAmount = maxDiscountAmount;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        // Getters and setters
    }

    public static class RoomInfo {
        private String address;
        private String image;
        private List<String> subImages;
        private String name;
        private String description;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public List<String> getSubImages() {
            return subImages;
        }

        public void setSubImages(List<String> subImages) {
            this.subImages = subImages;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        // Getters and setters
    }
}
