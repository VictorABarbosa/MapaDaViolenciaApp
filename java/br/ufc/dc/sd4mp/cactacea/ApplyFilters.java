package br.ufc.dc.sd4mp.cactacea;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Spinner;

public class ApplyFilters extends ActionBarActivity implements AdapterView.OnItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_filters);

        Spinner rate_spinner = (Spinner) findViewById(R.id.rate_restriction);
        String[] rateList = getResources().getStringArray(R.array.rate_homicides_restriction);
        ArrayAdapter<String> rateAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, rateList);
        rateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rate_spinner.setAdapter(rateAdapter);
        rate_spinner.setOnItemSelectedListener(this);
        rate_spinner.setSelection(0);

        Spinner population_spinner = (Spinner) findViewById(R.id.population_restriction);
        String[] populationList = getResources().getStringArray(R.array.population_restriction_array);
        ArrayAdapter<String> populationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, populationList);
        populationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        population_spinner.setAdapter(populationAdapter);
        population_spinner.setOnItemSelectedListener(this);
        population_spinner.setSelection(0);

        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.max_result);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(183);
    }

    public void activityBack(View view){
        finish();
    }

    public void GenerateCustomMap(View view){
        RadioButton filter_by_rate = (RadioButton) findViewById(R.id.filter_by_rate);
        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.max_result);
        CheckBox filter_by_population = (CheckBox) findViewById(R.id.filter_by_population);

        RadioButton search_by_name = (RadioButton) findViewById(R.id.search_by_name);
        EditText city_name = (EditText) findViewById(R.id.city_name);
        String name = "";

        double minRateHomicide = 10;
        double maxRateHomicide = 100000;
        int minPopulation = 100000;
        int maxPopulation = 10000000;
        int maxResults = 184;
        boolean ready_to_send_intent = false;

        if (numberPicker != null) maxResults = numberPicker.getValue();

        if (filter_by_rate != null && filter_by_rate.isChecked()) {

            Spinner rate_restriction = (Spinner) findViewById(R.id.rate_restriction);
            int selected_rate_restriction = rate_restriction.getSelectedItemPosition();
            if (selected_rate_restriction == 0) {//.toString().equals("menor que 10")){
                minRateHomicide = 10;
                maxRateHomicide = 100000;
            }
            else if (selected_rate_restriction == 1) {//.toString().equals("maior ou igual a 10")){
                minRateHomicide = 10;
                maxRateHomicide = 19.9;
            }
            else if (selected_rate_restriction == 2) {//.toString().equals("maior ou igual a 10 e menor que 30")){
                minRateHomicide = 20;
                maxRateHomicide = 100000;
            }
            else if (selected_rate_restriction == 3) {//.toString().equals("maior ou igual a 30")){
                minRateHomicide = 0;
                maxRateHomicide = 9.9;
            }
            else if (selected_rate_restriction == 4) {//.toString().equals("maior ou igual a 50")){
                minRateHomicide = 5;
                maxRateHomicide = 9.9;
            }
            else if (selected_rate_restriction == 5) {//.toString().equals("maior ou igual a 50")){
                minRateHomicide = 0.1;
                maxRateHomicide = 4.9;
            }
            else if (selected_rate_restriction == 6) {//.toString().equals("maior ou igual a 50")){
                minRateHomicide = 0;
                maxRateHomicide = 0;
            }

            if (filter_by_population != null && filter_by_population.isChecked()) {

                Spinner population_restriction = (Spinner) findViewById(R.id.population_restriction);
                int selected_population_restriction = population_restriction.getSelectedItemPosition();
                if (selected_population_restriction == 0){
                    minPopulation = 100000;
                    maxPopulation = 10000000;
                }
                else if (selected_population_restriction == 1){
                    minPopulation = 0;
                    maxPopulation = 99999;
                }
                else if (selected_population_restriction == 2){
                    minPopulation = 20000;
                    maxPopulation = 99999;
                }
                else if (selected_population_restriction == 3){
                    minPopulation = 0;
                    maxPopulation = 19999;
                }
            }
            ready_to_send_intent = true;
        }

        else if (search_by_name != null && search_by_name.isChecked()){
            if (city_name != null){
                if (city_name.getText().toString().isEmpty()==false){
                    name = city_name.getText().toString();
                    ready_to_send_intent = true;
                }
            }
        }

        if(ready_to_send_intent == true){

            Intent map_activity = new Intent(this, MapsActivity.class); //COMPONENTE ESPECIFICADO
            map_activity.setAction(null);
            map_activity.setType(null);
            map_activity.setData(null);
            map_activity.putExtra("filterByRate", filter_by_rate.isChecked());
            map_activity.putExtra("filterByPopulation", filter_by_population.isChecked());
            map_activity.putExtra("minRate", minRateHomicide);
            map_activity.putExtra("maxRate", maxRateHomicide);
            map_activity.putExtra("minPopulation", minPopulation);
            map_activity.putExtra("maxPopulation", maxPopulation);
            map_activity.putExtra("maxResults", maxResults);
            map_activity.putExtra("searchByName", search_by_name.isChecked());
            map_activity.putExtra("cityName", name);
            this.startActivity(map_activity);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_apply_filters, menu);
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
