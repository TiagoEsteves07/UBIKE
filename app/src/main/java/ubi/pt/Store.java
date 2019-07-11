package ubi.pt;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Store extends Fragment{

    Toolbar toolbar;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private RecyclerView recyclerView;

    private FirestoreRecyclerAdapter<Produto, ProdutoViewHolder> adapterP;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.store,null);



        toolbar = view.findViewById(R.id.toolbar_loja);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        RecyclerView recyclerView = view.findViewById(R.id.produtos_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        Query query = db.collection("produto");


        FirestoreRecyclerOptions<Produto> option = new FirestoreRecyclerOptions.Builder<Produto>()
                .setQuery(query, Produto.class)
                .build();



        adapterP = new FirestoreRecyclerAdapter<Produto, ProdutoViewHolder>(option) {
            @Override
            protected void onBindViewHolder(@NonNull ProdutoViewHolder produtoViewHolder, int i, @NonNull Produto produto) {
                produtoViewHolder.setProduto(produto.nome,produto.pontos);
            }

            @NonNull
            @Override
            public ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_produtos,parent,false);

                return new ProdutoViewHolder(view);
            }
        };
        recyclerView.setAdapter(adapterP);

        return view;
    }



    @Override
    public void onStart() {
        super.onStart();
        adapterP.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterP.stopListening();
    }

    private class ProdutoViewHolder extends RecyclerView.ViewHolder{
        private View view;

        public ProdutoViewHolder(@NonNull View itemView) {
            super(itemView);

            view = itemView;
        }

        void setProduto(String nomeProduto, String pontosProduto){
            TextView tnome = view.findViewById(R.id.nomeP);
            TextView tpontos = view.findViewById(R.id.pontosP);

            tnome.setText(nomeProduto);
            tpontos.setText(pontosProduto);
        }
    }

}