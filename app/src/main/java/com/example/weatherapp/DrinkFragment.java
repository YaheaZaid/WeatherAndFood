package com.example.weatherapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DrinkFragment extends Fragment {
    Button add_Drink;
    EditText etdrink;
    TextView resultw;
    Double aDouble;
    Integer [] hotDrinkImg;
    String[] hotDrinkName;
    RecyclerView recyclerView;
    ArrayList<MainModel> mainModels;
    MainAdapter mainAdapter;
    ListView drink_List;
    DatabaseHelper databaseHelper;
    ArrayList list1;
    ArrayAdapter arrayAdapter1;
    View inflate;
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_drink, container, false);
        add_Drink = inflate.findViewById(R.id.bt_add_drink);
        etdrink = inflate.findViewById(R.id.et_drink);
        drink_List = inflate.findViewById(R.id.list_view_drink);
        resultw = inflate.findViewById(R.id.drinks_sugg);



        //HORIZONTAL recyclerView
        recyclerView = inflate.findViewById(R.id.recyclview_drink);
        hotDrinkImg = new Integer[]{R.drawable.soda, R.drawable.orange_juice, R.drawable.cold_water, R.drawable.cold_mint,R.drawable.coffe, R.drawable.hot_chocolate, R.drawable.tea, R.drawable.chabathino};
        hotDrinkName = new String[]{"Soda", "Orange Juice", "Cold Water", "Cold Mint","Coffee", "Hot Chocolate", "Tea", "Chabathino"};

        mainModels = new ArrayList<>();
        for(int i=0;i<hotDrinkImg.length;i++){
            MainModel model = new MainModel(hotDrinkImg[i],hotDrinkName[i]);
            mainModels.add(model);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext()
                ,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mainAdapter = new MainAdapter(getContext(),mainModels);
        recyclerView.setAdapter(mainAdapter);





        databaseHelper = new DatabaseHelper(getContext());

        list1 = databaseHelper.getDrink();

        arrayAdapter1 = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1
                ,list1);
        drink_List.setAdapter(arrayAdapter1);
        getParentFragmentManager().setFragmentResultListener("datafromhome", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull @NotNull String requestKey, @NonNull @NotNull Bundle result) {
                aDouble = result.getDouble("temp");
                resultw.setText("The Weather is "+aDouble);


            }
        });



        add_Drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtDrink = etdrink.getText().toString();

                if(!txtDrink.isEmpty()){
                    if(databaseHelper.insertDrink(txtDrink)){
                        Toast.makeText(getContext(), "Data inserted.....", Toast.LENGTH_SHORT).show();
                        etdrink.setText("");
                        list1.clear();
                        list1.addAll(databaseHelper.getDrink());

                        arrayAdapter1.notifyDataSetChanged();

                        drink_List.invalidateViews();
                        drink_List.refreshDrawableState();

                    }

                }
            }
        });
        drink_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item_Name=list1.get(position).toString();
                databaseHelper.deleteItem(item_Name,"table_drink","drink");
                Toast.makeText(getContext(), "You Delete"+list1.get(position), Toast.LENGTH_SHORT).show();

                list1.clear();


                list1.addAll(databaseHelper.getDrink());

                arrayAdapter1.notifyDataSetChanged();

                drink_List.invalidateViews();

                drink_List.refreshDrawableState();
            }
        });
        return inflate;
    }
}
