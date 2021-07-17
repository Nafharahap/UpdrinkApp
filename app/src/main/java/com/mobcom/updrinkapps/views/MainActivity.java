package com.mobcom.updrinkapps.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.mobcom.updrinkapps.R;
import com.mobcom.updrinkapps.models.CartItem;
import com.mobcom.updrinkapps.models.UserData;
import com.mobcom.updrinkapps.retrofit.APIClient;
import com.mobcom.updrinkapps.retrofit.APIEndPoint;
import com.mobcom.updrinkapps.viewmodels.MenuViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    NavController navController;
    MenuViewModel menuViewModel;
    MainActivity mainActivity;

    APIEndPoint apiEndPoint;

    String personName, personEmail, personId, personPhoto;

    private int cartQuantity = 0;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiEndPoint = APIClient.getRetrofitInstance().create(APIEndPoint.class);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        personName = account.getDisplayName();
        personEmail = account.getEmail();
        personId = account.getId();
        if (account.getPhotoUrl() == null) {
            personPhoto = "";
        } else {
            personPhoto = account.getPhotoUrl().toString();
        }

        getUser(personId, personEmail);

        mainActivity = this;

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController);
        menuViewModel = new ViewModelProvider(this).get(MenuViewModel.class);
        menuViewModel.getCart().observe(this, new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> cartItems) {
                int quantity = 0;
                for (CartItem cartItem: cartItems) {
                    quantity += cartItem.getQuantity();
                }
                cartQuantity = quantity;
                invalidateOptionsMenu();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        navController.navigateUp();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        final MenuItem menuItem = menu.findItem(R.id.cartFragment);
        View actionView = menuItem.getActionView();

        MenuItem menuItem2 = menu.findItem(R.id.userActivity);
        View actionView2 = menuItem2.getActionView();

        MenuItem menuItem3 = menu.findItem(R.id.orderActivity);
        View actionView3 = menuItem3.getActionView();

        TextView cartNotif = actionView.findViewById(R.id.cart_notif);

        cartNotif.setText(String.valueOf(cartQuantity));
        cartNotif.setVisibility(cartQuantity == 0 ? View.GONE : View.VISIBLE);

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        actionView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivity, ProfileActivity.class);
                startActivity(intent);
            }
        });

        actionView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivity, OrderActivity.class);
                startActivity(intent);
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
    }

    private void getUser(String id_user, String user_email) {
        Call<UserData> call = apiEndPoint.getUser(id_user, user_email);
        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                if (response.body() == null){
                    postUser();
                } else {
                    Log.d(TAG, "onResponse: User already exist");
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void postUser() {
        Log.d(TAG, "postUser: " + personId + " " + personName + " " + personEmail + " " + personPhoto + " ");
        Call<UserData> call = apiEndPoint.postUser(personId, personName, personEmail, personPhoto);
        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                Log.d(TAG, "onResponse: User succesfully added");
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}