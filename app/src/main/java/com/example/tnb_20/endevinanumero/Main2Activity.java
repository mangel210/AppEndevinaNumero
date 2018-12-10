package com.example.tnb_20.endevinanumero;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Main2Activity extends AppCompatActivity {

    private ArrayList<Record> records = new ArrayList<Record>();
    private ArrayAdapter<Record> adapter;
    private static final String TAG = "Main2Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);


        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        //int intentos = intent.getIntExtra("int_value",0);
        //String nombre = intent.getStringExtra("string_name");
        //Record r = new Record(nombre, intentos);
        readFile();

        adapter = new ArrayAdapter<Record>( this, R.layout.list_item, records ){
            public View getView(int pos, View convertView, ViewGroup container)
            {
                // getView ens construeix el layout i hi "pinta" els valors de l'element en la posició pos
                if( convertView==null ) {
                    // inicialitzem l'element la View amb el seu layout
                    convertView = getLayoutInflater().inflate(R.layout.list_item, container, false);
                }
                // "Pintem" valors (també quan es refresca)
                ((TextView) convertView.findViewById(R.id.nom)).setText(getItem(pos).nombre);
                ((TextView) convertView.findViewById(R.id.intents)).setText(Integer.toString(getItem(pos).intentos));
                Uri uri = Uri.fromFile(getItem(pos).name_image);
                ((ImageView) convertView.findViewById(R.id.imageView)).setImageURI(uri);
                return convertView;
            };
        };

        // busquem la ListView i li endollem el ArrayAdapter
        ListView lv = (ListView) findViewById(R.id.recordsView);
        lv.setAdapter(adapter);
    }


    public void readFile(){
        String linea = null;
        String[] record = null;
        File file = new File(getApplicationContext().getFilesDir(),"records");

        try {
            FileInputStream inputStream = new FileInputStream (file);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while ( (linea = bufferedReader.readLine()) != null){
                Log.v("FILE_ERROR", "linea:" + linea);
                record = linea.split(":");
                records.add(new Record(record[0],Integer.parseInt(record[1]), new File("/data/data/com.example.tnb_20.endevinanumero/files/" + record[2])));
            }
            inputStream.close();
            bufferedReader.close();

            Log.v("FILE_ERROR", "Fichero leido con exito");
        } catch (Exception e) {

            Log.v("FILE_ERROR", "Error al leer el fichero");
        }
    }
}
