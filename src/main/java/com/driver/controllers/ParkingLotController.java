package com.driver.controllers;

import com.driver.repository.SpotRepository;
import com.driver.services.impl.ParkingLotServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.driver.model.*;

@RestController
@RequestMapping("/parking-lots")
public class ParkingLotController {

    //findById and deleteById should be used wherever necessary
    //findAll should never be used
    @Autowired
    ParkingLotServiceImpl parkingLotService;

    @PostMapping("/add")
    public ResponseEntity<ParkingLot> addParkingLot(@RequestParam("name") String name, @RequestParam("address") String address) {
        //add a new parking lot to the database
        ParkingLot newParkingLot= parkingLotService.addParkingLot(name, address);
        return new ResponseEntity<>(newParkingLot, HttpStatus.CREATED);
    }

    @PostMapping("/{parkingLotId}/spot/add")
    public ResponseEntity<Spot> addSpot(@PathVariable("parkingLotId") int parkingLotId, @RequestParam("numberOfWheels") Integer numberOfWheels, @RequestParam("pricePerHour") Integer pricePerHour) {
        //create a new spot in the parkingLot with given id
        //the spot type should be the next biggest type in case the number of wheels are not 2 or 4, for 4+ wheels, it is others
        Spot newSpot= parkingLotService.addSpot(parkingLotId,numberOfWheels,pricePerHour);
        return new ResponseEntity<>(newSpot, HttpStatus.CREATED);
    }

    @DeleteMapping("/spot/{spotId}/delete")
    public ResponseEntity<Void> deleteSpot(@PathVariable("spotId") int spotId) {
        //delete a spot from given parking lot
        parkingLotService.deleteSpot(spotId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{parkingLotId}/spot/{spotId}/update")
    public ResponseEntity<Spot> updateSpot(@PathVariable("parkingLotId") int parkingLotId, @PathVariable("spotId") int spotId, @RequestParam("pricePerHour") int pricePerHour) {
        //update the details of a spot
        Spot updatedSpot= parkingLotService.updateSpot(parkingLotId,spotId,pricePerHour);
        return new ResponseEntity<>(updatedSpot, HttpStatus.OK);
    }

    @DeleteMapping("/{parkingLotId}/delete")
    public ResponseEntity<Void> deleteParkingLot(@PathVariable("parkingLotId") int parkingLotId) {
        //delete a parkingLot
        parkingLotService.deleteParkingLot(parkingLotId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
