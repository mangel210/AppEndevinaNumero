package com.example.tnb_20.endevinanumero;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Xml;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnKeyListener;


import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.UUID;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    protected int nIntents;
    protected boolean isSave = false;
    protected int lastIntents;
    protected int number;
    protected  String tName;
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    static final int REQUEST_IMAGE_CAPTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        preFile();

        final Button button = findViewById(R.id.btnEndevina);
        final EditText text = findViewById(R.id.textInt);
        //final TextView textV = findViewById(R.id.txtView);
        final Random rNumber = new Random();
        number = rNumber.nextInt(100) + 1;
        nIntents = 0;
        text.setText(Integer.toString(number));
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int iNumber = Integer.parseInt(text.getText().toString());
                if(number < iNumber){
                    //textV.setText("El numero es menor de " + iNumber);
                    Toast toast = Toast.makeText(getApplicationContext(),"El numero es menor de " + iNumber, Toast.LENGTH_SHORT);
                    toast.show();
                    text.setText("");
                    nIntents++;
                }else if (number > iNumber){
                   // textV.setText("El numero es mayor de " + iNumber);
                    Toast toast = Toast.makeText(getApplicationContext(),"El numero es mayor de " + iNumber, Toast.LENGTH_SHORT);
                    toast.show();
                    text.setText("");
                    nIntents++;
                }else if(number == iNumber){
                    nIntents++;
                    lastIntents = nIntents;
                    //textV.setText("Has adivinado el numero " + iNumber + ", Numero de intentos: " + nIntents + ", Torna a començar");
                    Toast toast = Toast.makeText(getApplicationContext(),"Has endevinat el numero " + iNumber + "\n Numero de intents: " + lastIntents, Toast.LENGTH_SHORT);
                    toast.show();
                    text.setText("");
                    nIntents = 0;
                    number = rNumber.nextInt(100) + 1;
                    alertView(lastIntents);
                }

            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
       Intent intent = new Intent(this, Main2Activity.class);
       // intent.putExtra("int_value", lastIntents);
      //  intent.putExtra("string_name", tName);

       startActivity(intent);
    }

    private void alertView(final int intents) {
        final Dialog dialog = new Dialog(MainActivity.this);
        final String[] datos = {null, Integer.toString(intents)};
        dialog.setTitle( "" );
        dialog.setContentView(R.layout.layaout_dialog);
        Button btnOK = dialog.findViewById(R.id.btnok);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText name = dialog.findViewById(R.id.editTextName);
                tName = name.getText().toString();
                datos[0] = name.getText().toString();
                dialog.dismiss();

                //Encender la camar para hacer la foto;
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
                saveRecord(name.getText().toString(), intents);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void saveRecord(String name, int intents){
        String textRecord = name + ":" + intents + ":";
        File file = new File(getApplicationContext().getFilesDir(),"records");

        try {
            FileOutputStream outputStream = new FileOutputStream(file,true);
            outputStream.write(textRecord.getBytes());
            outputStream.close();
            Log.v("FILE_ERROR", "Dades guardades");

        } catch (Exception e) {
            e.printStackTrace();
            Log.v("FILE_ERROR", "Error escrivint arxiu");
        }
    }

    private void preFile(){

        try {
            File file = new File(getApplicationContext().getFilesDir(),"records");
            //file.delete();
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.v("FILE_ERROR", "Error");
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //nombre aleatorio
       String nameImage = "image_" + UUID.randomUUID().toString() + ".jpg";

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            // guardar a disc
            try {
                FileOutputStream out = openFileOutput(nameImage, MODE_PRIVATE);
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.close();

                saveImage(nameImage);
                Log.v("FILE ERROR", "Imatege Guradad");

            } catch (Exception e) {
                Log.v("FILE ERROR", "Error escrivint arxiu");
                e.printStackTrace();
            }
        }
    }

    private void saveImage(String image){
        String textRecord = image;
        File file = new File(getApplicationContext().getFilesDir(),"records");

        try {
            FileOutputStream outputStream = new FileOutputStream(file,true);
            outputStream.write((textRecord + System.getProperty("line.separator")).getBytes());
            outputStream.close();
            Log.v("FILE_ERROR", "Dades guardades");

        } catch (Exception e) {
            e.printStackTrace();
            Log.v("FILE_ERROR", "Error escrivint arxiu");
        }
    }
}
