package com.driver.controllers;

import com.driver.model.Reservation;
import com.driver.services.impl.ReservationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    @Autowired
    ReservationServiceImpl reservationService;
    @PostMapping("/reserveSpot")
    public Reservation reserveSpot(@RequestParam("userId") Integer userId, @RequestParam("parkingLotId") Integer parkingLotId, @RequestParam("timeInHours") Integer timeInHours, @RequestParam("numberOfWheels") Integer numberOfWheels) throws Exception{
        //Reserve a spot in the given parkingLot such that the total price is minimum. Note that the price per hour for each spot is different
        //Note that the vehicle can only be parked in a spot having a type equal to or larger than given vehicle
        //If parkingLot is not found, user is not found, or no spot is available, throw "Cannot make reservation" exception.
        Reservation reservation= reservationService.reserveSpot(userId,parkingLotId,timeInHours,numberOfWheels);

        return reservation;
    }
}
