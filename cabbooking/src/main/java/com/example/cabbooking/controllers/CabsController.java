package com.example.cabbooking.controllers;

import com.example.cabbooking.service.CabsManager;
import com.example.cabbooking.service.TripsManager;
import com.example.cabbooking.model.Cab;
import com.example.cabbooking.model.Location;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cabs")
public class CabsController {

    private final CabsManager cabsManager;
    private final TripsManager tripsManager;

    public CabsController(CabsManager cabsManager, TripsManager tripsManager) {
        this.cabsManager = cabsManager;
        this.tripsManager = tripsManager;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerCab(@RequestParam String cabId, @RequestParam String driverName) {
        cabsManager.createCab(new Cab(cabId, driverName));
        return ResponseEntity.ok("Cab registered successfully.");
    }

    @PostMapping("/location/update")
    public ResponseEntity<String> updateCabLocation(
            @RequestParam String cabId,
            @RequestParam Double newX,
            @RequestParam Double newY) {

        cabsManager.updateCabLocation(cabId, new Location(newX, newY));
        return ResponseEntity.ok("Cab location updated successfully.");
    }

    @PostMapping("/availability/update")
    public ResponseEntity<String> updateCabAvailability(
            @RequestParam String cabId,
            @RequestParam Boolean newAvailability) {

        cabsManager.updateCabAvailability(cabId, newAvailability);
        return ResponseEntity.ok("Cab availability updated successfully.");
    }

    @PostMapping("/trip/end")
    public ResponseEntity<String> endTrip(@RequestParam String cabId) {
        tripsManager.endTrip(cabsManager.getCab(cabId));
        return ResponseEntity.ok("Trip ended successfully.");
    }
}
