package com.driver.services.impl;

import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;

    @Autowired
    SpotRepository spotRepository1;

    @Override
    public ParkingLot addParkingLot(String name, String address) {
        ParkingLot parkingLot=new ParkingLot();
        parkingLot.setName(name);
        parkingLot.setAddress(address);

        parkingLotRepository1.save(parkingLot);
        return parkingLot;
    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {

        SpotType spotType;

        if(numberOfWheels<=2){
            spotType=SpotType.TWO_WHEELER;
        }else if(numberOfWheels<=4){
            spotType= SpotType.FOUR_WHEELER;
        }else{
            spotType=SpotType.OTHERS;
        }

        Spot spot=new Spot();

        spot.setPricePerHour(pricePerHour);
        spot.setOccupied(false);
        spot.setSpotType(spotType);

        ParkingLot parkingLot= parkingLotRepository1.findById(parkingLotId).get();

        List<Spot> spotList= parkingLot.getSpotList();
        if(spotList==null){
            spotList=new ArrayList<>();
        }

        spotList.add(spot);

        parkingLot.setSpotList(spotList);

        spot.setParkingLot(parkingLot);
        parkingLotRepository1.save(parkingLot);

        return spot;
    }

    @Override
    public void deleteSpot(int spotId) {
        Spot spot= spotRepository1.findById(spotId).get();

        spotRepository1.delete(spot);

        ParkingLot parkingLot= spot.getParkingLot();

        List<Spot> spotList= parkingLot.getSpotList();

        if(spotList==null){
            spotList=new ArrayList<>();
        }

        for(Spot spots: spotList){
            if(spots==spot){
                spotList.remove(spots);
                break;
            }
        }
        parkingLot.setSpotList(spotList);

        parkingLotRepository1.save(parkingLot);
    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {

        ParkingLot parkingLot= parkingLotRepository1.findById(parkingLotId).get();
        List<Spot> spotList= parkingLot.getSpotList();
        if(spotList==null){
            spotList=new ArrayList<>();
        }

        Spot spot= new Spot();

        for(Spot spots: spotList){
            if(spots.getId()==spotId){
                spots.setPricePerHour(pricePerHour);
                spot=spots;
            }
        }
        parkingLotRepository1.save(parkingLot);
        return spot;
    }

    @Override
    public void deleteParkingLot(int parkingLotId) {
        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();

        List<Spot> spotList= parkingLot.getSpotList();

        parkingLotRepository1.delete(parkingLot);

        for(Spot spot: spotList){
            spotRepository1.delete(spot);
        }
    }
}
