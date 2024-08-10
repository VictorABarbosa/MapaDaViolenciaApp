package br.ufc.dc.sd4mp.cactacea;

import android.content.Context;
import android.content.Intent;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by Victor on 23/05/2015.
 */
public class MarkerClickListener implements GoogleMap.OnMarkerClickListener {

    private Context context;

    @Override
    public boolean onMarkerClick(Marker marker) {
/*        if (marker == null || marker.getTitle().toString().equals("Você está aqui")) return false;

        String title = marker.getTitle();

        Intent explicita = new Intent(context, GetRegion.class); //COMPONENTE ESPECIFICADO
        explicita.setAction(null);
        explicita.setType(null);
        explicita.setData(null);
        explicita.putExtra("clickRegionTitle",title);
        context.startActivity(explicita);*/
        return false;
    }

    public void setContext(Context c){
        this.context = c;
    }
}
