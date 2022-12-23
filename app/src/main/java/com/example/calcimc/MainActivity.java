package com.example.calcimc;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {

    private TextView txtVlrIMC;
    private TextView txtTpIMC;
    private EditText editPs;
    private EditText editAlt;

    private BigDecimal IMC;

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnCalc = findViewById(R.id.btnCalc);
        ImageButton btnAjd = findViewById(R.id.btnAjuda);
        txtTpIMC = findViewById(R.id.textTpIMC);
        txtVlrIMC = findViewById(R.id.textVlrIMC);
        editAlt = findViewById(R.id.editAlt);
        editPs = findViewById(R.id.editPs);

        editPs.setOnFocusChangeListener((view, b) -> {
            if (!b){
                esconderTeclado(view);
            }
        });

        editAlt.setOnFocusChangeListener((view, b) -> {
            if (!b){
                esconderTeclado(view);
            }
        });

        btnAjd.setOnClickListener(view -> {
            AlertDialog.Builder msgAjuda = new AlertDialog.Builder(this, R.style.DialogStyle);
            msgAjuda.setIcon(R.drawable.ic_baseline_help_24);
            msgAjuda.setTitle("AJUDA");
            msgAjuda.setMessage(
                    "PARA UTILIZAR A CALCULADORA BASTA PREENCHER AS INFORMAÇÕES E CLICAR EM CLACULAR!!!\n\n"+
                    "O INDICE DE MASSA CORPORAL (IMC), CALCULA O ÍNDICE DE OBESIDADE "+
                    "ATRAVÉS DA ALTURA E DO PESO.\n\n"+
                    "PARA SABER MAIS ACESSE O LINK.");
            msgAjuda.setPositiveButton(R.string.link, (dialogInterface, i) -> startActivity(new Intent
                    (Intent.ACTION_VIEW, Uri.parse("https://qbemqfaz.com.br/vida-equilibrada/tabela"+
                            "-imc?gclid=Cj0KCQiAwJWdBhCYARIsAJc4idA8LxUYY5mUJe4R8gItjkkPA0_VNlXJRSOg0"+
                            "Odr2H-Y4kR83XqltPsaAvVNEALw_wcB"))));
            msgAjuda.show();
        });

        btnCalc.setOnClickListener(view -> {
            try{
                calcularIMC();
                tipoIMC();
                editPs.setText("");
                editAlt.setText("");
            }catch (Exception E){
                txtVlrIMC.setText("VALOR");
                txtTpIMC.setText("INVÁLIDO");
            }
            esconderTeclado(view);
        });
    }

    @SuppressLint("SetTextI18n")
    public void calcularIMC (){

        BigDecimal altura = new BigDecimal(editAlt.getText().toString().replace(",", "."));
        BigDecimal peso = new BigDecimal(editPs.getText().toString().replace(",", "."));

        IMC = new BigDecimal("0").setScale(2, RoundingMode.HALF_EVEN);
        IMC = altura.multiply(altura);
        IMC = peso.divide(IMC, MathContext.DECIMAL64);

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

    public void esconderTeclado (View view){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}