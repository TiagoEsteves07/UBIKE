package ubi.pt;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private EditText eUtilizador;
    private EditText ePassword;

    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        fAuth = FirebaseAuth.getInstance();

        eUtilizador = (EditText) findViewById(R.id.eUserName);
        ePassword = (EditText) findViewById(R.id.ePassword);
    }



    public void confirmar(View v){

        String loginEmail = eUtilizador.getText().toString();
        String loginPassword = ePassword.getText().toString();

        if(!TextUtils.isEmpty(loginEmail) && !TextUtils.isEmpty(loginPassword)) {

            fAuth.signInWithEmailAndPassword(loginEmail, loginPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        Intent iConfirmar = new Intent(Login.this, MainActivity.class);
                        startActivity(iConfirmar);
                        finish();

                    } else {

                        String error = task.getException().getMessage();
                        Toast.makeText(Login.this, "Error: " + error, Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }


    public void registar(View v){

        Intent iRegistar = new Intent(this,Registar.class);
        startActivity(iRegistar);
        finish();

    }

}