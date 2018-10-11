package com.example.mange.app1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    protected int numero;
    protected int finalValue;
    protected int cont = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText etNumero;

        final Intent i = new Intent();

        etNumero = findViewById(R.id.Numero);

/*        String value= etNumero.getText().toString();
        final int finalValue=Integer.parseInt(value);*/

        numero = (int) (Math.random() * 100) + 1;


        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String value= etNumero.getText().toString();
                int finalValue = Integer.parseInt(value);
                if (finalValue == numero) {
                    /*TextView txtCambiado = findViewById(R.id.textView);
                    txtCambiado.setText("Correcte");*/

                    Toast toastTrue = Toast.makeText(getApplicationContext(), "Correcte", Toast.LENGTH_LONG);
                    toastTrue.show();
                    numero = (int) (Math.random() * 100) + 1;
                }
                else {
                    /*TextView txtCambiado = findViewById(R.id.textView);
                    txtCambiado.setText("Incorrecte. Torna a probar");*/
                    String message = "";
                    if (finalValue < numero) {
                        message += "Incorrecte. És més gran";
                        etNumero.setText("");
                    } else {
                        message += "Incorrecte. És més petit";
                        etNumero.setText("");
                    }
                    Toast toastFalse = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
                    toastFalse.show();
                    cont++;
                }
            }
        });
        final Button buttonRanking = findViewById(R.id.button2);
        buttonRanking.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), FameActivity.class);
                startActivityForResult(intent, 0);
                intent.addFlags(cont);
            }
        });
    }
}
