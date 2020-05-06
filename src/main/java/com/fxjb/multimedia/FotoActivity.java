package com.fxjb.multimedia;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.provider.MediaStore;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View.OnClickListener;

import static android.Manifest.permission.CAMERA;

public class FotoActivity extends Activity implements OnClickListener {

    Button btn;
    ImageView img;
    Intent i;
    Bitmap bmp;
    final static int cons= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto);
        init();
        if(validaPermisos()){
            btn.setEnabled(true);

        }else{
            btn.setEnabled(false);
        }
    }
    //metodo boton

    public void Anterior(View view){
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
        AlertDialog.Builder dialogo= new AlertDialog.Builder(FotoActivity.this);
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
        btn = (Button)findViewById(R.id.btnTomar);
        btn.setOnClickListener(this);

        img = (ImageView)findViewById(R.id.imagen);
    }



    @Override
    public void onClick(View v) {
        int id;
        id = v.getId();
        switch (id)
        {
            case R.id.btnTomar:
                i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//busca una actividad en este caso la camara
                startActivityForResult(i,cons);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == Activity.RESULT_OK)
        {
            Bundle ext = data.getExtras();
            bmp = (Bitmap)ext.get("data");
            img.setImageBitmap(bmp);
        }
    }

}
