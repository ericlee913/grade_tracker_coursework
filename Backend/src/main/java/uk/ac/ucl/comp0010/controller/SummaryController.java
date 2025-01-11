package uk.ac.ucl.comp0010.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ucl.comp0010.dto.SummaryDto;
import uk.ac.ucl.comp0010.service.SummaryService;

/**
 * Rest controller class for summary operations.
 */
@RestController
public class SummaryController {

  @Autowired
  private SummaryService summaryService;

  /**
   * Method for getting summary data.
   *
   * @return a ResponseEntity containing the summaryDto object
   */
  @GetMapping(value = "/summary")
  public ResponseEntity<SummaryDto> getSummaryData() {
    SummaryDto summary = summaryService.getSummaryData();
    return ResponseEntity.ok(summary);
  }
}
