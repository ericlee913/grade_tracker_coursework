package uk.ac.ucl.comp0010.controller;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ucl.comp0010.model.Registration;
import uk.ac.ucl.comp0010.service.RegistrationService;

/**
 * Rest controller class for registration operations.
 */
@RestController
public class RegistrationController {

  private final RegistrationService registrationService;

  /**
   * Constructor for an instance of RegistrationController.
   *
   * @param registrationService service for registration operations
   */
  public RegistrationController(RegistrationService registrationService) {
    this.registrationService = registrationService;
  }

  /**
   * Handles HTTP POST requests to register a student for a module.
   *
   * @param params map containing student ID and module code
   * @return a ResponseEntity containing the Registration object
   */
  @PostMapping(value = "/registrations/registerModule")
  public ResponseEntity<Registration> registerModule(
      @RequestBody Map<String, String> params) {
    Long studentId = Long.valueOf(params.get("student_id"));
    String moduleCode = params.get("module_code");

    Registration registration = registrationService.registerModule(studentId, moduleCode);
    return ResponseEntity.ok(registration);
  }
}
