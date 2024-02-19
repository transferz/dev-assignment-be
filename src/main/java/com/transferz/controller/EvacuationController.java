package com.transferz.controller;

import com.transferz.dao.Airport;
import com.transferz.dto.AddNewAirportRequest;
import com.transferz.dto.AddNewPassengerRequest;
import com.transferz.dto.GenericSuccessResponse;
import com.transferz.service.EvacuationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/evacuate/nl")
@RequiredArgsConstructor
@Slf4j
public class EvacuationController {

    private final EvacuationService evacuationService;

    /**
     * Handles GET requests to retrieve all airports
     * @param name is optional, used to filter airports with respect to name
     * @param code is optional, used to filter airports with respect to code
     * @param page is optional, used to filter airports with respect to page number. Default value is 0
     * @param size is optional, used to set maximum no of contents to be displayed in a page. Default value is 10
     * @return ResponseEntity containing success message and list of airports.
     */
    @GetMapping("airports")
    ResponseEntity<Page<Airport>> getAllAirports(@RequestParam(required = false) String name,
                                                 @RequestParam(required = false) String code,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size
    ) throws Exception {
        log.info("Received request for fetching all Airports");
        Pageable pageableAirportList = PageRequest.of(page, size);
        return ResponseEntity.ok().body(evacuationService.getAllAirports(name, code, pageableAirportList));
    }

    /**
     * Handles POST requests to add a new airport
     *
     * @param addNewAirportRequest the request body containing information to add an airport
     * @return ResponseEntity<GenericSuccessResponse> containing success message
     */
    @PostMapping("airport")
    ResponseEntity<GenericSuccessResponse> addAirport(@Valid @RequestBody AddNewAirportRequest addNewAirportRequest) throws Exception {
        log.info("Received request for adding new airport: {}", addNewAirportRequest.getName());
        return ResponseEntity.ok().body(evacuationService.addNewAirport(addNewAirportRequest));
    }

    /**
     * Handles POST requests to add a new passenger
     *
     * @param addNewPassengerRequest the request body containing information to add passenger
     * @return ResponseEntity<GenericSuccessResponse> containing success message.
     */
    @PostMapping("passenger")
    ResponseEntity<GenericSuccessResponse> addPassenger(@Valid @RequestBody AddNewPassengerRequest addNewPassengerRequest) throws Exception {
        log.info("Received customer creation request for: {}", addNewPassengerRequest.getName());
        return ResponseEntity.ok().body(evacuationService.addNewPassenger(addNewPassengerRequest));
    }
}
