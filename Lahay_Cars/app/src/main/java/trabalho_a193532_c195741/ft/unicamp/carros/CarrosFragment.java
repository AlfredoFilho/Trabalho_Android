package trabalho_a193532_c195741.ft.unicamp.carros;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import trabalho_a193532_c195741.ft.unicamp.MainActivity;
import trabalho_a193532_c195741.ft.unicamp.R;


public class CarrosFragment extends Fragment {
    View view;
    RecyclerView mRecyclerView;
    MyAdapter mAdapter;
    AbrirDetalheCarro abrirDetalheCarro;

    public CarrosFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_carros, container, false);
        }

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new MyAdapter(new ArrayList(Arrays.asList(Carros.carros)), this.getActivity());
        mAdapter.setMyOnItemClickListener(
                new MyAdapter.MyOnItemClickListener() {
                    @Override
                    public void mostrarNomeCarro(String nome) {
                        Toast.makeText(getActivity(), nome, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void enviarPosicaoCarro(int index) {
                        if (abrirDetalheCarro != null) {
                            abrirDetalheCarro.abrirDetalheCarro(index);
                        }
                    }
                }
        );
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }
    public void setAbrirDetalheCarro(AbrirDetalheCarro abrirDetalheCarro) {
        this.abrirDetalheCarro = abrirDetalheCarro;
    }
}
