package com.kelompok2.rifqi002satriawan001.aplikasitokomainan;

public class user {
    private String nama;
    private String namapendek;
    private String email;
    private String noTelp;
    private String alamat;
    private String modeakses;
    private String created_at;
    private String update_at;

    public user(){

    }
    public user(String nama,String namapendek, String email, String noTelp, String alamat, String modeakses, String created_at,String update_at) {
        this.nama = nama;
        this.namapendek = namapendek;
        this.email = email;
        this.noTelp = noTelp;
        this.alamat = alamat;
        this.modeakses = modeakses;
        this.created_at = created_at;
        this.update_at = update_at;
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

    public String getNamapendek() {
        return namapendek;
    }

    public void setNamapendek(String namapendek) {
        this.namapendek = namapendek;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getModeakses() {
        return modeakses;
    }

    public void setModeakses(String modeakses) {
        this.modeakses = modeakses;
    }
}
