package one.dio.parking.service;

import lombok.RequiredArgsConstructor;
import one.dio.parking.entity.Parking;
import one.dio.parking.entity.dto.CreateParkingDTO;
import one.dio.parking.entity.dto.ParkingDTO;
import one.dio.parking.entity.dto.ParkingMapper;
import one.dio.parking.exception.CheckOutAlreadyExistsException;
import one.dio.parking.exception.ParkingNotFoundException;
import one.dio.parking.repository.ParkingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParkingService {

    private final ParkingRepository parkingRepository;
    private final ParkingMapper parkingMapper;

    @Transactional(readOnly = true)
    public List<ParkingDTO> findAll() {
        return parkingMapper.toParkingDTOList(parkingRepository.findAll());
    }

    public ParkingDTO findById(String id) {
        Parking parking = findParkingById(id);
        return parkingMapper.toParkingDTO(parking);
    }

    @Transactional
    public ParkingDTO create(CreateParkingDTO createParkingDTO) {
        Parking parking = parkingMapper.toCreateParking(createParkingDTO);
        String uuid = getUUID();
        parking.setId(uuid);
        parking.setEntryDate(LocalDateTime.now());
        Parking createdParking = parkingRepository.save(parking);
        return parkingMapper.toParkingDTO(createdParking);
    }

    @Transactional
    public ParkingDTO update(String id, CreateParkingDTO updateParkingDTO) {
        Parking parking = findParkingById(id);
        parking.setColor(updateParkingDTO.getColor());
        parking.setState(updateParkingDTO.getState());
        parking.setModel(updateParkingDTO.getModel());
        parking.setLicense(updateParkingDTO.getLicense());
        Parking updatedParking = parkingRepository.save(parking);
        return parkingMapper.toParkingDTO(updatedParking);
    }

    @Transactional
    public void delete(String id) {
        findParkingById(id);
        parkingRepository.deleteById(id);
    }

    @Transactional
    public ParkingDTO checkOut(String id) throws CheckOutAlreadyExistsException {
        Parking parking = findParkingById(id);
        if (parking.getExitDate() != null) throw new CheckOutAlreadyExistsException();
        parking.setExitDate(LocalDateTime.now());
        parking.setBill(ParkingCheckOut.getBill(parking));
        Parking checkedOutParking = parkingRepository.save(parking);
        return parkingMapper.toParkingDTO(checkedOutParking);
    }

    @Transactional(readOnly = true)
    private Parking findParkingById(String id) {
        Parking parking = parkingRepository.findById(id).orElseThrow(() -> new ParkingNotFoundException(id));
        return parking;
    }

    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}