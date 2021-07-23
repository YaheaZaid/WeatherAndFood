package com.example.weatherapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class EatFragment extends Fragment {
    Button add_Food;
    EditText etfood;
    RecyclerView recyclerView;
    ListView food_List;
    TextView resultw;
     DatabaseHelper databaseHelper;
    ArrayList list1;
    ArrayAdapter arrayAdapter1;
    ArrayList<MainModel> mainModels;
    MainAdapter mainAdapter;
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_eat,container,false);
      add_Food = v.findViewById(R.id.bt_add);
      etfood = v.findViewById(R.id.et_food);
      food_List = v.findViewById(R.id.list_view_food);
        resultw = v.findViewById(R.id.textres);


      getParentFragmentManager().setFragmentResultListener("datafromhome", this, new FragmentResultListener() {
          @Override
          public void onFragmentResult(@NonNull @NotNull String requestKey, @NonNull @NotNull Bundle result) {
              Double aDouble = result.getDouble("temp");
              resultw.setText("The Weather is "+aDouble);

          }
      });
      recyclerView = v.findViewById(R.id.recyclview_food);
      Integer [] langImg = {R.drawable.bred,R.drawable.rice,R.drawable.soap_veg,R.drawable.fish,R.drawable.meat};
      String[] langName = {"Bread","Rice","Soap","Fish","Meat"};
      mainModels = new ArrayList<>();
      for(int i=0;i<langImg.length;i++){
          MainModel model = new MainModel(langImg[i],langName[i]);
          mainModels.add(model);
      }
      LinearLayoutManager layoutManager = new LinearLayoutManager(getContext()
      ,LinearLayoutManager.HORIZONTAL,false);
      recyclerView.setLayoutManager(layoutManager);
      recyclerView.setItemAnimator(new DefaultItemAnimator());
      mainAdapter = new MainAdapter(getContext(),mainModels);
      recyclerView.setAdapter(mainAdapter);


        databaseHelper = new DatabaseHelper(getContext());

      list1 = databaseHelper.getFood();

        arrayAdapter1 = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1
      ,list1);
      food_List.setAdapter(arrayAdapter1);




        add_Food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtFood = etfood.getText().toString();

                if(!txtFood.isEmpty()){
                    if(databaseHelper.insertFood(txtFood)){
                        Toast.makeText(getContext(), "Data inserted.....", Toast.LENGTH_SHORT).show();
                        etfood.setText("");
                        list1.clear();
                        list1.addAll(databaseHelper.getFood());

                        arrayAdapter1.notifyDataSetChanged();

                        food_List.invalidateViews();
                        food_List.refreshDrawableState();

                    }

                }
            }
        });
        food_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item_Name=list1.get(position).toString();
                databaseHelper.deleteItem(item_Name,"table_food","food");
                Toast.makeText(getContext(), "You Delete "+list1.get(position), Toast.LENGTH_SHORT).show();

                list1.clear();


                list1.addAll(databaseHelper.getFood());

                arrayAdapter1.notifyDataSetChanged();

                food_List.invalidateViews();

                food_List.refreshDrawableState();
            }
        });
        return v;
    }
}
