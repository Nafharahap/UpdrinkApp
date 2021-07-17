package com.mobcom.updrinkapps.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mobcom.updrinkapps.R;
import com.mobcom.updrinkapps.adapters.MenuListAdapter;
import com.mobcom.updrinkapps.databinding.FragmentMenuBinding;
import com.mobcom.updrinkapps.models.Menu;
import com.mobcom.updrinkapps.viewmodels.MenuViewModel;

import java.util.List;

public class MenuFragment extends Fragment implements MenuListAdapter.MenuInterface {

    FragmentMenuBinding fragmentMenuBinding;
    private MenuListAdapter menuListAdapter;
    private MenuViewModel menuViewModel;
    private NavController navController;
    FloatingActionButton floatingActionButton;

    private static final String TAG = "MenuFragment";

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentMenuBinding = FragmentMenuBinding.inflate(inflater, container, false);
        return fragmentMenuBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        menuListAdapter = new MenuListAdapter(this);
        fragmentMenuBinding.menuRecylerView.setAdapter(menuListAdapter);
//        fragmentMenuBinding.menuRecylerView.addItemDecoration(new DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL));
//        fragmentMenuBinding.menuRecylerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL));

//        floatingActionButton = view.findViewById(R.id.profile_btn);
//        floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), ProfileActivity.class);
//                startActivity(intent);
//            }
//        });

        menuViewModel = new ViewModelProvider(requireActivity()).get(MenuViewModel.class);
        menuViewModel.getMenus().observe(getViewLifecycleOwner(), new Observer<List<Menu>>() {
            @Override
            public void onChanged(List<Menu> menuList) {
                menuListAdapter.submitList(menuList);
            }
        });

        navController = Navigation.findNavController(view);
    }

    @Override
    public void addItem(Menu menu) {
        boolean isAdded = menuViewModel.addItemToCart(menu);
        if (isAdded) {
            Snackbar.make(requireView(), menu.getName() + "Added To Cart", Snackbar.LENGTH_LONG)
                    .setAction("Checkout", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            navController.navigate(R.id.action_menuFragment_to_cartFragment);
                        }
                    }).show();
        } else {
            Snackbar.make(requireView(), "Max quantity added in cart", Snackbar.LENGTH_LONG).show();
        }

    }

    @Override
    public void onItemClick(Menu menu) {
        menuViewModel.setMenu(menu);
        navController.navigate(R.id.action_menuFragment_to_menuDetailFragment);
    }
}