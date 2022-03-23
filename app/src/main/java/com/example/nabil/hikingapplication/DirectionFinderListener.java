package com.example.nabil.hikingapplication;

import java.util.List;

interface DirectionFinderListener {

    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
