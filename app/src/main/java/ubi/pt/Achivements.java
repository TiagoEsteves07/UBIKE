package ubi.pt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Fragment;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Achivements extends Fragment {

    Toolbar toolbar;
    private FirestoreRecyclerAdapter<Objetivo, ObjetivoViewHolder> adapterA;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();

    private String user_id;
    private float fDistancia ;
    private float fDescricao ;
    private Objetivo objetivo;
    private Pessoa pessoa;
    private String doc_id;
    private String pontos;
    private String distancia;
    private String descricao;
    private String pontosA;
    private int total;
    private ArrayList arrayDescricao = new ArrayList();
    private ArrayList<Integer> arrayPontos = new ArrayList();

    private TextView tpontos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.achivements,null);


        toolbar = view.findViewById(R.id.toolbar_achivements);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        guardarObjetivos();

        RecyclerView recyclerView = view.findViewById(R.id.recyclerA);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        CollectionReference Aref = db.collection("achivements");
        Query query = Aref.orderBy("id");

        FirestoreRecyclerOptions<Objetivo> options = new FirestoreRecyclerOptions.Builder<Objetivo>()
                .setQuery(query,Objetivo.class)
                .build();

        adapterA = new FirestoreRecyclerAdapter<Objetivo, ObjetivoViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ObjetivoViewHolder objetivoViewHolder, int i, @NonNull Objetivo objetivo) {
                objetivoViewHolder.setObjetivo(objetivo.descricao,objetivo.pontos);
            }

            @NonNull
            @Override
            public ObjetivoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_achievements,parent,false);
                return new ObjetivoViewHolder(view);
            }
        };


        recyclerView.setAdapter(adapterA);




        return view;

    }



    //Aguardar os objetivos no array
    public void guardarObjetivos(){


        CollectionReference achRef = db.collection("achivements");
        Query achQuery = achRef.orderBy("id", Query.Direction.DESCENDING);
        achQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot doc: task.getResult()){
                        objetivo = doc.toObject(Objetivo.class);
                        descricao = objetivo.getDescricao();
                        pontosA = objetivo.getPontos();

                        arrayDescricao.add(Float.valueOf(descricao).floatValue());
                        arrayPontos.add(Integer.valueOf(pontosA));



                    }
                }
            }
        });

        user_id = fAuth.getCurrentUser().getUid();
        System.out.println(user_id);

        CollectionReference cr = db.collection("pessoa");
        Query query = cr.whereEqualTo("user_id",user_id);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot doc: task.getResult()){
                        pessoa = doc.toObject(Pessoa.class);

                        doc_id = pessoa.getDoc_id();
                        pontos = pessoa.getPontos();
                        distancia = pessoa.getDistancia();

                        for(int i =0;i<arrayDescricao.size();i++){
                            fDistancia = Float.valueOf(distancia).floatValue();
                            fDescricao = (float) arrayDescricao.get(i);


                            if(Float.compare(fDistancia,fDescricao) > 0 ){
                                total = total + arrayPontos.get(i);
                            }
                        }

                        tpontos = getActivity().findViewById(R.id.achivPontos);
                        tpontos.setText(Integer.toString(total));
                    }
                    db.collection("pessoa").document(doc_id).update("pontos", Integer.toString(total) );
                }




            }
        });


    }


    private class ObjetivoViewHolder extends RecyclerView.ViewHolder{
        private View view;

        public ObjetivoViewHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView;
        }

        void setObjetivo(String descricao, String pontos){
            TextView tdescricao = view.findViewById(R.id.descricaoA);
            TextView tpontos = view.findViewById(R.id.pontosA);

            tdescricao.setText(descricao);
            tpontos.setText(pontos);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapterA.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();

        if (adapterA != null) {
            adapterA.stopListening();
        }
    }

}