package com.sp.robo_adriel;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class ListaDispositivos extends ListActivity {
    private BluetoothAdapter bluet=null;
    static String Endereco_mac= null;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ArrayAdapter<String> arraybluetooth= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        bluet=BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> dispositivospareados = bluet.getBondedDevices();
        if(dispositivospareados.size() >0){
            for (BluetoothDevice dispositivo :dispositivospareados){
                String nomebt= dispositivo.getName();
                String macbt= dispositivo.getAddress();
                arraybluetooth.add(nomebt +"/n"+ macbt);
            }
            setListAdapter(arraybluetooth);
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String informacaogeral= ((TextView)v).getText().toString();
        Toast.makeText(getApplicationContext(),"info:"+ informacaogeral,Toast.LENGTH_LONG).show();
        String enderecomac= informacaogeral.substring(informacaogeral.length()-17);
        Toast.makeText(getApplicationContext(),"mac:"+ enderecomac,Toast.LENGTH_LONG).show();
        Intent retornamac=new Intent();
        retornamac.putExtra(Endereco_mac,enderecomac);
        setResult(RESULT_OK,retornamac);
        finish();
    }
}
