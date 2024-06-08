package com.app.roomzy.Models;

        public class Voucher {
        private String TenVC;
        private String MoTa;
        private String Hinh;
        private String GiamGia;
        private String MaVC;
        private int ThoiHan;
        private double GiaToiDa;

        public Voucher(){

        }
        public Voucher(String tenVC, String moTa, String giamGia, String hinh) {
            this.TenVC = tenVC;
            this.MoTa = moTa;
            this.GiamGia = giamGia;
            this.Hinh = hinh;
        }
        public String getTenVC() {
            return TenVC;
        }

        public void setTenVC(String tenVC) {
            TenVC = tenVC;
        }

        public String getMoTa() {
            return MoTa;
        }

        public void setMoTa(String moTa) {
            MoTa = moTa;
        }

        public String getHinh() {
            return Hinh;
        }

        public void setHinh(String hinh) {
            Hinh = hinh;
        }

        public String getGiamGia() {
            return GiamGia;
        }

        public void setGiamGia(String giamGia) {
            GiamGia = giamGia;
        }

        public String getMaVC() {
            return MaVC;
        }

        public void setMaVC(String maVC) {
            MaVC = maVC;
        }

        public int getThoiHan() {
            return ThoiHan;
        }

        public void setThoiHan(int thoiHan) {
            ThoiHan = thoiHan;
        }

        public double getGiaToiDa() {
            return GiaToiDa;
        }

        public void setGiaToiDa(double giaToiDa) {
            GiaToiDa = giaToiDa;
        }

}


