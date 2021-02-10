package com.example.advertisingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class FragmentLocation extends Fragment implements OnMapReadyCallback {

    MapView mapView = null;

    public FragmentLocation(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_location, container, false);
        mapView = (MapView)v.findViewById(R.id.maps);

        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng Seoul = new LatLng(37.56,126.97);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(Seoul);
        markerOptions.title("모아광");
        markerOptions.snippet("회사 위치");
        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(Seoul));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));
    }
}
