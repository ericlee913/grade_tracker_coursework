package uk.ac.ucl.comp0010.controller;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ucl.comp0010.exception.NoRegistrationException;
import uk.ac.ucl.comp0010.model.Grade;
import uk.ac.ucl.comp0010.service.GradeService;

/**
 * Rest controller class for grade operations.
 */
@RestController
public class GradeController {

  private final GradeService gradeService;

  /**
   * Constructor for instance of GradeController.
   *
   * @param gradeService - service for grade operations
   */
  public GradeController(GradeService gradeService) {
    this.gradeService = gradeService;
  }

  /**
   * Method that handles HTTP post requests to add grade in a specific module.
   *
   * @param params - map containing student id, module code, and score of grade
   * @return a ResponseEntity containing the Grade object
   */
  @PostMapping(value = "/grades/addGrade")
  public ResponseEntity<?> addGrade(@RequestBody Map<String, String> params) {
    try {
      Grade grade = gradeService.addGrade(Long.valueOf(params.get("student_id")),
          params.get("module_code"), Integer.parseInt(params.get("score")));
      return ResponseEntity.ok(grade);
    } catch (NoRegistrationException ex) {
      // Custom message for when no registration is found
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("No registration found for the student and module.");

    }
  }
}
