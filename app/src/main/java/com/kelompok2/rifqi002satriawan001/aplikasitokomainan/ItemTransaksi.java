package com.kelompok2.rifqi002satriawan001.aplikasitokomainan;

public class ItemTransaksi {

    private String key_tr;
    private String user;
    private String key_mainan;
    private int qty;
    private long TotalHargaJual;
    private String WaktuTransaksi;

    public ItemTransaksi(String user, String key_mainan, int qty, long totalHargaJual, String waktuTransaksi) {
        this.user = user;
        this.key_mainan = key_mainan;
        this.qty = qty;
        TotalHargaJual = totalHargaJual;
        WaktuTransaksi = waktuTransaksi;
    }

    public ItemTransaksi(){

    }

    public String getKey_tr() {
        return key_tr;
    }

    public void setKey_tr(String key_tr) {
        this.key_tr = key_tr;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getKey_mainan() {
        return key_mainan;
    }

    public void setKey_mainan(String key_mainan) {
        this.key_mainan = key_mainan;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public long getTotalHargaJual() {
        return TotalHargaJual;
    }

    public void setTotalHargaJual(long totalHargaJual) {
        TotalHargaJual = totalHargaJual;
    }

    public String getWaktuTransaksi() {
        return WaktuTransaksi;
    }

    public void setWaktuTransaksi(String waktuTransaksi) {
        WaktuTransaksi = waktuTransaksi;
    }
}
