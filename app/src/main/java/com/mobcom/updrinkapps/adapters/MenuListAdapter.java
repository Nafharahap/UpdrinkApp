package com.mobcom.updrinkapps.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.mobcom.updrinkapps.databinding.MenuRowBinding;
import com.mobcom.updrinkapps.models.Menu;

public class MenuListAdapter extends ListAdapter<Menu, MenuListAdapter.MenuViewHolder> {

    MenuInterface menuInterface;

    public MenuListAdapter(MenuInterface menuInterface) {
        super(Menu.itemCallback);
        this.menuInterface = menuInterface;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        MenuRowBinding menuRowBinding = MenuRowBinding.inflate(layoutInflater, parent, false);
        menuRowBinding.setMenuInterface(menuInterface);
        return new MenuViewHolder(menuRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuListAdapter.MenuViewHolder holder, int position) {
        Menu menu = getItem(position);
        holder.menuRowBinding.setMenu(menu);
        holder.menuRowBinding.executePendingBindings();
    }

    class MenuViewHolder extends RecyclerView.ViewHolder{

        MenuRowBinding menuRowBinding;

        public MenuViewHolder(MenuRowBinding binding) {
            super(binding.getRoot());
            this.menuRowBinding = binding;


        }
    }

    public interface MenuInterface{
        void addItem(Menu menu);
        void onItemClick(Menu menu);
    }
}
