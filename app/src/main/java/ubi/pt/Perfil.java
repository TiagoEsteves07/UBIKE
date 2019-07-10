package ubi.pt;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Perfil extends Fragment {

    Toolbar toolbar;

    private TextView tNome;
    private TextView tEmail;
    private TextView tData;
    private TextView tDistancia;
    private TextView tBicicleta;
    private TextView tPontos;

    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference;

    private String user_id ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.perfil, null);

        tNome = view.findViewById(R.id.tNome);
        tEmail = view.findViewById(R.id.tEmail);
        tData = view.findViewById(R.id.tData);
        tDistancia = view.findViewById(R.id.tDistancia);
        tBicicleta = view.findViewById(R.id.tBicicleta);
        tPontos = view.findViewById(R.id.tPontos);

        toolbar = view.findViewById(R.id.toolbar_perfil);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        storageReference = FirebaseStorage.getInstance().getReference();

        user_id = fAuth.getCurrentUser().getUid();

        CollectionReference pessoaRef = db.collection("pessoa");
        Query pessoaQuery = pessoaRef.whereEqualTo("user_id", user_id);

        pessoaQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                Pessoa pessoa = null;

                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        pessoa = doc.toObject(Pessoa.class);

                    }

                    String nome = pessoa.getNome();
                    String email = pessoa.getEmail();
                    String data = pessoa.getData();
                    String id = pessoa.getIdBicicleta();
                    String distancia = pessoa.getDistancia();
                    String pontos = pessoa.getPontos();

                    tNome.setText(nome);
                    tEmail.setText(email);
                    tData.setText(data);
                    tDistancia.setText(distancia);
                    tBicicleta.setText(id);
                    tPontos.setText(pontos);

                }
            }
        });
        return view;
    }



    /*
    public void carregarImagem(View v){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                Toast.makeText(activity,"Permission Denied",Toast.LENGTH_LONG).show();
                ((AppCompatActivity)getActivity()).requestPermissions( new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

            }else{

                Toast.makeText(activity,"You already have permission",Toast.LENGTH_LONG).show();


            }
        }


    }*/

    /*
    public void carregarImagem(View v){

        String user_id = fAuth.getCurrentUser().getUid();
        StorageReference img = storageReference.child("img_perfil").child(user_id+".jpg");

        img.putFile(imagem_perfil).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){

                    Uri uri_carregado = task.getResult().getUploadSessionUri();
                    Toast.makeText(getActivity(),"Imagem carregada com sucesso : ", Toast.LENGTH_LONG).show();

                }else{
                    String erro = task.getException().getMessage();
                    Toast.makeText(getActivity(),"Erro : " + erro, Toast.LENGTH_LONG).show();
                }
            }
        });

    }*/

}
