package com.api.parkingcontrol.Service;

import com.api.parkingcontrol.Models.ParkingSpotModel;
import com.api.parkingcontrol.Respository.ParkingSpotRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ParkingSpotService {

    final ParkingSpotRepository parkingSpotRepository;

    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository){
        this.parkingSpotRepository = parkingSpotRepository;
    }

    @Transactional
    public ParkingSpotModel save(ParkingSpotModel parkingSpotModel) {
        return parkingSpotRepository.save(parkingSpotModel);
    }

    public boolean existByCarLicensePlate(String carLicensePlate){
        return parkingSpotRepository.existsByCarLicensePlate(carLicensePlate);
    }
    public boolean existByParkingSpotNumber(String parkingSpotNumber){
        return parkingSpotRepository.existsByParkingSpotNumber(parkingSpotNumber);
    }
    public boolean existByApartmentAndBlock(String apartment, String block){
        return parkingSpotRepository.existsByApartmentAndBlock(apartment,block);
    }
}
