package com.example.kelola_data_mahasiswa;

import android.content.Context;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileHelper {

    private static final String FILENAME = "datamahasiswa.dat";

    // Metode untuk menyimpan data ke file
    public static void saveData(Context context, ArrayList<Mahasiswa> data) {
        try (FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(data);
        } catch (Exception e) {
            e.printStackTrace(); // Cetak error jika terjadi masalah
        }
    }

    // Metode untuk memuat data dari file
    @SuppressWarnings("unchecked") // Untuk men-suppress warning konversi tipe data
    public static ArrayList<Mahasiswa> loadData(Context context) {
        ArrayList<Mahasiswa> data = new ArrayList<>();
        try (FileInputStream fis = context.openFileInput(FILENAME);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            data = (ArrayList<Mahasiswa>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace(); // Cetak error jika file tidak ditemukan atau masalah lain
        }
        return data;
    }
}