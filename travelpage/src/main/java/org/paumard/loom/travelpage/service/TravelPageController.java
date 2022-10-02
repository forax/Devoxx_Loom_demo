package org.paumard.loom.travelpage.service;

import io.swagger.v3.oas.annotations.Operation;
import org.paumard.loom.travelpage.model.Quotation;
import org.paumard.loom.travelpage.model.Quotation.QuotationException;
import org.paumard.loom.travelpage.model.Travel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api")
public class TravelPageController {
  @ExceptionHandler(Exception.class)
  @ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No Travel Found")
  public void handleException()  {}

  @PostMapping(path="/travel", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  @Operation(summary = "find a travel")
  public Travel findTravel(@RequestBody TravelRequest travelRequest) throws InterruptedException {
    return Travel.readTravelPage();
  }

  @GetMapping(path="/whoami", produces = APPLICATION_JSON_VALUE)
  @Operation(summary = "who am i")
  public String whoami() {
    return """
        {
          "current-thread": "%s"
        }
        """.formatted(Thread.currentThread());
  }
}