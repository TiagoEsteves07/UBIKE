package ubi.pt;

import android.app.Fragment;
import android.content.Context;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class ContarPassos extends Fragment implements SensorEventListener {

    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String user_id;

    private SensorManager sensorManager;
    private TextView count;
    boolean running;

    Toolbar toolbar;

    private Button bIniciar;
    private Button bTerminar;
    private Chronometer cronometro;
    private long pausa;


    int aux =0;

    private int cont;
    private int numeroFeito;
    private int contador;

    float metros = 0;
    float totalP=0;

    private String doc_id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.contar_passos,null);

        aux=0;
        bIniciar = (Button) view.findViewById(R.id.bIniciar);
        bTerminar = (Button) view.findViewById(R.id.bTerminar);

        toolbar = view.findViewById(R.id.toolbar_passos);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        count = (TextView) view.findViewById(R.id.passos);
        sensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);

        cronometro = view.findViewById(R.id.cronometro);



        user_id=fAuth.getCurrentUser().getUid();


        //Contar Passos
        contador =0;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(getActivity(), "Count sensor not available!", Toast.LENGTH_LONG).show();
        }

        bIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aux =1;
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
                aux =0;
                if(running){
                    cronometro.stop();
                    pausa = SystemClock.elapsedRealtime()-cronometro.getBase();
                    running = false;

                    metros = (float) (contador*0.61);

                    setPontosDB();

                }

            }

        });




        return view;
    }

    public void setPontosDB(){

        //Busacar os dados do utilizador
        CollectionReference pessoaRef = db.collection("pessoa");
        Query pessoaQuery = pessoaRef.whereEqualTo("user_id", user_id);
        pessoaQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Pessoa pessoa = new Pessoa();

                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot doc: task.getResult()){
                        pessoa = doc.toObject(Pessoa.class);
                    }

                    String pontos = pessoa.getDistancia();
                    doc_id = pessoa.getDoc_id();

                    totalP = Float.parseFloat(pontos) + metros;
                    System.out.println(pontos);
                    System.out.println(metros);
                    System.out.println(totalP);

                    Toast.makeText(getActivity(),"Fez "+ metros +"metros" ,Toast.LENGTH_LONG).show();

                    //set dos pontos
                    db.collection("pessoa").document(doc_id)
                            .update(
                                    "distancia", Float.toString(totalP)
                            );

                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        if(aux==1) {
            running = true;
            Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            if (countSensor != null) {
                sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
            } else {
                Toast.makeText(getActivity(), "Count sensor not available!", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        pausa = SystemClock.elapsedRealtime()-cronometro.getBase();
        running = true;
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