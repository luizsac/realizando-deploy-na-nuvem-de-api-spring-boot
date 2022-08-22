package one.dio.parking.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import one.dio.parking.entity.dto.CreateParkingDTO;
import one.dio.parking.entity.dto.ParkingDTO;
import one.dio.parking.exception.CheckOutAlreadyExistsException;
import one.dio.parking.service.ParkingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking")
@Api(tags = "Parking Controller")
@RequiredArgsConstructor
public class ParkingController {

    private final ParkingService parkingService;

    @GetMapping
    @ApiOperation("Find all parkings")
    public ResponseEntity<List<ParkingDTO>> findAll() {
        var parkingDTOList = parkingService.findAll();
        return ResponseEntity.ok(parkingDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingDTO> findById(@PathVariable String id) {
        var parkingDTO = parkingService.findById(id);
        return ResponseEntity.ok(parkingDTO);
    }

    @PostMapping
    public ResponseEntity<ParkingDTO> create(@RequestBody CreateParkingDTO createParkingDTO) {
        var parkingDTO = parkingService.create(createParkingDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingDTO> update(@PathVariable String id, @RequestBody CreateParkingDTO updateParkingDTO) {
        var parkingDTO = parkingService.update(id, updateParkingDTO);
        return ResponseEntity.ok(parkingDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        parkingService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/exit")
    public ResponseEntity<ParkingDTO> checkOut(@PathVariable String id) throws CheckOutAlreadyExistsException {
        var parkingDTO = parkingService.checkOut(id);
        return ResponseEntity.ok(parkingDTO);
    }

}
