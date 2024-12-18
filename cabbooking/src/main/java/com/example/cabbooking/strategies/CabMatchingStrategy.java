package com.example.cabbooking.strategies;

import com.example.cabbooking.model.Cab;
import com.example.cabbooking.model.Location;
import com.example.cabbooking.model.Rider;

import java.util.List;

public interface CabMatchingStrategy {

  Cab matchCabToRider(Rider rider, List<Cab> candidateCabs, Location fromPoint, Location toPoint);
}
