package com.example.weatherapp;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

public class HomeFragment extends Fragment {
    private static final String API_KEY = "";
    FusedLocationProviderClient client;
    Button SearchBtn ,GetLocBtn;
    EditText CityName;
    TextView TvCity, TvTemp;
    ImageView IconWeather;
    View inflate;
    Spinner spinner;
    DatabaseHelper databaseHelper;
    ArrayList list1,list2;
    ListView listFood,listDrink;
    ArrayAdapter arrayAdapter1,arrayAdapter2;



    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override

    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_home, container, false);

        SearchBtn = inflate.findViewById(R.id.searchbtn);
        GetLocBtn = inflate.findViewById(R.id.getlocationbtn);
        CityName = inflate.findViewById(R.id.city_name);
        IconWeather = inflate.findViewById(R.id.weathericon);
        TvCity = inflate.findViewById(R.id.tvcity);
        spinner = inflate.findViewById(R.id.spinner_home);
        listFood = inflate.findViewById(R.id.list_view_home1);
        listDrink = inflate.findViewById(R.id.list_view_home2);
        databaseHelper = new DatabaseHelper(getContext());




        list1 = databaseHelper.getFood();
        list2 = databaseHelper.getDrink();
        arrayAdapter1 = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1
                ,list1);
        listFood.setAdapter(arrayAdapter1);
        arrayAdapter2 = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1
                ,list2);
        listDrink.setAdapter(arrayAdapter2);

        TvTemp = inflate.findViewById(R.id.tvtemp);
        String[] spinnerList = new String[]{"List Food","List Drink"};
        ArrayAdapter<String> adapter =new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item,spinnerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    listFood.setVisibility(View.VISIBLE);
                    listDrink.setVisibility(View.GONE);
                }
                if(position == 1){
                    listFood.setVisibility(View.GONE);
                    listDrink.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        GetLocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getContext()
                        , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getlocation();

                } else {

                    ActivityCompat.requestPermissions(getActivity()
                            , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }

            }
        });
        client = LocationServices.getFusedLocationProviderClient(getActivity());
        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String city = CityName.getText().toString();
                if (city.isEmpty())
                    Toast.makeText(getContext(), "Please Insert Country !!!", Toast.LENGTH_SHORT).show();
                else {
                    loadWeatherByCityName(city);
                }
            }
        });

        return inflate;
    }

    private void getlocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {

                    try {
                        Geocoder geocoder = new Geocoder(getContext()
                                , Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1);
                        CityName.setText(null);
                      CityName.setText((Html.fromHtml(
                                 addresses.get(0).getCountryName())));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    private void loadWeatherByCityName(String city) {
        Ion.with(this)
                .load("https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+API_KEY)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        JsonObject main = result.get("main").getAsJsonObject();
                        double temp = main.get("temp").getAsDouble();
                        double tempInc = Math.round(temp-273.15);
                        Bundle bundle = new Bundle();
                        bundle.putDouble("temp",tempInc);
                        getParentFragmentManager().setFragmentResult("datafromhome",bundle);
                        TvTemp.setText(tempInc+"°C");
                        JsonObject sys = result.get("sys").getAsJsonObject();
                        JsonArray weather = result.get("weather").getAsJsonArray();
                        String icon = weather.get(0).getAsJsonObject().get("icon").getAsString();
                        String country = sys.get("country").getAsString();

                        TvCity.setText(city+","+country);
                        loadicon(icon);




                    }
                });
    }
    private void loadicon(String icon) {
        Ion.with(this)
                .load("https://api.openweathermap.org/img/w/"+icon+".png")
                .intoImageView(IconWeather);
    }
}
