package com.example.kelola_data_mahasiswa;

import android.content.Context; // Import Context
import java.util.ArrayList;
import java.util.Comparator;

public class MahasiswaController {

    private ArrayList<Mahasiswa> daftarMahasiswa;
    private Context context; // Tambahkan variabel untuk Context

    // Ubah constructor untuk menerima Context
    public MahasiswaController(Context context) {
        this.context = context;
        // Muat data dari file saat controller dibuat
        this.daftarMahasiswa = FileHelper.loadData(context);

        // Jika data kosong
//        if (this.daftarMahasiswa.isEmpty()) {
//            tambahDataDummy();
//        }
    }

//    Data Dummy
//    private void tambahDataDummy() {
//        tambahMahasiswa("240101", "Budi Racing");
//        tambahMahasiswa("240102", "Mamat Gunshop");
//        tambahMahasiswa("240103", "Citra Pelangi");
//        tambahMahasiswa("240104", "Dewi Lestari");
//        tambahMahasiswa("240105", "Eko Prasetyo");
//        tambahMahasiswa("240106", "Fitri Anggraini");
//        tambahMahasiswa("240107", "Gilang Ramadhan");
//        tambahMahasiswa("240108", "Hesti Purwanti");
//        tambahMahasiswa("240109", "Indra Wijaya");
//        tambahMahasiswa("240110", "Joko Susilo");
//        tambahMahasiswa("240111", "Kartika Sari");
//        tambahMahasiswa("240112", "Lia Amelia");
//        tambahMahasiswa("240113", "Nadia Putri");
//        tambahMahasiswa("240114", "Oscar Maulana");
//        tambahMahasiswa("240115", "Putri Wulandari");
//        tambahMahasiswa("240116", "Qori'ah Halim");
//        tambahMahasiswa("240117", "Rizky Akbar");
//        tambahMahasiswa("240118", "Siska Apriani");
//        tambahMahasiswa("240119", "Toni Firmansyah");
//        tambahMahasiswa("240120", "Umar Sanjaya");
//        tambahMahasiswa("240121", "Vina Panduwinata");
//        tambahMahasiswa("240122", "Wahyu Hidayat");
//        tambahMahasiswa("240123", "Xavier Nugroho");
//        tambahMahasiswa("240124", "Yulia Permata");
//        tambahMahasiswa("240125", "Zainal Abidin");
//        tambahMahasiswa("240126", "Ahmad Dhani");
//        tambahMahasiswa("240127", "Bella Swan");
//        tambahMahasiswa("240128", "Cahyo Kumolo");
//        tambahMahasiswa("240129", "Dian Sastro");
//        tambahMahasiswa("240130", "Elang Gumilang");
//    }

    public ArrayList<Mahasiswa> getAllMahasiswa() {
        return daftarMahasiswa;
    }

    public boolean tambahMahasiswa(String nim, String nama) {
        if (findMahasiswaByNim(nim) != null) {
            return false;
        }
        daftarMahasiswa.add(new Mahasiswa(nim, nama));
        FileHelper.saveData(context, daftarMahasiswa); // Simpan perubahan
        return true;
    }

    public boolean hapusMahasiswaByNim(String nim) {
        Mahasiswa mhsUntukDihapus = findMahasiswaByNim(nim);
        if (mhsUntukDihapus != null) {
            daftarMahasiswa.remove(mhsUntukDihapus);
            FileHelper.saveData(context, daftarMahasiswa); // Simpan perubahan
            return true;
        }
        return false;
    }

    public boolean ubahMahasiswa(String nimLama, String nimBaru, String namaBaru) {
        Mahasiswa mhsUntukDiubah = findMahasiswaByNim(nimLama);
        if (mhsUntukDiubah == null) {
            return false;
        }
        if (!nimLama.equals(nimBaru) && findMahasiswaByNim(nimBaru) != null) {
            return false;
        }
        mhsUntukDiubah.setNim(nimBaru);
        mhsUntukDiubah.setNama(namaBaru);
        FileHelper.saveData(context, daftarMahasiswa); // Simpan perubahan
        return true;
    }

    public void urutkanMahasiswaByNim() {
        daftarMahasiswa.sort(Comparator.comparing(Mahasiswa::getNim));
        FileHelper.saveData(context, daftarMahasiswa); // Simpan perubahan
    }

    public Mahasiswa findMahasiswaByNim(String nim) {
        for (Mahasiswa mhs : daftarMahasiswa) {
            if (mhs.getNim().equals(nim)) {
                return mhs;
            }
        }
        return null;
    }
}