package com.fxjb.multimedia;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.VideoView;

import static android.Manifest.permission.CAMERA;

public class VideoActivity extends Activity implements OnClickListener{

    Button btn;
    VideoView vid;
    Intent i;
    final static int cons= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        init();
        if(validaPermisos()){
            btn.setEnabled(true);

        }else{
            btn.setEnabled(false);
        }
    }

    public void Anterior1(View view){
        Intent back = new Intent(this,MainActivity.class);
        startActivity(back);
    }
    //metodos
    private boolean validaPermisos() {
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return  true;

        }
        if((checkSelfPermission(CAMERA)== PackageManager.PERMISSION_GRANTED)){
            return true;

        }
        if((shouldShowRequestPermissionRationale(CAMERA))){
            cargarDialogoRecomendacion();
        }else{
            requestPermissions(new String[]{CAMERA},100);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


    }

    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo= new AlertDialog.Builder(VideoActivity.this);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermissions(new String[]{CAMERA},100);
            }
        });
        dialogo.show();
    }

    public void init(){
        btn = (Button)findViewById(R.id.btnVideo);
        btn.setOnClickListener(this);

        vid = (VideoView) findViewById(R.id.video);

    }
    @Override
    public void onClick(View v) {
        int id;
        id = v.getId();
        switch (id)
        {
            case R.id.btnVideo:
                i = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);//busca una actividad en este caso la camara
                startActivityForResult(i,cons);
                break;

        }

    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode ==cons && resultCode == RESULT_OK)
        {
            Uri videoUri = data.getData();
            if(videoUri != null)
            {
                vid.setVideoURI(videoUri);
                vid.start();
            }

        }
    }

}
