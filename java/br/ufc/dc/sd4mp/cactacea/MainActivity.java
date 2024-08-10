package br.ufc.dc.sd4mp.cactacea;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.view.menu.MenuBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Geocoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;


public class MainActivity extends ActionBarActivity {

    private static CityDAO cityDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) throws RuntimeException{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       try {
          cityDAO = new CityDAO(this);
       }
       catch (Exception e){
          Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
       }
        List<City> cities = cityDAO.list();
        if(cities == null || (cities!=null && cities.size()==0)) populate();
   /*   else{
            Iterator<City> it = cities.iterator();
            StringBuffer buffer = new StringBuffer();

            while (it.hasNext()) {
                //
                City city;
                city = it.next();
                buffer.append(city.toString());
                buffer.append("\n");
            }

            ((TextView)findViewById(R.id.cities_info)).setText(buffer); */
        //}
    }

    public void populate(){
        Log.v("SQLite", "create id = " + "teste");
        importFile file = new importFile("ceara.txt", this);

        if (file == null){
            Toast.makeText(this,"Arquivo nao encontrado.", Toast.LENGTH_LONG).show();
            return;
        }
        else Toast.makeText(this,"Arquivo encontrado.", Toast.LENGTH_LONG).show();

        Log.v("SQLite", "teste = " + file.getFullFile().get(1));
        /*limites do estado*/
        double lowerLeftLat = -7.48689;
        double lowerLeftLng = -40.49;
        double upperRightLat = -2.47;
        double upperRightLng = -37.348;

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> address = null;

        for ( String[] line : file.getFullFile() ){
            try {
                address = geocoder.getFromLocationName(line[0] + "," + line[1], 1, lowerLeftLat, lowerLeftLng, upperRightLat, upperRightLng);

                City city = new City();
                city.setNameCity(line[0]);
                city.setUF(line[1]);
                city.setPopulation(Integer.parseInt(line[2].replace(".", "").trim()));
                city.setRateHomicides(Double.parseDouble(line[8].replace(',', '.')));
                city.setNacionalPosition(Integer.parseInt(line[9].trim()));
                city.setLat(address.get(0).getLatitude());
                city.setLng(address.get(0).getLongitude());

                cityDAO.create(city);
            }catch (IOException e){
                Toast.makeText(this, "ApplyGeocoder exception: "+e.getCause()+", "+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
/*
        List<City> cities = cityDAO.list();

        Iterator<City> it = cities.iterator();
        StringBuffer buffer = new StringBuffer();

        while (it.hasNext()) {
            //
            City city;
            city = it.next();
            buffer.append(city.toString());
            buffer.append("\n");
        }

        ((TextView)findViewById(R.id.cities_info)).setText(buffer);
*/
    }

    @Override
     public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.getItem(0).setVisible(true);
        menu.getItem(1).setVisible(true);
        menu.getItem(2).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_list_all) {
            List<City> listAll = cityDAO.list();
            if (listAll != null && listAll.size()>0){
                ArrayList<String> listAllNames = new ArrayList<String>();
                Iterator<City> it = listAll.iterator();

                while (it.hasNext()){
                    City city;
                    city = it.next();
                    listAllNames.add(city.getNameCity());
                }

                Intent show_list_activity = new Intent(this, ShowListActivity.class); //COMPONENTE ESPECIFICADO
                show_list_activity.setAction(null);
                show_list_activity.setType(null);
                show_list_activity.setData(null);
                show_list_activity.putStringArrayListExtra("listOfCities",listAllNames);
                this.startActivity(show_list_activity);
            }
            return true;
        }

        if (id == R.id.action_sample) {
            //MapsActivity mapsActivity = new MapsActivity(true, true, 0.0, 100000.0, 50000, 10000000, 30);
            Intent map_activity = new Intent(this, MapsActivity.class); //COMPONENTE ESPECIFICADO
            map_activity.setAction(null);
            map_activity.setType(null);
            map_activity.setData(null);
            map_activity.putExtra("filterByRate", true);
            map_activity.putExtra("filterByPopulation", false);
            map_activity.putExtra("minRate", 5.0);
            map_activity.putExtra("maxRate", 100000.0);
            map_activity.putExtra("minPopulation", 0);
            map_activity.putExtra("maxPopulation", 10000000);
            map_activity.putExtra("maxResults", 184);
            map_activity.putExtra("searchByName", false);
            map_activity.putExtra("cityName", "");

            this.startActivity(map_activity);
            return true;
        }

        if (id == R.id.action_filters) {
            Intent filters_activity = new Intent(this, ApplyFilters.class); //COMPONENTE ESPECIFICADO
            filters_activity.setAction(null);
            filters_activity.setType(null);
            filters_activity.setData(null);
            this.startActivity(filters_activity);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void IrParaInformacoesUteis(View view){
        Intent info = new Intent(this, InformacaoUtil.class); //COMPONENTE ESPECIFICADO
        info.setAction(null);
        info.setType(null);
        info.setData(null);
        this.startActivity(info);
    }

    public static CityDAO getCityDAO() {
        return cityDAO;
    }
}
