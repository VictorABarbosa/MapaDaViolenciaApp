package br.ufc.dc.sd4mp.cactacea;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class InformacaoUtil extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacao_util);
    }


    public void LinkToSource(View view){
        Intent linktoSource = new Intent(Intent.ACTION_VIEW);
        //Intent filters_activity = new Intent(this, ApplyFilters.class); //COMPONENTE ESPECIFICADO
        linktoSource.setAction(null);
        linktoSource.setType(null);
        linktoSource.setData(null);
        linktoSource.setData(Uri.parse("http://mapadaviolencia.org.br"));
        this.startActivity(linktoSource);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_informacao_util, menu);
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
