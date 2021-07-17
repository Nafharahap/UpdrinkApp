package com.mobcom.updrinkapps.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.mobcom.updrinkapps.R;
import com.mobcom.updrinkapps.adapters.CartListAdapter;
import com.mobcom.updrinkapps.databinding.CartRowBinding;
import com.mobcom.updrinkapps.databinding.FragmentCartBinding;
import com.mobcom.updrinkapps.databinding.FragmentMenuDetailBinding;
import com.mobcom.updrinkapps.models.CartItem;
import com.mobcom.updrinkapps.models.OrderData;
import com.mobcom.updrinkapps.models.UserData;
import com.mobcom.updrinkapps.retrofit.APIClient;
import com.mobcom.updrinkapps.retrofit.APIEndPoint;
import com.mobcom.updrinkapps.viewmodels.MenuViewModel;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment implements CartListAdapter.CartInterface {

    private static final String TAG = "CartFragment";
    MenuViewModel menuViewModel;
    FragmentCartBinding fragmentCartBinding;
    NavController navController;

    String index;

    APIEndPoint apiEndPoint;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentCartBinding = FragmentCartBinding.inflate(inflater, container, false);
        return fragmentCartBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        apiEndPoint = APIClient.getRetrofitInstance().create(APIEndPoint.class);

        CartListAdapter cartListAdapter = new CartListAdapter(this);
        fragmentCartBinding.cartRecyclerView.setAdapter(cartListAdapter);
        fragmentCartBinding.cartRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        menuViewModel = new ViewModelProvider(requireActivity()).get(MenuViewModel.class);
        menuViewModel.getCart().observe(getViewLifecycleOwner(), new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> cartItems) {
                cartListAdapter.submitList(cartItems);
                index = Integer.toString(cartItems.size());
                fragmentCartBinding.placeOrderButton.setEnabled(cartItems.size() > 0);
            }
        });

        menuViewModel.getTotalPrice().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer aInteger) {
                fragmentCartBinding.orderTotalTextView.setText("Total: IDR " + aInteger.toString());
            }
        });

        fragmentCartBinding.placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());

                String personName = account.getDisplayName();
                String personId = account.getId();
                String totalOrder = menuViewModel.getTotalPrice().getValue().toString();
                Date date = new Date();
                long stringdate = date.getTime();
                String transactionCode = stringdate + "-" + index;
                postOrder(personId, personName, totalOrder, transactionCode, 1);
                navController.navigate(R.id.action_cartFragment_to_orderFragment);
            }
        });
    }

    private void postOrder(String id_user, String full_name, String total, String transaction_code, int status_order) {
        Call<OrderData> call = apiEndPoint.postOrder(id_user, full_name, total, transaction_code, status_order);
        call.enqueue(new Callback<OrderData>() {
            @Override
            public void onResponse(Call<OrderData> call, Response<OrderData> response) {
                if (!response.isSuccessful()){
                    Log.d(TAG, "onResponse: " + response.code() + " " + response.message());
                }
                Log.d(TAG, "onResponse: Post success");
            }

            @Override
            public void onFailure(Call<OrderData> call, Throwable t) {
                Log.d(TAG, "onFailure: Post failed");
            }
        });

    }

    @Override
    public void deleteItem(CartItem cartItem) {
        menuViewModel.removeItemFromCart(cartItem);
    }

    @Override
    public void changeQuantity(CartItem cartItem, int quantity) {
        menuViewModel.changeQuantity(cartItem, quantity);
    }
}