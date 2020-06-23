package com.akagami.absensipramuka.adapter;

public class AbsensiAdapterItems {

    private String bulan;
    private String minggu_1;
    private String minggu_2;
    private String minggu_3;
    private String minggu_4;
    private String minggu_5;

    public AbsensiAdapterItems(String bulan, String minggu_1, String minggu_2, String minggu_3, String minggu_4, String minggu_5) {
        this.bulan = bulan;
        this.minggu_1 = minggu_1;
        this.minggu_2 = minggu_2;
        this.minggu_3 = minggu_3;
        this.minggu_4 = minggu_4;
        this.minggu_5 = minggu_5;
    }

    public String getBulan() {
        return bulan;
    }

    public String getMinggu_1() {
        return minggu_1;
    }

    public String getMinggu_2() {
        return minggu_2;
    }

    public String getMinggu_3() {
        return minggu_3;
    }

    public String getMinggu_4() {
        return minggu_4;
    }

    public String getMinggu_5() {
        return minggu_5;
    }
}
