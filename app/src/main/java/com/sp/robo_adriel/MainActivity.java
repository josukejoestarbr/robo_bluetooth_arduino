package com.sp.robo_adriel;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    Button bluetooth, Descon, Config,Frente,tras,Esquerda,Direita,Habilitar,parar,voz,sequencia;
    int REQUEST_ENABLE_BT=1;
    int REQUEST_DISCOVER_BT=0;
    int CONEXAO=2;
    BluetoothAdapter bta;
    String MAC=null;
    TextView Status;
    Boolean valida=false;
    BluetoothDevice dispositivo=null;
    BluetoothSocket socket=null;
    InputStream mmInStream;
    OutputStream mmOutStream;
     byte[] mmBuffer; // mmBuffer store for the stream
    UUID meuuuid= UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    String frente=null;
    String trass=null;
    String esquerda=null;
    String direita=null;
    String pararr=null;
    String filepath="config";
    String configuracao=null;
    String[] configuracao1=null;
    Boolean usa=false;
    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getSupportActionBar().hide();
        if (requestCode == CONEXAO && resultCode == RESULT_OK) {
            // conexao de dados
          MAC=data.getExtras().getString(ListaDispositivos.Endereco_mac);
            //Toast.makeText(getApplicationContext(),"MAC: "+MAC,Toast.LENGTH_LONG).show();

            dispositivo= bta.getRemoteDevice(MAC);

            try {
                socket= dispositivo.createRfcommSocketToServiceRecord(meuuuid);
               socket.connect();
                Toast.makeText(getApplicationContext(),"voce foi conectado com: " + MAC,Toast.LENGTH_LONG).show();
                valida=true;
               // ParcelUuid[] uuids = dispositivo.getUuids();
                 //socket = dispositivo.createRfcommSocketToServiceRecord(uuids[0].getUuid());
                //socket.connect();
                mmOutStream = socket.getOutputStream();
                mmInStream = socket.getInputStream();

            } catch (IOException e) {
                Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
            }

        }
        else{
            Toast.makeText(getApplicationContext(),"falha ao obter o mack",Toast.LENGTH_LONG).show();
        }
    }
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parar= (Button) findViewById(R.id.Parar);
        Frente= (Button) findViewById(R.id.frente);
        tras= (Button) findViewById(R.id.tras);
        Esquerda= (Button) findViewById(R.id.esquerda);
        Direita= (Button) findViewById(R.id.direita);
        Config= (Button) findViewById(R.id.config);
        Descon= (Button) findViewById(R.id.descon);
        Habilitar= (Button) findViewById(R.id.habilitar);
        voz= (Button) findViewById(R.id.Voz);
        sequencia= (Button) findViewById(R.id.Sequencia);
        Status= (TextView) findViewById(R.id.status);
        bta = BluetoothAdapter.getDefaultAdapter();
        boolean conexao=false;
        if(bta==null){
            Status.setText("sem conexão");
        }
        else{
            Status.setText("Bluetooth está disponível");
            Intent intb= new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intb,REQUEST_ENABLE_BT);

        }
        // ajustar as configurações
        File caminho = new File(getExternalFilesDir(filepath), "configurar.txt");
        if(caminho.exists()){
            try {
                FileInputStream fis = new FileInputStream(caminho);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader buffreader = new BufferedReader(isr);
                configuracao = buffreader.readLine();
                Toast.makeText(MainActivity.this, configuracao, Toast.LENGTH_LONG).show();
                configuracao1= configuracao.split(",");
                frente= configuracao1[1];
                trass=configuracao1[2];
                direita=configuracao1[3];
                esquerda=configuracao1[4];
                pararr=configuracao1[5];
                usa=true;

            } catch (FileNotFoundException e) {
                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }


        //sequencia da voz
        sequencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valida==true ){
                    Toast.makeText(MainActivity.this,"sequencia",Toast.LENGTH_LONG).show();
                    String a= "s";
                    try {
                        mmOutStream.write(a.getBytes());

                    } catch (IOException e) {
                        Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_LONG).show();
                    }

                }
                else{
                    Toast.makeText(MainActivity.this,"bluetooth não habilitado",Toast.LENGTH_LONG).show();

                }





        }
        });
        // tela da voz
        voz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valida==true ){
                    Toast.makeText(MainActivity.this,"voz",Toast.LENGTH_LONG).show();
                    String a= "v";
                    try {
                        mmOutStream.write(a.getBytes());

                    }
                    catch (IOException e) {
                        Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_LONG).show();
                    }
                }

                else{
                    Toast.makeText(MainActivity.this,"bluetooth não habilitado",Toast.LENGTH_LONG).show();

                }



        }
        });
        // tela de config
        Config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valida==true){
                    valida=false;
                    try {
                        mmOutStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                Intent Inicio= new Intent(MainActivity.this,config.class);
                startActivity(Inicio);

            }
        });
        // comandos botoes
        Frente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valida==true && usa==false) {
                    Frente.setBackgroundResource(R.drawable.manipular_frentepress);
                    Toast.makeText(MainActivity.this,"frente",Toast.LENGTH_LONG).show();
                    String a= "f";
                    try {
                        mmOutStream.write(a.getBytes());

                    } catch (IOException e) {
                        Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_LONG).show();
                    }
                    Frente.setBackgroundResource(R.drawable.manipular_frente);
                }
                  else{
                    Toast.makeText(MainActivity.this,"ligue o bluetooth para dar comandos",Toast.LENGTH_LONG).show();
                }
                if(valida==true && usa==true) {
                    Frente.setBackgroundResource(R.drawable.manipular_frentepress);
                    Toast.makeText(MainActivity.this,"frente",Toast.LENGTH_LONG).show();

                    try {
                        mmOutStream.write(frente.getBytes());

                    } catch (IOException e) {
                        Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_LONG).show();
                    }
                    Frente.setBackgroundResource(R.drawable.manipular_frente);
                }
                else{
                    Toast.makeText(MainActivity.this,"ligue o bluetooth para dar comandos",Toast.LENGTH_LONG).show();
                }



                    };
                });
        tras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valida==true && usa==false) {
                    Toast.makeText(MainActivity.this,"tras",Toast.LENGTH_LONG).show();
                    String a= "t";
                    try {
                        mmOutStream.write(a.getBytes());

                    } catch (IOException e) {
                        Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_LONG).show();
                    }

                }
                else{
                    Toast.makeText(MainActivity.this,"ligue o bluetooth para dar comandos",Toast.LENGTH_LONG).show();
                }

                if(valida==true && usa==true) {
                    Toast.makeText(MainActivity.this,"tras",Toast.LENGTH_LONG).show();

                    try {
                        mmOutStream.write(trass.getBytes());

                    } catch (IOException e) {
                        Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_LONG).show();
                    }

                }
                else{
                    Toast.makeText(MainActivity.this,"ligue o bluetooth para dar comandos",Toast.LENGTH_LONG).show();
                }

            };
        });
        Esquerda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valida==true && usa==false) {
                    Toast.makeText(MainActivity.this,"esquerda",Toast.LENGTH_LONG).show();
                    String a= "e";
                    try {
                        mmOutStream.write(a.getBytes());

                    } catch (IOException e) {
                        Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_LONG).show();
                    }

                }
                else{
                    Toast.makeText(MainActivity.this,"ligue o bluetooth para dar comandos",Toast.LENGTH_LONG).show();
                }
                if(valida==true && usa==true) {
                    Toast.makeText(MainActivity.this,"esquerda",Toast.LENGTH_LONG).show();

                    try {
                        mmOutStream.write(esquerda.getBytes());

                    } catch (IOException e) {
                        Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_LONG).show();
                    }

                }
                else{
                    Toast.makeText(MainActivity.this,"ligue o bluetooth para dar comandos",Toast.LENGTH_LONG).show();
                }



            };
        });
        Direita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valida==true && usa==false) {
                    Toast.makeText(MainActivity.this,"direita",Toast.LENGTH_LONG).show();
                    String a= "d";
                    try {
                        mmOutStream.write(a.getBytes());

                    } catch (IOException e) {
                        Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_LONG).show();
                    }

                }
                else{
                    Toast.makeText(MainActivity.this,"ligue o bluetooth para dar comandos",Toast.LENGTH_LONG).show();
                }

                if(valida==true && usa==true) {
                    Toast.makeText(MainActivity.this,"direita",Toast.LENGTH_LONG).show();

                    try {
                        mmOutStream.write(direita.getBytes());

                    } catch (IOException e) {
                        Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_LONG).show();
                    }

                }
                else{
                    Toast.makeText(MainActivity.this,"ligue o bluetooth para dar comandos",Toast.LENGTH_LONG).show();
                }

            };
        });
        parar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (valida==true && usa==false) {
                    Toast.makeText(MainActivity.this,"parar",Toast.LENGTH_LONG).show();
                    String a= "p";
                    try {
                        mmOutStream.write(a.getBytes());

                    } catch (IOException e) {
                        Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_LONG).show();
                    }

                }
                else{
                    Toast.makeText(MainActivity.this,"ligue o bluetooth para dar comandos",Toast.LENGTH_LONG).show();
                }
                if (valida==true && usa==true) {
                    Toast.makeText(MainActivity.this,"parar",Toast.LENGTH_LONG).show();

                    try {
                        mmOutStream.write(pararr.getBytes());

                    } catch (IOException e) {
                        Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_LONG).show();
                    }

                }
                else{
                    Toast.makeText(MainActivity.this,"ligue o bluetooth para dar comandos",Toast.LENGTH_LONG).show();
                }



            };
        });
        // Conectar bluetooth
        Habilitar.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                Intent abreLista= new Intent(MainActivity.this,ListaDispositivos.class);
                startActivityForResult(abreLista,CONEXAO);
                // if(!bta.isDiscovering()){
                //    Toast.makeText(MainActivity.this,"Faça seu dispositivo ser reconhecido",3);
                //  Intent blue= new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                //  startActivityForResult(blue,REQUEST_DISCOVER_BT);
               // }

            }
        });
        // Parear bluetooth


         // Desconectar bluetooth

        Descon.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                if((valida==true)){

                    try {
                        mmOutStream.close();
                        Toast.makeText(MainActivity.this,"Desconectado",Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_LONG).show();
                    }

                }
            }
        });



    }


}