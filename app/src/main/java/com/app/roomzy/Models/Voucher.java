package com.app.roomzy.Models;

public class Voucher {
    private String tenVoucher;
    private String moTa;
    private String imageURL;
    private String giamGia;
    //==================
    private String maVoucher;
    private int thoiHan;
    private double giaToiDa;

    public Voucher() {
    }

    public Voucher(String tenVoucher, String moTa, String imageURL, String giamGia) {
        this.tenVoucher = tenVoucher;
        this.moTa = moTa;
        this.imageURL = imageURL;
        this.giamGia = giamGia;
    }

    public String getTenVoucher() {
        return tenVoucher;
    }

    public void setTenVoucher(String tenVoucher) {
        this.tenVoucher = tenVoucher;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(String giamGia) {
        this.giamGia = giamGia;
    }

    public String getMaVoucher() {
        return maVoucher;
    }

    public void setMaVoucher(String maVoucher) {
        this.maVoucher = maVoucher;
    }

    public int getThoiHan() {
        return thoiHan;
    }

    public void setThoiHan(int thoiHan) {
        this.thoiHan = thoiHan;
    }

    public double getGiaToiDa() {
        return giaToiDa;
    }

    public void setGiaToiDa(double giaToiDa) {
        this.giaToiDa = giaToiDa;
    }
}
