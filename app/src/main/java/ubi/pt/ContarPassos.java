package ubi.pt;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class ContarPassos extends Fragment implements SensorEventListener {

    private SensorManager sensorManager;
    private TextView count;
    boolean running;

    Toolbar toolbar;

    private Button bIniciar;
    private Button bTerminar;
    private Chronometer cronometro;
    private long pausa;

    private int cont;
    private int numeroFeito;
    private int contador;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.contar_passos,null);

        bIniciar = (Button) view.findViewById(R.id.bIniciar);
        bTerminar = (Button) view.findViewById(R.id.bTerminar);

        toolbar = view.findViewById(R.id.toolbar_passos);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        count = (TextView) view.findViewById(R.id.passos);
        sensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);

        cronometro = view.findViewById(R.id.cronometro);

        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(getActivity(), "Count sensor not available!", Toast.LENGTH_LONG).show();
        }

        bIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cronometro.setBase(SystemClock.elapsedRealtime());
                pausa = 0;
                contador =0;
                count.setText(String.valueOf(contador));


                if(!running){
                    //Cronometro
                    cronometro.setBase((SystemClock.elapsedRealtime() - pausa));
                    cronometro.start();
                    running = true;


                }
            }
        });

        bTerminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(running){
                    cronometro.stop();
                    pausa = SystemClock.elapsedRealtime()-cronometro.getBase();
                    running = false;
                }
            }
        });
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        running = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(getActivity(), "Count sensor not available!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        running = false;
    }


    public void onSensorChanged(SensorEvent event) {
        if(running) {
            cont = (int) event.values[0];
            count.setText(String.valueOf(contador));
            contador++;
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}