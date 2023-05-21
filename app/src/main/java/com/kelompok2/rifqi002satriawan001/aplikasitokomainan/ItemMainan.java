package com.kelompok2.rifqi002satriawan001.aplikasitokomainan;

public class ItemMainan {
    private String nama_mainan;
    private String merk;
    private long harga;
    private String satuan;
    private int stok_mainan;
    private String comp_nama_merk;
    private String key;
    private int qty;
    private String created_at;
    private String update_at;

    public ItemMainan(){

    }

    public ItemMainan(String nama_mainan, String merk, long harga, String satuan, int stok_mainan, String comp_nama_merk, int qty, String created_at, String update_at) {
        this.nama_mainan = nama_mainan;
        this.merk = merk;
        this.harga = harga;
        this.satuan = satuan;
        this.stok_mainan = stok_mainan;
        this.comp_nama_merk = comp_nama_merk;
        this.qty = qty;
        this.created_at = created_at;
        this.update_at = update_at;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }

    public String getNama_mainan() {
        return nama_mainan;
    }

    public void setNama_mainan(String nama_mainan) {
        this.nama_mainan = nama_mainan;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public long getHarga() {
        return harga;
    }

    public void setHarga(long harga) {
        this.harga = harga;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public int getStok_mainan() {
        return stok_mainan;
    }

    public void setStok_mainan(int stok_mainan) {
        this.stok_mainan = stok_mainan;
    }

    public String getComp_nama_merk() {
        return comp_nama_merk;
    }

    public void setComp_nama_merk(String comp_nama_merk) {
        this.comp_nama_merk = comp_nama_merk;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
