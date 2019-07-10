package ubi.pt;

import android.os.Bundle;

import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Fragment;
public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener{




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        getFragmentManager().beginTransaction().replace(R.id.frame_container, new Perfil()).commit();

    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){


        Fragment fragment = null;

        switch (item.getItemId()){
            case R.id.perfil:
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new Perfil()).commit();
                break;

            case R.id.store:
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new Store()).commit();
                break;

            case R.id.map:
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new Map()).commit();
                break;

            case R.id.achiv:
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new Achivements()).commit();
                break;

            case R.id.def:
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new ContarPassos()).commit();
                break;
        }
        return true;
    }


}
