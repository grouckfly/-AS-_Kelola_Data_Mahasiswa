package com.example.kelola_data_mahasiswa;

import android.os.Bundle;
import android.text.InputType;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private MahasiswaController controller;
    private ArrayAdapter<Mahasiswa> adapter;
    private ListView listViewMahasiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Berikan context (this) saat membuat controller
        controller = new MahasiswaController(this);

        listViewMahasiswa = findViewById(R.id.listViewMahasiswa);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, controller.getAllMahasiswa());
        listViewMahasiswa.setAdapter(adapter);

        Button btnMenu = findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(v -> tampilkanMenu());

    }

    private void tampilkanMenu() {
        String[] options = {
                "1. Menambahkan Data Mahasiswa",
                "2. Menghapus Data Mahasiswa",
                "3. Mengubah Data Mahasiswa",
                "4. Mengurutkan Data Mahasiswa",
                "5. Menampilkan Data Mahasiswa"
        };
        new AlertDialog.Builder(this)
                .setTitle("Menu Pilihan")
                .setItems(options, (dialog, which) -> {
                    switch (which) {
                        case 0: dialogTambahData(); break;
                        case 1: dialogHapusData(); break;
                        case 2: dialogUbahData(); break;
                        case 3: prosesUrutkanData(); break;
                        case 4: dialogTampilkanData(); break;
                    }
                })
                .show();
    }

    private void dialogTambahData() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tambah Data Mahasiswa");
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText inputNim = new EditText(this);
        inputNim.setHint("NIM");
        inputNim.setInputType(InputType.TYPE_CLASS_NUMBER);
        layout.addView(inputNim);
        final EditText inputNama = new EditText(this);
        inputNama.setHint("Nama Mahasiswa");
        layout.addView(inputNama);
        builder.setView(layout);
        builder.setPositiveButton("Tambah", (dialog, which) -> {
            String nim = inputNim.getText().toString().trim();
            String nama = inputNama.getText().toString().trim();
            if (!nim.isEmpty() && !nama.isEmpty()) {
                // Panggil controller dan cek hasilnya
                boolean isSuccess = controller.tambahMahasiswa(nim, nama);
                if (isSuccess) {
                    adapter.notifyDataSetChanged();
                    tampilkanPesan("BERHASIL DITAMBAHKAN", nama);
                } else {
                    // Tampilkan pesan error jika duplikat
                    Toast.makeText(this, "Gagal! NIM " + nim + " sudah terdaftar.", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "NIM dan Nama tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Batal", null).show();
    }

    private void dialogHapusData() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Hapus Data Mahasiswa");
        final EditText inputNim = new EditText(this);
        inputNim.setHint("Masukkan NIM yang akan dihapus");
        inputNim.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(inputNim);
        builder.setPositiveButton("Hapus", (dialog, which) -> {
            String nimDihapus = inputNim.getText().toString().trim();
            Mahasiswa mhs = controller.findMahasiswaByNim(nimDihapus);
            if (mhs != null) {
                // Panggil controller untuk menghapus
                controller.hapusMahasiswaByNim(nimDihapus);
                adapter.notifyDataSetChanged(); // Refresh adapter
                tampilkanPesan("BERHASIL DIHAPUS", mhs.getNama());
            } else {
                Toast.makeText(this, "NIM tidak ditemukan!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Batal", null).show();
    }

    private void dialogUbahData() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cari Data untuk Diubah");
        final EditText inputNimCari = new EditText(this);
        inputNimCari.setHint("Masukkan NIM mahasiswa yang akan diubah");
        builder.setView(inputNimCari);
        builder.setPositiveButton("Cari", (dialog, which) -> {
            String nimDicari = inputNimCari.getText().toString().trim();
            Mahasiswa mhsUntukDiubah = controller.findMahasiswaByNim(nimDicari);
            if (mhsUntukDiubah != null) {
                dialogUpdate(mhsUntukDiubah);
            } else {
                Toast.makeText(this, "NIM tidak ditemukan", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Batal", null).show();
    }

    private void dialogUpdate(Mahasiswa mhs) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Masukkan Data Baru");
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText inputNimBaru = new EditText(this);
        inputNimBaru.setHint("NIM Baru");
        inputNimBaru.setText(mhs.getNim());
        layout.addView(inputNimBaru);
        final EditText inputNamaBaru = new EditText(this);
        inputNamaBaru.setHint("Nama Baru");
        inputNamaBaru.setText(mhs.getNama());
        layout.addView(inputNamaBaru);
        builder.setView(layout);
        builder.setPositiveButton("Ubah", (dialog, which) -> {
            String nimBaru = inputNimBaru.getText().toString().trim();
            String namaBaru = inputNamaBaru.getText().toString().trim();
            if (!nimBaru.isEmpty() && !namaBaru.isEmpty()) {
                // Panggil controller dan cek hasilnya
                boolean isSuccess = controller.ubahMahasiswa(mhs.getNim(), nimBaru, namaBaru);
                if (isSuccess) {
                    adapter.notifyDataSetChanged();
                    tampilkanPesan("BERHASIL DIUBAH", namaBaru);
                } else {
                    // Tampilkan pesan error jika duplikat
                    Toast.makeText(this, "Gagal! NIM " + nimBaru + " sudah digunakan mahasiswa lain.", Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton("Batal", null).show();
    }

    private void prosesUrutkanData() {
        // Panggil controller untuk mengurutkan
        controller.urutkanMahasiswaByNim();
        adapter.notifyDataSetChanged(); // Refresh adapter
        tampilkanInfo("Data berhasil diurutkan berdasarkan NIM.");
    }

    private void dialogTampilkanData() {
        // Jika tidak ada data, tampilkan pesan singkat dan jangan lanjutkan
        if (controller.getAllMahasiswa().isEmpty()) {
            tampilkanInfo("Tidak ada data mahasiswa untuk ditampilkan.", "Informasi");
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Data Mahasiswa Saat Ini");

        // 1. Inflate (memuat) layout custom
        android.view.View dialogView = getLayoutInflater().inflate(R.layout.dialog_tampilkan_data, null);

        // 2. Cari ListView di dalam layout custom
        ListView listViewDialog = dialogView.findViewById(R.id.listViewDialog);

        // 3. Buat Adapter baru khusus untuk ListView di dalam dialog
        ArrayAdapter<Mahasiswa> dialogAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                controller.getAllMahasiswa()
        );

        // 4. Hubungkan Adapter dengan ListView di dalam dialog
        listViewDialog.setAdapter(dialogAdapter);

        // 5. Atur custom view ini sebagai konten dari dialog
        builder.setView(dialogView);

        builder.setPositiveButton("Tutup", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    // --- Metode untuk menampilkan dialog pesan ---

    private void tampilkanPesan(String status, String nama) {
        new AlertDialog.Builder(this)
                .setTitle("Status Operasi")
                .setMessage("Nama Mahasiswa: " + nama + ", " + status)
                .setPositiveButton("OK", null)
                .show();
    }

    private void tampilkanInfo(String message, String title) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
    private void tampilkanInfo(String message) {
        tampilkanInfo(message, "Informasi");
    }
}