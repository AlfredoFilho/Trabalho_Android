package com.example.lahay.carrinho;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lahay.firstfrag.FirstFragment;
import com.example.lahay.MainActivity;
import com.example.lahay.R;
import com.example.lahay.comprar.Comprar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FecharCompra extends Fragment {

    View view;
    EditText editText;
    TextView txtTotal;
    ArrayList<Comprar> listaCompras;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String endereco;
    TextView numeroCasa;
    TextView textEndereco;
    String email;
    String precoT;

    public FecharCompra() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_fechar_compra, container, false);
        }

        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Finalizar Compra");

        Integer totalCarrinho = 0;

        editText = view.findViewById(R.id.editCEP);
        numeroCasa = view.findViewById(R.id.numeroCasa);
        textEndereco = view.findViewById(R.id.textEndereco);
        listaCompras = ((MainActivity)getActivity()).getListaCarrinho();
        txtTotal = view.findViewById(R.id.precoTotal);

        email = user.getEmail();

        listaCompras = ((MainActivity)getActivity()).getListaCarrinho();
        for (Comprar itemLista: listaCompras) {
            totalCarrinho = totalCarrinho + Integer.parseInt(itemLista.getPreco());
        }
        txtTotal.setText("R$ " + totalCarrinho);
        precoT = txtTotal.getText().toString();

        view.findViewById(R.id.btnBuscar).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new MyViaCepAsyncTask(FecharCompra.this).execute(editText.getText().toString());
                    }
                }
        );

        view.findViewById(R.id.btnComprar).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        endereco = endereco +  ", Número: " + numeroCasa.getText().toString() + ".";

                        enviarEmail();
                        openDialog();

                        Fragment firstFragment;
                        firstFragment = new FirstFragment();
                        ((MainActivity)getActivity()).replaceFragment(firstFragment, "first_fragment");
                        limpar();
                    }
                }
        );

        return view;
    }

    public void enviarEmail(){
        String conteudo = "Olá, " + user.getDisplayName() + "." +
                "\n\nEstamos enviando esse email para confirmar sua compra no valor de: " + precoT + "."
                + "\nSeu(s) veículo(s) vão chegar em: " + endereco;

        GmailSend send = new GmailSend(email, conteudo);
    }

    public void setEndereco(String enderecoAssync){
        textEndereco.setText(enderecoAssync);
        endereco = enderecoAssync;
    }

    public String getEndereco() {
        return endereco;
    }

    public void openDialog(){
        /**Fechar teclado*/
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        DialogCompra dialogCompra = new DialogCompra();
        dialogCompra.show(getFragmentManager(), "dialog compra");
    }

    public void limpar(){
        ((MainActivity)getActivity()).limparListaCarrinho();

    }

}
