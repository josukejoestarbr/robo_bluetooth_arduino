package com.sp.robo_adriel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class config extends AppCompatActivity {
    Button inicio,Salvar;
    EditText frente;
    EditText tras;
    EditText esquerda;
    EditText direita;
    EditText parar;
    String soma;
    String trass=null;
    String filepath="config";
    String fr=null;
    String dirr=null;
    String esqq=null;
    String parr=null;
    TextView Comandos;
    String valores;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_config);
        inicio = (Button) findViewById(R.id.volta);
        Salvar = (Button) findViewById(R.id.salvar);
        frente= (EditText) findViewById(R.id.cfrente);
        tras= (EditText) findViewById(R.id.ctras);
        esquerda= (EditText) findViewById(R.id.cesquerda);
        direita= (EditText) findViewById(R.id.cdireita);
        parar= (EditText) findViewById(R.id.cparar);
        Comandos= (TextView) findViewById(R.id.comandos);
        File caminho = new File(getExternalFilesDir(filepath), "configurar.txt");
        if(caminho.exists()){
            try {
                FileInputStream fis = new FileInputStream(caminho);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader buffreader = new BufferedReader(isr);
                String configuracao = buffreader.readLine();
                String[] configuracao1= configuracao.split(",");
                valores=configuracao1[1]+"," +configuracao1[2]+","+
                        configuracao1[3]+"," +configuracao1[4]+","+configuracao1[5];

                Comandos.setText(valores);



            } catch (FileNotFoundException e) {
                Toast.makeText(config.this, e.toString(), Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(config.this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
        //voltar a tela inicial
        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Inicio= new Intent(config.this,MainActivity.class);
                startActivity(Inicio);
            }
        });
        // funcao para salvar os comandos
        Salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File caminho = new File(getExternalFilesDir(filepath), "configurar.txt");
                if(caminho.exists()){
                    try {
                        FileInputStream fis = new FileInputStream(caminho);
                        InputStreamReader isr = new InputStreamReader(fis);
                        BufferedReader buffreader = new BufferedReader(isr);
                        String configuracao = buffreader.readLine();
                        Toast.makeText(config.this, configuracao, Toast.LENGTH_LONG).show();
                        String[] configuracao1= configuracao.split(",");
                        fr= configuracao1[1];
                        trass=configuracao1[2];
                        dirr=configuracao1[3];
                        esqq=configuracao1[4];
                        parr=configuracao1[5];

                    } catch (FileNotFoundException e) {
                        Toast.makeText(config.this, e.toString(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        Toast.makeText(config.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
                String fren= frente.getText().toString();
                if(fren.equals("")){
                    Toast.makeText(config.this,"caixa vazia",Toast.LENGTH_LONG).show();
                     soma=","+fr +",";
                }
                else{
                    soma+=","+fren +",";

                }
                String tra= tras.getText().toString();
                if(tra.equals("")){
                    Toast.makeText(config.this,"caixa vazia",Toast.LENGTH_LONG).show();
                    soma+=trass +",";
                }
                else{
                    soma+=tra +",";

                }
                String dir= direita.getText().toString();
                if(dir.equals("")){
                    Toast.makeText(config.this,"caixa vazia",Toast.LENGTH_LONG).show();
                    soma+= dirr+",";
                }
                else{
                    soma+=dir +",";

                }
                String esq= esquerda.getText().toString();
                if(esq.equals("")){
                    Toast.makeText(config.this,"caixa vazia",Toast.LENGTH_LONG).show();
                    soma+=esqq +",";
                }
                else{
                    soma+=esq +",";

                }
                String par= parar.getText().toString();
                if(par.equals("")){
                    Toast.makeText(config.this,"caixa vazia",Toast.LENGTH_LONG).show();
                    soma+=parr +",";
                }
                else{
                    soma+=par +",";

                }
                File caminhoarquivo= new File(getExternalFilesDir(filepath),"configurar.txt");
                try {
                    FileOutputStream fos= new FileOutputStream(caminhoarquivo);
                    fos.write(soma.getBytes());
                    fos.close();
                    Toast.makeText(config.this,"conteudo salvo",Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    Toast.makeText(config.this,e.toString(),Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Toast.makeText(config.this,e.toString(),Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}