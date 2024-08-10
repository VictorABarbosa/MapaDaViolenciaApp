package br.ufc.dc.sd4mp.cactacea;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class CompareCities extends ActionBarActivity {

    private CityDAO cityDAO;
    private List<String> listCitiesToCompare;
    private ListView list_city_to_compare;

    @Override
    protected void onCreate(Bundle savedInstanceState) throws RuntimeException {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_compare_cities);
            cityDAO = MainActivity.getCityDAO();
            listCitiesToCompare = new ArrayList<String>();
            //list_city_to_compare = null;


            Intent intent = getIntent();
            String regionClicked = intent.getStringExtra("chosenCity");


            City city = cityDAO.queryName(regionClicked);
            if (city != null) {
            /*  String regionName = city.getNameCity();
                String regionUF = city.getUF();
                String regionHomicides = "Taxa de homicídios  " + city.getRateHomicides();

                String regionIfEndemicRate = "";
                if (city.getRateHomicides() >= 10.0)
                    regionIfEndemicRate = "Endêmica";
                else
                    regionIfEndemicRate = "Não-endêmica";

                String regionPosition = "Posição em todo Brasil  " + city.getNacionalPosition();
                String regionPopulation = "População  " + city.getPopulation();
            */
                Button add_compare_to = (Button) findViewById(R.id.add_compare_to);
                add_compare_to.setText("Comparar " + city.getNameCity() + " a...");

                TextView region_name = (TextView) findViewById(R.id.region_name);
                region_name.setText(city.getNameCity());

                //TextView region_uf = (TextView) findViewById(R.id.region_uf);
                //region_uf.setText("("+regionUF+")");

                TextView region_homicides = (TextView) findViewById(R.id.region_homicides);
                region_homicides.setText("" + city.getRateHomicides());

                //TextView region_if_endemic_rate = (TextView) findViewById(R.id.region_if_endemic_rate);
                //region_if_endemic_rate.setText(regionIfEndemicRate);
                //if (city.getRateHomicides() >= 10.0) region_if_endemic_rate.setTextColor(Color.rgb(230,0,0));
                //else region_if_endemic_rate.setTextColor(Color.rgb(102,153,0));

                TextView region_population = (TextView) findViewById(R.id.region_population);
                region_population.setText("" + city.getPopulation());

                TextView region_position = (TextView) findViewById(R.id.region_national_position);
                region_position.setText("" + city.getNacionalPosition());
            }
        }
        catch(Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void activityAnterior(View view){
        finish();
    }

    public void addCityToCompare(View view) throws RuntimeException{
        try {
            EditText city_to_compare = (EditText) findViewById(R.id.city_to_compare);
            //TextView city_to_compare_info = (TextView)findViewById(R.id.city_to_compare_info);

            if (city_to_compare != null && !city_to_compare.getText().toString().isEmpty()) {
                City city = cityDAO.queryName(city_to_compare.getText().toString());

                //if (listCitiesToCompare == null) {
                //    listCitiesToCompare = new ArrayList<City>();
                //}
                //city_to_compare_info.setText(city.toString());
                if (city != null) {
                    TextView region_name = (TextView) findViewById(R.id.region_name_to_compare);
                    region_name.setText(city.getNameCity());

                    //TextView region_uf = (TextView) findViewById(R.id.region_uf);
                    //region_uf.setText("("+regionUF+")");

                    TextView region_homicides = (TextView) findViewById(R.id.region_homicides_to_compare);
                    region_homicides.setText("" + city.getRateHomicides());

                    //TextView region_if_endemic_rate = (TextView) findViewById(R.id.region_if_endemic_rate);
                    //region_if_endemic_rate.setText(regionIfEndemicRate);
                    //if (city.getRateHomicides() >= 10.0) region_if_endemic_rate.setTextColor(Color.rgb(230,0,0));
                    //else region_if_endemic_rate.setTextColor(Color.rgb(102,153,0));

                    TextView region_population = (TextView) findViewById(R.id.region_population_to_compare);
                    region_population.setText("" + city.getPopulation());

                    TextView region_position = (TextView) findViewById(R.id.region_national_position_to_compare);
                    region_position.setText("" + city.getNacionalPosition());
                /*
                try {
                    ListAdapter adapter = new ArrayAdapter(this, R.layout.city_homicides_info, R.id.region_info, listCitiesToCompare);
                    //adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                    //list_city_to_compare.removeAllViews();
                    list_city_to_compare.setAdapter(adapter);
                }
                catch (Exception e){
                    Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
                }
                //list_city_to_compare.setChoiceMode(ListView.CHOICE_MODE_NONE);
                //myList.setItemChecked(0, true);
        //        int var=10;
                list_city_to_compare.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                list_city_to_compare.setOnItemClickListener(null);
                Toast.makeText(this,Integer.toString(list_city_to_compare.getCount()),Toast.LENGTH_LONG).show();
        */
                    //View nextCityToCompare = getLayoutInflater().inflate(R.layout.city_homicides_info, (ViewGroup)view);
                    //((TextView)nextCityToCompare.findViewById(R.id.region_name_and_rate)).setText(city.getNameCity());
                    //((TextView)nextCityToCompare.findViewById(R.id.region_population)).setText(Integer.toString(city.getPopulation()));
                    //((TextView)nextCityToCompare.findViewById(R.id.region_uf)).setText(city.getUF());
                    //((TextView)nextCityToCompare.findViewById(R.id.region_homicides)).setText((Double.toString(city.getRateHomicides())));
                    //if (city.getRateHomicides() >= 10.0)
                    //    ((TextView)nextCityToCompare.findViewById(R.id.region_if_endemic_rate)).setText("ENDÊMICO");
                    //else
                    //    ((TextView)nextCityToCompare.findViewById(R.id.region_if_endemic_rate)).setText("NÃO-ENDÊMICO");


                    //ArrayAdapter adapter = new ArrayAdapter(this, R.layout.city_homicides_info, R.id.region_name, listCitiesToCompare);
                    //adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                    //list_city_to_compare.setAdapter(adapter);
                    //list_city_to_compare.addFooterView(nextCityToCompare);
                    // myList.setCacheColorHint(Color.TRANSPARENT);
                    //myList.setItemChecked(0, true);
                    //int var=10;
                    //list_city_to_compare.setOnItemClickListener(null);
                    //view.findViewById(R.id.city_to_compare).requestFocus();
                }
            }
        }
        catch(Exception e){
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_compare_cities, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
