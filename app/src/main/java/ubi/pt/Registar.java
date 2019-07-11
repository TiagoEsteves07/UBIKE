package ubi.pt;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class Registar extends AppCompatActivity {

    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private EditText eNnome;
    private EditText eNemail;
    private EditText eNid;
    private EditText eNpassword;

    private DatePickerDialog.OnDateSetListener data;
    private TextView tData;

    private String nome;
    private String email;
    private String id;
    private String password;
    private String eNdata;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registar);

        eNnome = (EditText) findViewById(R.id.eNnome);
        eNemail = (EditText) findViewById(R.id.eNemail);
        eNid = (EditText) findViewById(R.id.eNid);
        eNpassword = (EditText) findViewById(R.id.eNpassword);
        tData = (TextView) findViewById(R.id.tData);




        data = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                String date = (dayOfMonth + "-" + month + "-" + year);
                tData.setText(date);
            }

        };
    }



    public void rConfirmar(View v) {


        final Map<String, Object> pessoa = new HashMap<>();

        nome = eNnome.getText().toString();
        email = eNemail.getText().toString();
        id = eNid.getText().toString();
        password = eNpassword.getText().toString();
        eNdata = tData.getText().toString();

        boolean bool = confirmarCampos(nome,email,eNdata,id,password);

        if (!bool){

            fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    String user_id = fAuth.getCurrentUser().getUid();
                    DocumentReference dr = db.collection("pessoa").document();

                    if (task.isSuccessful()) {
                        pessoa.put("user_id", user_id);
                        pessoa.put("nome", nome);
                        pessoa.put("email", email);
                        pessoa.put("ano", eNdata);
                        pessoa.put("idBicicleta", id);
                        pessoa.put("distancia", "0");
                        pessoa.put("pontos", "0");
                        pessoa.put("doc_id",dr.getId());


                        dr.set(pessoa)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful()){
                                            Toast.makeText(Registar.this,"Perfil Criado com Sucesso", Toast.LENGTH_SHORT).show();


                                        }else{
                                            String erro = task.getException().getMessage();
                                            Toast.makeText(Registar.this,"ERRO :" + erro, Toast.LENGTH_SHORT).show();

                                        }
                                    }

                                });

                        Intent iConfirmar = new Intent(Registar.this, Login.class);
                        startActivity(iConfirmar);
                        finish();

                    }else{

                        Toast.makeText(Registar.this, "O email já existe", Toast.LENGTH_LONG).show();
                    }
                }
            });


        }
    }

    public void calendario(View v){

        Calendar c = Calendar.getInstance();
        int ano = c.get(Calendar.YEAR);
        int mes = c.get(Calendar.MONTH);
        int dia = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                Registar.this,
                android.R.style.Theme_Holo_Dialog_MinWidth,
                data,
                ano,mes,dia);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private boolean confirmarCampos(String nome, String email, String data, String id, String password){

        if (nome.isEmpty()){
            eNnome.setError("Necessário preencher o nome");
            eNnome.requestFocus();
            return true;
        }
        if (!typeEmail(email)){
            eNemail.setError("Escreva um email válido");
            eNemail.requestFocus();
            return true;
        }
        if (email.isEmpty()){
            eNemail.setError("Necessário preencher o email");
            eNemail.requestFocus();
            return true;
        }
        if (data.isEmpty()){
            tData.setError("Necessário preencher a data de nascimento");
            tData.requestFocus();
            return true;
        }
        if (id.isEmpty()){
            eNid.setError("Necessário preencher o id da bicicleta");
            eNid.requestFocus();
            return true;
        }
        if (password.isEmpty()){
            eNpassword.setError("Necessário preencher a password");
            eNpassword.requestFocus();
            return true;
        }

        return false;
    }

    boolean typeEmail (CharSequence email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


}