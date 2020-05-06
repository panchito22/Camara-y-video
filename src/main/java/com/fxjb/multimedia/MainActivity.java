package com.fxjb.multimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //metodo boton

    public void Video(View view){
        Intent video = new Intent(this,VideoActivity.class);
        startActivity(video);
    }

    public void Foto(View view){
        Intent foto = new Intent(this,FotoActivity.class);
        startActivity(foto);
    }
}
