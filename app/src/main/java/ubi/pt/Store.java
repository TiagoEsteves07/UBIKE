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

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Store extends Fragment {

    Toolbar toolbar;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private List<Produto> produto_lista;
    private RecyclerView produto_listView;
    private ProdutoRecyclerAdapter produtoRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.store,container,false);

        toolbar = view.findViewById(R.id.toolbar_loja);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        produto_listView = (RecyclerView) view.findViewById(R.id.produtos_list);
        produto_listView.setLayoutManager(new LinearLayoutManager(getContext()));




        db.collection("prduto").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {

                for(DocumentChange doc: queryDocumentSnapshots.getDocumentChanges()){

                    if(doc.getType() == DocumentChange.Type.ADDED){
                        Produto produto = doc.getDocument().toObject(Produto.class);
                        produto_lista.add(produto);

                        produtoRecyclerAdapter.notifyDataSetChanged();

                    }

                }

            }
        });

        return view;
    }

}