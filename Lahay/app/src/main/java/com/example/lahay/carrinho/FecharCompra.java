package com.example.lahay.carrinho;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.text.HtmlCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lahay.FirstFragment;
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
    TextView precoTotal;
    ArrayList<Comprar> listaCarrinho;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String endereco;
    TextView numeroCasa;
    String precoT;
    String email;

    public FecharCompra() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_fechar_compra, container, false);
        }

        editText = view.findViewById(R.id.editCEP);
        numeroCasa = view.findViewById(R.id.numeroCasa);
        listaCarrinho = ((MainActivity)getActivity()).getListaCarrinho();
        precoTotal = view.findViewById(R.id.precoTotal);
        precoT = precoTotal.getText().toString();
        email = user.getEmail();

        view.findViewById(R.id.btnComprar).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new MyViaCepAsyncTask(FecharCompra.this).execute(editText.getText().toString());

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
        String conteudo = "Olá " + user.getDisplayName() + "." +
                "\n\nEstamos enviando esse email para confirmar sua compra no valor de: " + precoT + "."
                + "\nSeu(s) veículo(s) vão chegar em: " + getEndereco();

        GmailSend send = new GmailSend(email, conteudo);
    }

    public void setEndereco(String enderecoAssync){
        this.endereco = enderecoAssync + ", Número: " + numeroCasa.getText().toString() + ".";
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
