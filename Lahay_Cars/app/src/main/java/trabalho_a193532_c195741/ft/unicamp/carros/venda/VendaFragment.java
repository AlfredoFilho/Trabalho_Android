package trabalho_a193532_c195741.ft.unicamp.carros.venda;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import trabalho_a193532_c195741.ft.unicamp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class VendaFragment extends Fragment {

    private Button cancelarBtnVenda;
    private View view;

    public VendaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

     if (view == null){
         view = inflater.inflate(R.layout.fragment_venda, container, false);

         cancelarBtnVenda = (Button) view.findViewById(R.id.CancelarVenda);

         cancelarBtnVenda.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                getActivity().onBackPressed();
             }
         });
     }
        return view;
    }

}
