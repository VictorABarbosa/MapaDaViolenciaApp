package br.ufc.dc.sd4mp.cactacea;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.wearable.Asset;

import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.Address;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;


public class MapsActivity extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, AdapterView.OnItemSelectedListener {

    private GoogleApiClient googleApiClient;
    private GoogleMap googleMap; // Might be null if Google Play services APK is not available.
    private LocationManager locationManager;
    private Location lastLocation;
    private CityDAO cityDAO;

    private boolean filterByRate;
    private boolean filterByPopulation;
    private double minRate;
    private double maxRate;
    private int minPopulation;
    private int maxPopulation;
    private int maxResults;
    private boolean search_by_name;
    private String cityName;
    private List<String> listOfCities;

    @Override
    protected void onCreate(Bundle savedInstanceState) throws RuntimeException{
        //Toast.makeText(getApplicationContext(), "OnCreate...", Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (googleApiClient==null) {
            try {
                GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this);
                builder.addApi(LocationServices.API);
                builder.addConnectionCallbacks(this);
                builder.addOnConnectionFailedListener(this);
                googleApiClient = builder.build();
        //        googleApiClient.connect();
            /*    lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                if (lastLocation != null)
                    Toast.makeText(getApplicationContext(), "Pos: "+lastLocation.getLatitude()+","+lastLocation.getLongitude(), Toast.LENGTH_SHORT).show();
          */
            } catch (RuntimeException e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                return;
            }
        }
        googleApiClient.connect();

        cityDAO = MainActivity.getCityDAO();
        filterByRate = getIntent().getBooleanExtra("filterByRate", true);
        filterByPopulation = getIntent().getBooleanExtra("filterByPopulation", false);
        minRate = getIntent().getDoubleExtra("minRate", 10.0);
        maxRate = getIntent().getDoubleExtra("maxRate", 100000.0);
        minPopulation = getIntent().getIntExtra("minPopulation", 0);
        maxPopulation = getIntent().getIntExtra("maxPopulation", 10000000);
        maxResults = getIntent().getIntExtra("maxResults", 184);
        search_by_name = getIntent().getBooleanExtra("searchByName", false);
        cityName = getIntent().getStringExtra("cityName");

        listOfCities = new ArrayList<String>();
        //TextView rate_info = (TextView) findViewById(R.id.rate_restriction_info);
        //TextView population_info = (TextView) findViewById(R.id.population_restriction_info);
        //TextView results_info = (TextView) findViewById(R.id.max_results_info);

        //results_info.setText("Número de resultados: " + maxResults);

        setUpMapIfNeeded();
        addMarkersOnMap();
    }
/*
    public MapsActivity(boolean filter_by_rate, boolean filter_by_population, double min_rate, double max_rate, int min_population, int max_population, int max_results){
        filterByRate = filter_by_rate;
        filterByPopulation = filter_by_population;
        minRate = min_rate;
        maxRate = max_rate;
        minPopulation = min_population;
        maxPopulation = max_population;
        maxResults = max_results;
    }
*/
    @Override
    protected void onDestroy() {
        googleApiClient.disconnect();
        super.onDestroy();
        Log.i("Mapa", "MapsActivity" + ".onDestroy() --> destroyed");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Mapa", "MapsActivity" + ".onPause() --> paused");
    }

    @Override
    protected void onRestart() {
        googleApiClient.connect();
    //    setUpMapIfNeeded();
    //    addMarkersOnMap();
        super.onRestart();
    //    addMarkersOnMap();
        Log.i("Mapa", "MapsActivity" + ".onRestart() --> restarted");
    }

    @Override
    protected void onStart() {
        googleApiClient.connect();
    //    setUpMapIfNeeded();
    //    addMarkersOnMap();
        super.onStart();
        Log.i("Mapa", "MapsActivity" + ".onStart() --> started");
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
        Log.i("Mapa", "MapsActivity" + ".onStopped() --> stopped");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Mapa", "MapsActivity" + ".onResume() --> resumed");
    //    setUpMapIfNeeded();
    //    addMarkersOnMap();
    }

    public void activityBack(View view){
        finish();
    }

    @Override
    public void onConnected(Bundle bundle) {
        //LocationRequest request = new LocationRequest();
        //request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        //request.setInterval(5000);
        //request.setSmallestDisplacement(2);
/*
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, request, this);

        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        if (lastLocation == null){
            String bestProvider = "No provider";
            locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
            bestProvider = locationManager.getBestProvider(new Criteria(), true);
            lastLocation = locationManager.getLastKnownLocation(bestProvider);
        }
*/
//        Toast.makeText(this, "lastLocation="+lastLocation.toString(), Toast.LENGTH_LONG).show();
    //    Toast.makeText(this, "Connected!", Toast.LENGTH_LONG).show();
//        setUpMapIfNeeded();
//        addMarkersOnMap();
    }

    @Override
    public void onConnectionSuspended(int value) {
    //    Toast.makeText(this, "Disconnected!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
    //    Toast.makeText(this, "Connection failed...", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLocationChanged(Location location) {
    //    Toast.makeText(this, "location changed", Toast.LENGTH_LONG).show();
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        String bestProvider = "No provider";
        /*if (lastLocation == null){
            locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
            bestProvider = locationManager.getBestProvider(new Criteria(), true);
            lastLocation = locationManager.getLastKnownLocation(bestProvider);
        }*/
        //setUpMapIfNeeded();
        //addMarkersOnMap();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #addMarkersOnMap()} ()} once when {@link #googleMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
    //    Toast.makeText(getApplicationContext(), "Creating maps", Toast.LENGTH_SHORT).show();
        //((SupportMapFragment) getSupportFragmentManager()
        // Do a null check to confirm that we have not already instantiated the map.
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            if (googleMap != null){
                googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                googleMap.getUiSettings().setMapToolbarEnabled(false);
                FocusAllLocations(findViewById(R.id.map));
                //    addMarkersOnMap();
            }
    //      else Toast.makeText(getApplicationContext(), "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
        }
        else{
            googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            googleMap.getUiSettings().setMapToolbarEnabled(false);
            FocusAllLocations(findViewById(R.id.map));
        }
        //else addMarkersOnMap();
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #googleMap} is not null.
     */
    private void addMarkersOnMap() throws RuntimeException{


            //if (googleMap != null) {
            //    FocusAllLocations(findViewById(R.id.map));
                try {
                    googleMap.clear();

                /*this.locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
                if (locationManager!=null) Toast.makeText(this,"locationManager: "+locationManager.toString(), Toast.LENGTH_LONG).show();
                String bestProvider = "No provider";
                if (this.locationManager != null) bestProvider = this.locationManager.getBestProvider(new Criteria(), true);
                */
                    String bestProvider = "No provider";
                    //googleApiClient.connect();
                /*    if (googleApiClient.isConnected() == false) {
                        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
                        //if (locationManager!=null) Toast.makeText(this,"locationManager: "+locationManager.toString(), Toast.LENGTH_LONG).show();
                        if (locationManager != null) {
                            bestProvider = locationManager.getBestProvider(new Criteria(), true);
                            lastLocation = locationManager.getLastKnownLocation(bestProvider);
                        }
                        Toast.makeText(this, "googleApiClient: " + googleApiClient.toString(), Toast.LENGTH_LONG).show();
                        //return;
                    } else*/
                    lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                    if (lastLocation == null) {
                        this.locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
                        bestProvider = this.locationManager.getBestProvider(new Criteria(), true);
                        lastLocation = locationManager.getLastKnownLocation(bestProvider);
                    }
                    if (lastLocation == null) return;

                    //        if (lastLocation == null) //Toast.makeText(this, "lastLocation: null", Toast.LENGTH_LONG).show();
                    // else{//if (lastLocation != null) {
                    //Toast.makeText(this, "lastLocation: " + lastLocation.toString(), Toast.LENGTH_LONG).show();
                    double lat = lastLocation.getLatitude();
                    double lng = lastLocation.getLongitude();

                    LatLng latLng = new LatLng(lat, lng);
                    googleMap.addMarker(new MarkerOptions().position(latLng).title("Sua localização").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_user_marker)).visible(true));

                        /* Add Markers to the regions */
                    List<City> cities = cityDAO.list();

                    if (filterByRate == true) {

                        TextView rate_info = (TextView) findViewById(R.id.rate_restriction_info);
                        TextView population_info = (TextView) findViewById(R.id.population_restriction_info);

                        if (minRate == 0.0 && maxRate > 0.0) rate_info.setText("Menos de " + (int)(maxRate+1) + " mortes/100 mil habitantes.");
                        else if (maxRate == 100000.0) rate_info.setText("Igual a " + (int)minRate + " ou mais mortes/100 mil habitantes.");
                        else if (minRate == 0.1 && maxRate < 100000.0) rate_info.setText("Mais de 0 mortes/100 mil habitantes, e menos de " + (int)(maxRate+1) + ".");
                        else if (minRate > 0.0 && maxRate < 100000.0) rate_info.setText("Igual a " + (int)minRate + " ou mais mortes/100 mil habitantes, e menos de " + (int)(maxRate+1) + ".");
                        else rate_info.setText("Igual a 0 mortes/100 mil habitantes.");

                        if (filterByPopulation == false) population_info.setText("Qualquer população.");
                        else if (minPopulation == 100000 && maxPopulation == 10000000) population_info.setText("População igual a 100 mil habitantes ou mais.");
                        else if (minPopulation == 0 && maxPopulation == 99999) population_info.setText("População com menos de 100 mil habitantes.");
                        else if (minPopulation == 20000 && maxPopulation == 99999) population_info.setText("População com 20 mil habitantes ou mais, e menos de 100 mil.");
                        else if (minPopulation == 0 && maxPopulation == 19999) population_info.setText("População com menos de 20 mil habitantes.");


                        if (cities != null && cities.size() > 0) {
                            Iterator<City> it = cities.iterator();
                            if (it != null) {

                                boolean satisfiesRateRestriction;
                                boolean satisfiesPopulationRestriction;

                                int rows = 0;
                                while (it.hasNext() && rows < maxResults) {

                                    City currentCity = it.next();
                                    satisfiesRateRestriction = false;
                                    satisfiesPopulationRestriction = false;

                                    if (currentCity.getRateHomicides() >= minRate && currentCity.getRateHomicides() <= maxRate)
                                        satisfiesRateRestriction = true;
                                    if (currentCity.getPopulation() >= minPopulation && currentCity.getPopulation() <= maxPopulation)
                                        satisfiesPopulationRestriction = true;

                                    if (filterByRate == true && filterByPopulation == true) {
                                        if ((filterByRate == true && satisfiesRateRestriction == true) && (filterByPopulation == true && satisfiesPopulationRestriction == true)) {
                                            try {
                                                ApplyGeocoder(currentCity);
                                                rows++;
                                                listOfCities.add(currentCity.getNameCity());
                                                continue;
                                            } catch (IOException e) {
                                                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    } else {
                                        if (filterByRate == true && satisfiesRateRestriction == true) {
                                            try {
                                                ApplyGeocoder(currentCity);
                                                rows++;
                                                listOfCities.add(currentCity.getNameCity());
                                                continue;
                                            } catch (IOException e) {
                                                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
                                            }
                                        } else if (filterByPopulation == true && satisfiesPopulationRestriction == true) {
                                            try {
                                                ApplyGeocoder(currentCity);
                                                rows++;
                                                listOfCities.add(currentCity.getNameCity());
                                                continue;
                                            } catch (IOException e) {
                                                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    }
                                }
                                TextView results_info = (TextView) findViewById(R.id.max_results_info);
                                results_info.setText("Resultados: " + rows + " (máx. " + maxResults + ")");
                            }
                        }
                    }
                    else if (search_by_name == true){
                        if (cityName.isEmpty() == false) {
                            try {
                                    City city = cityDAO.queryName(cityName);
                                    if (city != null) {
                                        TextView search_info = (TextView) findViewById(R.id.rate_restriction_info);
                                        TextView city_population_info = (TextView) findViewById(R.id.population_restriction_info);
                                        TextView city_rate_info = (TextView) findViewById(R.id.max_results_info);
                                        search_info.setText("Pesquisa por nome: " + city.getNameCity());
                                        city_population_info.setText("População: " + city.getPopulation());
                                        city_rate_info.setText("Taxa de homicídios: " + city.getRateHomicides());
                                        ApplyGeocoder(city);
                                        listOfCities.add(city.getNameCity());
                                    }
                                    else finish();
                                }
                                catch (IOException e) {
                                    Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
                                }
                        }
                        else {
                            finish();
                        }
                    }

                    MarkerClickListener markerClickListener;
                    markerClickListener = new MarkerClickListener();
                    markerClickListener.setContext(this);
                    googleMap.setOnMarkerClickListener(markerClickListener);
                    //FocusAllLocations(findViewById(R.id.map));
                }
                catch (Exception e) {
                    Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
                }
    }

    public void ApplyGeocoder(City currentCity) throws IOException{
 /*       Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        int numResults = 1;
        double lowerLeftLat = -7.48689;
        double lowerLeftLng = -40.49;
        double upperRightLat = -2.47;
        double upperRightLng = -37.348;*/
        if (currentCity != null) {
            String nameCity = currentCity.getNameCity();
            String cityUF = currentCity.getUF();
            double cityRateHomicide = currentCity.getRateHomicides();
            //List<Address> address = null;
            try {
              //  address = geocoder.getFromLocationName(nameCity + "," + cityUF, numResults, lowerLeftLat, lowerLeftLng, upperRightLat, upperRightLng);
                //if (address != null && address.size() > 0) {
                    //LatLng latLng1 = new LatLng(address.get(0).getLatitude(), address.get(0).getLongitude());
                LatLng latLng1 = new LatLng(currentCity.getLat(), currentCity.getLng());
                    //LatLng latLngCamocim = new LatLng(-2.90,-40.84);
                    //if (currentCity.getRateHomicides() >= 50)
                    //    this.googleMap.addMarker(new MarkerOptions().position(latLng1).title(nameCity).snippet(cityRateHomicide+" mortes / 100 mil hab.").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_dark_red_marker)));
                    if (currentCity.getRateHomicides() >= 20)
                        this.googleMap.addMarker(new MarkerOptions().position(latLng1).title(nameCity).snippet(cityRateHomicide+" mortes / 100 mil hab.").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_dark_red_marker)));
                    else if (currentCity.getRateHomicides() >= 10)
                        this.googleMap.addMarker(new MarkerOptions().position(latLng1).title(nameCity).snippet(cityRateHomicide+" mortes / 100 mil hab.").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_red_marker)));
                    else if (currentCity.getRateHomicides() >= 5)
                        this.googleMap.addMarker(new MarkerOptions().position(latLng1).title(nameCity).snippet(cityRateHomicide+" mortes / 100 mil hab.").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_orange_marker)));
                    else if (currentCity.getRateHomicides() > 0)
                        this.googleMap.addMarker(new MarkerOptions().position(latLng1).title(nameCity).snippet(cityRateHomicide+" mortes / 100 mil hab.").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_yellow_marker)));
                    else if (currentCity.getRateHomicides() == 0)
                        this.googleMap.addMarker(new MarkerOptions().position(latLng1).title(nameCity).snippet(cityRateHomicide+" mortes / 100 mil hab.").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_white_marker)));

                //}
            }
            catch (Exception e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void ShowList(View view){
        Intent explicita = new Intent(this, ShowListActivity.class); //COMPONENTE ESPECIFICADO
        explicita.setAction(null);
        explicita.setType(null);
        explicita.setData(null);
        explicita.putStringArrayListExtra("listOfCities",(ArrayList)listOfCities);
        this.startActivity(explicita);
    }

    public void FocusMyLocation(View view){
        this.locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        String bestProvider = "No provider";
        if (this.locationManager != null) bestProvider = this.locationManager.getBestProvider(new Criteria(), true);

        lastLocation = this.locationManager.getLastKnownLocation(bestProvider);

        double lat = lastLocation.getLatitude();
        double lng = lastLocation.getLongitude();
        LatLng LatLngToFocus = new LatLng(lat, lng);

        //float max_zoom = this.mMap.getMaxZoomLevel();
        CameraPosition camera = new CameraPosition(LatLngToFocus, 10, 0, 0); //(max_zoom + 3/2)/3

        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(camera);
        googleMap.animateCamera(cameraUpdate);
    }

    public void FocusAllLocations(View view) throws RuntimeException{
        try {
            LatLng LatLngToFocus = new LatLng(-4.9200, -39.4000);
            float max_zoom = googleMap.getMaxZoomLevel();
            CameraPosition camera = new CameraPosition(LatLngToFocus, (max_zoom - 3/2)/3, 0, 0);

            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(camera);
            googleMap.animateCamera(cameraUpdate);
        }
        catch (RuntimeException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }
/*
    public void setFilterAttributes(boolean filter_by_rate, boolean filter_by_population, double min_rate, double max_rate, int min_population, int max_population, int max_results){
        filterByRate = filter_by_rate;
        filterByPopulation = filter_by_population;
        minRate = min_rate;
        if (max_rate > 0) maxRate = max_rate;
        if (min_population > 0) minPopulation = min_population;
        if (max_population > 0) maxPopulation = max_population;
        if (max_results > 0) maxResults = max_results;
    }
*/
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /*
    public void GenerateCustomMap(View view){
        filterByRate = true;
        filterByPopulation = true;
        //RadioButton filter_by_rate = (RadioButton) findViewById(R.id.filter_by_rate);
        //NumberPicker numberPicker = (NumberPicker) findViewById(R.id.max_result);
        minRate = 10.0;
        maxRate = 100000.0;

        maxResults = 60;
        //if (numberPicker != null) maxResults = numberPicker.getValue();

        //if (filter_by_rate != null && filter_by_rate.isChecked()) {

            Spinner rate_restriction = (Spinner) findViewById(R.id.rate_restriction);
            int selected_rate_restriction = rate_restriction.getSelectedItemPosition();
            if (selected_rate_restriction == 0) {//.toString().equals("menor que 10")){
                minRate = 10.0;
                maxRate = 100000.0;
            } else if (selected_rate_restriction == 1) {//.toString().equals("maior ou igual a 10")){
                minRate = 50.0;
                maxRate = 100000.0;
            } else if (selected_rate_restriction == 2) {//.toString().equals("maior ou igual a 10 e menor que 30")){
                minRate = 30.0;
                maxRate = 100000.0;
            } else if (selected_rate_restriction == 3) {//.toString().equals("maior ou igual a 30")){
                minRate = 10.0;
                maxRate = 29.99;
            } else if (selected_rate_restriction == 4) {//.toString().equals("maior ou igual a 50")){
                minRate = 0.0;
                maxRate = 9.99;
            }
        //}

        //CheckBox filter_by_population = (CheckBox) findViewById(R.id.filter_by_population);
        minPopulation = 100000;
        maxPopulation = 10000000;

        //if (filter_by_population != null && filter_by_population.isChecked()) {

            Spinner population_restriction = (Spinner) findViewById(R.id.population_restriction);
            int selected_population_restriction = population_restriction.getSelectedItemPosition();
            if (selected_population_restriction == 0){
                minPopulation = 100000;
                maxPopulation = 10000000;
            }
            else if (selected_population_restriction == 1){
                minPopulation = 0;
                maxPopulation = 100000;
            }
        //}

        mMap.clear();
        setUpMap();

    /*    Intent map_activity = new Intent(this, MapsActivity.class); //COMPONENTE ESPECIFICADO
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
        this.startActivity(map_activity);
    }*/
}