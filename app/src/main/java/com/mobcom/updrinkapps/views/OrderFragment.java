package com.mobcom.updrinkapps.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mobcom.updrinkapps.R;
import com.mobcom.updrinkapps.databinding.FragmentCartBinding;
import com.mobcom.updrinkapps.databinding.FragmentOrderBinding;
import com.mobcom.updrinkapps.viewmodels.MenuViewModel;

public class OrderFragment extends Fragment {

    NavController navController;
    FragmentOrderBinding fragmentOrderBinding;
    MenuViewModel menuViewModel;
    Button btnToDana, btnToWa;

    private static final String TAG = "OrderFragment";

    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_order, container, false);
        fragmentOrderBinding = FragmentOrderBinding.inflate(inflater, container, false);
        return fragmentOrderBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        menuViewModel = new ViewModelProvider(requireActivity()).get(MenuViewModel.class);
        btnToDana = view.findViewById(R.id.btnToDana);
        btnToWa = view.findViewById(R.id.btnToWa);

        btnToDana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: BtnToDana already clicked");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://link.dana.id/qr/2us71m9b"));
                startActivity(intent);
            }
        });

        btnToWa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Already clicked");
                String message = "Send payment proof to here";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("https://api.whatsapp.com/send?phone=%s&text=%s", "6287780916623", message)));
                intent.setPackage("com.whatsapp");
                startActivity(intent);
            }
        });

        fragmentOrderBinding.continueShopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuViewModel.resetCart();
                navController.navigate(R.id.action_orderFragment_to_menuFragment);
            }
        });
    }
}