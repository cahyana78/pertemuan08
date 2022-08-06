package com.example.topik08_coba;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class InternalActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String FILENAME = "namafile.txt";
    Button buat, baca, ubah, hapus, eksternal;
    TextView txtBaca;
    EditText inputEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal);

        buat = findViewById(R.id.btnBuat);
        baca = findViewById(R.id.btnBaca);
        ubah = findViewById(R.id.btnUbah);
        hapus = findViewById(R.id.btnHps);
        txtBaca = findViewById(R.id.txtBaca);
        inputEditText = findViewById(R.id.inputText);

        eksternal = findViewById(R.id.btnExt);

        buat.setOnClickListener(this);
        baca.setOnClickListener(this);
        ubah.setOnClickListener(this);
        eksternal.setOnClickListener(this);
        hapus.setOnClickListener(this);


    }

    void buatFile(String isiFile){
        //String isiFile = "Coba isi data pada file txt";

        File file = new File(getFilesDir(), FILENAME);

        FileOutputStream outputStream = null;
        try{
            file.createNewFile();
            outputStream = new FileOutputStream(file, true);
            outputStream.write(isiFile.getBytes());
            outputStream.flush();
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void buatFileEksternal(){
        String isiFile = "Coba isi data pada file txt eksternal";
        String state = Environment.getExternalStorageState();

        if(!Environment.MEDIA_MOUNTED.equals(state))
            return;

        //pembuatan file media eksternal
        File file = new File(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_DOCUMENTS), FILENAME);

        FileOutputStream outputStream = null;
        try{
            file.createNewFile();
            outputStream = new FileOutputStream(file, true);
            outputStream.write(isiFile.getBytes());
            outputStream.flush();
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void bacaFile() {
        File file = new File(getFilesDir(), FILENAME);
        if(file.exists()) {
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();
                while (line != null) {
                    text.append(line);
                    line = br.readLine();
                }
                br.close();
            } catch (IOException e) {
                System.out.println("Error" + e.getMessage());
            }
            txtBaca.setText(text.toString());
        }
    }

    void ubahFile() {
        String ubah = "Update Isi Data File Text";
        File file = new File(getFilesDir(), FILENAME);
        FileOutputStream outputStream = null;
        try {
            file.createNewFile();
            outputStream = new FileOutputStream(file, false);
            outputStream.write(ubah.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void hapusFile(){
        File file = new File(getFilesDir(), FILENAME);
        if(file.exists()){
            new AlertDialog.Builder(InternalActivity.this)
                    .setTitle("Hapus File")
                    .setMessage("Apakah Anda yakin hapus file?")
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    })
                    .setNegativeButton(R.string.cancel, new
                            DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    }).show();
            file.delete();
            txtBaca.setText("");
        }
    }

    @Override
    public void onClick(View view) {
        jalankanPerintah(view.getId());
    }

    public void jalankanPerintah(int id){
        String isiFile = inputEditText.getText().toString();

        switch (id){
            case R.id.btnBuat:
                buatFile(isiFile);
                break;
            case R.id.btnBaca:
                bacaFile();
                break;
            case R.id.btnUbah:
                ubahFile();
                break;
            case R.id.btnHps:
                hapusFile();
                break;
            case R.id.btnExt:
                buatFileEksternal();
                break;
        }
    }
}