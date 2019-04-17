package ubi.pt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    public void confirmar(View v){
        Intent iConfirmar = new Intent(this,MainActivity.class);
        startActivity(iConfirmar);
        finish();

    }
}