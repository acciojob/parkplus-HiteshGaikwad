package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;

    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {
        User user= userRepository3.findById(userId).get();
        if(user==null){
            throw new Exception("Cannot make reservation");
        }

        ParkingLot parkingLot= parkingLotRepository3.findById(parkingLotId).get();
        if(parkingLot==null){
            throw new Exception("Cannot make reservation");
        }

        List<Spot> spotList= parkingLot.getSpotList();

        int totalPrice=Integer.MAX_VALUE;

        Spot reservedSpot=null;

        Reservation reservation=new Reservation();

        for(Spot spot: spotList){
            if(spot.getOccupied()==false && (spot.getPricePerHour()*timeInHours)<totalPrice){
                reservedSpot=spot;
            }
        }
        if(reservedSpot==null){
            throw new Exception("Cannot make reservation");
        }
        reservedSpot.setOccupied(true);
        reservedSpot.setParkingLot(parkingLot);

        if(numberOfWheels<=2){
            reservedSpot.setSpotType(SpotType.TWO_WHEELER);
        }else if(numberOfWheels<=4){
            reservedSpot.setSpotType(SpotType.FOUR_WHEELER);
        }else{
            reservedSpot.setSpotType(SpotType.OTHERS);
        }

        reservation.setSpot(reservedSpot);
        reservation.setUser(user);
        reservation.setNumberOfHours(timeInHours);


        List<Reservation> reservationList= reservedSpot.getReservationList();
        reservationList.add(reservation);

        reservedSpot.setReservationList(reservationList);

        reservationRepository3.save(reservation);

        return reservation;
    }
}
