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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Achivements extends Fragment {

    Toolbar toolbar;
    private FirestoreRecyclerAdapter<Objetivo, ObjetivoViewHolder> adapterA;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.achivements,null);

        final TextView tdescricao = view.findViewById(R.id.descricaoA);
        final TextView tpontos = view.findViewById(R.id.pontosA);

        toolbar = view.findViewById(R.id.toolbar_achivements);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerA);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseFirestore db = FirebaseFirestore.getInstance();
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