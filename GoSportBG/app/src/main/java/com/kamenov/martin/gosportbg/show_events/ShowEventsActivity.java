package com.kamenov.martin.gosportbg.show_events;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.maps.android.ui.IconGenerator;
import com.kamenov.martin.gosportbg.GoSportApplication;
import com.kamenov.martin.gosportbg.R;
import com.kamenov.martin.gosportbg.base.contracts.BaseContracts;
import com.kamenov.martin.gosportbg.constants.Constants;
import com.kamenov.martin.gosportbg.event.EventActivity;
import com.kamenov.martin.gosportbg.internet.HttpRequester;
import com.kamenov.martin.gosportbg.models.Event;
import com.kamenov.martin.gosportbg.models.LocalUser;
import com.kamenov.martin.gosportbg.navigation.ActivityNavigationCommand;

import java.util.Arrays;

import static com.google.maps.android.ui.IconGenerator.STYLE_BLUE;
import static com.google.maps.android.ui.IconGenerator.STYLE_GREEN;
import static com.google.maps.android.ui.IconGenerator.STYLE_ORANGE;
import static com.google.maps.android.ui.IconGenerator.STYLE_PURPLE;
import static com.google.maps.android.ui.IconGenerator.STYLE_RED;
import static com.google.maps.android.ui.IconGenerator.STYLE_WHITE;

public class ShowEventsActivity extends FragmentActivity implements ShowEventsContracts.IShowEventsView, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private ShowEventsContracts.IShowEventsPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_events);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        ShowEventsContracts.IShowEventsPresenter presenter = new ShowEventsPresenter(new HttpRequester(),
                new Gson(),
                new ActivityNavigationCommand(this, EventActivity.class));
        setPresenter(presenter);
        presenter.subscribe(this);
        presenter.getEvents();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng startingLatLng = new LatLng(42.698334, 23.319941);
        if(mPresenter.getUser() != null && mPresenter.getUser().getCity() != null) {
            String userCity = mPresenter.getUser().getCity();
            int index = Arrays.asList(Constants.CITIES).indexOf(userCity);
            if(index >= 0) {
                double cityLatitude = Arrays.asList(Constants.CITIESCOORDINATES).get(index)[0];
                double cityLongitude = Arrays.asList(Constants.CITIESCOORDINATES).get(index)[1];
                startingLatLng = new LatLng(cityLatitude, cityLongitude);
            }
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startingLatLng, 13.0f));
    }

    @Override
    public void setPresenter(BaseContracts.Presenter presenter) {
        this.mPresenter = (ShowEventsContracts.IShowEventsPresenter) presenter;
    }

    @Override
    public GoSportApplication getGoSportApplication() {
        return (GoSportApplication)getApplication();
    }

    @Override
    public void showEventsOnUITread(final Event[] events) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                IconGenerator iconGenerator = new IconGenerator(ShowEventsActivity.this);
                mMap.setOnMarkerClickListener(ShowEventsActivity.this);

                for(int i = 0; i < events.length; i++) {
                    Event event = events[i];
                    int styleIndex = chooseStyle(event.sport);
                    iconGenerator.setStyle(styleIndex);
                    Bitmap iconBitmap;
                    String info = event.name;
                    if(event.name.length() >= 20) {
                        info = info.substring(0, 19);
                    }
                    if(event.neededPlayers > 0) {
                        iconBitmap = iconGenerator.makeIcon(event.sport + "\n" +
                                info + "\n" +
                                event.players.size() + "/" + event.neededPlayers + "\n" +
                                event.datetime.dayOfMonth + " " + Constants.MONTHS[event.datetime.month] + "\n" +
                                event.datetime.hour + ":" + String.format("%02d", event.datetime.minute));
                    } else {
                        iconBitmap = iconGenerator.makeIcon(event.sport + "\n" +
                                info + "\n" +
                                event.datetime.dayOfMonth + " " + Constants.MONTHS[event.datetime.month] + "\n" +
                                event.datetime.hour + ":" + String.format("%02d", event.datetime.minute));
                    }
                    LatLng latLng = new LatLng(events[i].location.latitude, events[i].location.longitude);
                    mMap.addMarker(new MarkerOptions().position(latLng)
                            .icon(BitmapDescriptorFactory.fromBitmap(iconBitmap)).anchor(0.5f, 0.6f)
                            .snippet(String.valueOf(events[i].id)));
                }
            }
        });
    }

    private int chooseStyle(String sport) {
        switch (sport) {
            case "Футбол":
                return Constants.STYLES[1];
            case "Баскетбол":
                return Constants.STYLES[3];
            default:
                return Constants.STYLES[5];
        }
    }

    @Override
    public void markerPressed(int id) {
        mPresenter.navigateToEvent(id);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        int id = Integer.parseInt(marker.getSnippet());
        markerPressed(id);

        return false;
    }
}
