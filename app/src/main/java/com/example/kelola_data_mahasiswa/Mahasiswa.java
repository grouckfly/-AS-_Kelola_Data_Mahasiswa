package com.example.kelola_data_mahasiswa;

import java.io.Serializable; // Import library ini

public class Mahasiswa implements Serializable {

    // Penambahan serialVersionUID agar tidak ada masalah saat versi kelas berubah
    private static final long serialVersionUID = 1L;

    private String nim;
    private String nama;

    public Mahasiswa(String nim, String nama) {
        this.nim = nim;
        this.nama = nama;
    }

    public String getNim() {
        return nim;
    }
    public String getNama() {
        return nama;
    }
    public void setNim(String nim) {
        this.nim = nim;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    @Override
    public String toString() {
        return nim + " - " + nama;
    }
}