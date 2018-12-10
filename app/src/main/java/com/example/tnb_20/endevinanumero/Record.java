package com.example.tnb_20.endevinanumero;

import java.io.File;
import java.util.ArrayList;

public class Record implements Comparable<Record>{
    public int intentos;
    public String nombre;
    public File name_image;

    public Record(String nombre, int intentos, File name_image){
        this.nombre = nombre;
        this.intentos = intentos;
        this.name_image = name_image;
    }

    public int compareTo(Record o){
        if(intentos < o.intentos){
            return -1;
        }
        if(intentos > o.intentos){
            return 1;
        }
        return 0;
    }

    public int getIntentos(){
        return intentos;
    }

    public String getNombre(){
        return nombre;
    }

    public File getName_image(){return name_image;}

}
