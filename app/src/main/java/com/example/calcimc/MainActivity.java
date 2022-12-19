package com.example.calcimc;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {

    private TextView txtVlrIMC;
    private TextView txtTpIMC;
    private EditText editPs;
    private EditText editAlt;

    private BigDecimal IMC;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnCalc = findViewById(R.id.btnCalc);
        txtTpIMC = findViewById(R.id.textTpIMC);
        txtVlrIMC = findViewById(R.id.textVlrIMC);
        editAlt = findViewById(R.id.editAlt);
        editPs = findViewById(R.id.editPs);

        btnCalc.setOnClickListener(view -> {
            try{
                calcularIMC();
                tipoIMC();
            }catch (Exception E){
                txtVlrIMC.setText("VALOR");
                txtTpIMC.setText("INVÁLIDO");
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void calcularIMC (){
        BigDecimal altura = new BigDecimal(editAlt.getText().toString());
        BigDecimal peso = new BigDecimal(editPs.getText().toString());

        IMC = new BigDecimal(altura.multiply(altura).toString());
        IMC = peso.divide(IMC);

        txtVlrIMC.setText(IMC.setScale(2, RoundingMode.HALF_EVEN).toString());
    }

    @SuppressLint("SetTextI18n")
    public void tipoIMC (){
        int imc = IMC.intValue();
        if (imc < 18.5){
            txtTpIMC.setText("ABAIXO DO PESO");
        }else if(imc >=18.5 && imc < 25){
            txtTpIMC.setText("PESO NORMAL");
        }else if(imc >= 25 && imc < 30){
            txtTpIMC.setText("ACIMA DO PESO");
        }else if(imc >= 30 && imc < 35){
            txtTpIMC.setText("OBESIDADE 1");
        }else if(imc >= 35 && imc < 40){
            txtTpIMC.setText("OBESIDADE 2");
        }else if(imc > 40){
            txtTpIMC.setText("OBESIDADE 3");
        }
    }
}