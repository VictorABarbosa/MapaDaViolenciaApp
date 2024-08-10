package br.ufc.dc.sd4mp.cactacea;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ShowListActivity extends ActionBarActivity implements AdapterView.OnItemClickListener{

    private CityDAO cityDAO;
    private List<String> listOfCities;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);
        cityDAO = MainActivity.getCityDAO();
        listView = (ListView) findViewById(R.id.listOfCities);

        listOfCities = getIntent().getStringArrayListExtra("listOfCities");

        List<String> ListItem = new ArrayList<String>();
        City city = null;
        Iterator<String> it = listOfCities.iterator();
        int pos = 1;
        while (it.hasNext()) {
            city = cityDAO.queryName(it.next().toString());
            //ToDoItem item = new ToDoItem(toDo, 0);//, R.id.ic_edit_button, R.id.ic_apagar_button);
            ListItem.add(pos + "  " + city);
            pos++;
        }

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.city_homicides_info, R.id.region_info, ListItem);
        //adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        listView.setAdapter(adapter);

        // myList.setCacheColorHint(Color.TRANSPARENT);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        //myList.setItemChecked(0, true);
        listView.setOnItemClickListener(this);

    }

    public void activityAnterior(View view){
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) throws RuntimeException{

        try {
            String item = (String)parent.getItemAtPosition(position);
            String[] splitItems = item.split("  ");
            String cityName = splitItems[1];

            Intent explicita = new Intent(this, GetRegion.class); //COMPONENTE ESPECIFICADO
            explicita.setAction(null);
            explicita.setType(null);
            explicita.setData(null);
            explicita.putExtra("chosenCity", cityName);
            this.startActivity(explicita);
        }
        catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_list, menu);
        menu.getItem(0).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_icon) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
