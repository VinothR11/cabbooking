package com.example.cabbooking.controllers;

import com.example.cabbooking.service.RidersManager;
import com.example.cabbooking.service.TripsManager;
import com.example.cabbooking.model.Location;
import com.example.cabbooking.model.Rider;
import com.example.cabbooking.model.Trip;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/riders") // Base path for all rider-related endpoints
public class RidersController {

    private final RidersManager ridersManager;
    private final TripsManager tripsManager;

    public RidersController(RidersManager ridersManager, TripsManager tripsManager) {
        this.ridersManager = ridersManager;
        this.tripsManager = tripsManager;


    }

    @PostMapping("/register")
    public ResponseEntity<String> registerRider(
            @RequestParam String riderId,
            @RequestParam String riderName) {

        ridersManager.createRider(new Rider(riderId, riderName));
        return ResponseEntity.ok("Rider registered successfully.");
    }

    @PostMapping("/book")
    public ResponseEntity<String> bookTrip(
            @RequestParam String riderId,
            @RequestParam Double sourceX,
            @RequestParam Double sourceY,
            @RequestParam Double destX,
            @RequestParam Double destY) {

        tripsManager.createTrip(
                ridersManager.getRider(riderId),
                new Location(sourceX, sourceY),
                new Location(destX, destY));

        return ResponseEntity.ok("Trip booked successfully.");
    }

    @GetMapping("/history")
    public ResponseEntity<List<Trip>> fetchTripHistory(@RequestParam String riderId) {
        List<Trip> trips = tripsManager.tripHistory(ridersManager.getRider(riderId));
        return ResponseEntity.ok(trips);
    }
}
