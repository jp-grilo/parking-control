package com.api.parkingcontrol.Controller;

import com.api.parkingcontrol.DTOs.ParkingSpotPostDto;
import com.api.parkingcontrol.Models.ParkingSpotModel;
import com.api.parkingcontrol.Service.ParkingSpotService;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")
public class ParkingSpotController {

    final ParkingSpotService parkingSpotService;

    public ParkingSpotController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    @PostMapping
    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotPostDto parkingSpotPostDto){
        if (parkingSpotService.existByCarLicensePlate(parkingSpotPostDto.getCarLicensePlate())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: License plate is already in use.");
        }
        if (parkingSpotService.existByParkingSpotNumber(parkingSpotPostDto.getParkingSpotNumber())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking spot is already in use.");
        }
        if (parkingSpotService.existByApartmentAndBlock(parkingSpotPostDto.getApartment(), parkingSpotPostDto.getBlock())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking spot already registered in this apartment.");
        }

        var parkingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingSpotPostDto, parkingSpotModel);
        parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotModel));
    }

    @GetMapping
    public ResponseEntity<List<ParkingSpotModel>> getAllParkingSpots(){
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll());
    }
}
