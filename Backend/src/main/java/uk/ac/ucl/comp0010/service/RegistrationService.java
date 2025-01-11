package uk.ac.ucl.comp0010.service;

import org.springframework.stereotype.Service;
import uk.ac.ucl.comp0010.model.Module;
import uk.ac.ucl.comp0010.model.Registration;
import uk.ac.ucl.comp0010.model.Student;
import uk.ac.ucl.comp0010.repository.ModuleRepository;
import uk.ac.ucl.comp0010.repository.RegistrationRepository;
import uk.ac.ucl.comp0010.repository.StudentRepository;

/**
 * Service class for handling registration operations.
 */
@Service
public class RegistrationService {

  private final StudentRepository studentRepository;
  private final ModuleRepository moduleRepository;
  private final RegistrationRepository registrationRepository;

  /**
   * Constructor for RegistrationService.
   *
   * @param studentRepository the repository for student data
   * @param registrationRepository the repository for registration data
   * @param moduleRepository the repository for module data
   */
  public RegistrationService(StudentRepository studentRepository,
      RegistrationRepository registrationRepository, ModuleRepository moduleRepository) {
    this.studentRepository = studentRepository;
    this.registrationRepository = registrationRepository;
    this.moduleRepository = moduleRepository;
  }

  /**
   * Registers a student for a module. If the student is already registered for the module, the
   * existing registration is updated; otherwise, a new registration is created.
   *
   * @param studentId the ID of the student
   * @param moduleCode the code of the module
   * @return the registered module
   * @throws IllegalArgumentException if the student or module is not found
   */
  public Registration registerModule(Long studentId, String moduleCode) {
    Student student = studentRepository.findById(studentId)
        .orElseThrow(() -> new IllegalArgumentException("Student not found."));

    Module module = moduleRepository.findById(moduleCode)
        .orElseThrow(() -> new IllegalArgumentException("Module not found."));

    return registrationRepository.findByStudentAndModule(student, module)
        .map(existingRegistration -> {
          // Update the score of the existing grade
          return registrationRepository.save(existingRegistration);
        }).orElseGet(() -> {
          // Create a new grade if none exists
          Registration registration = new Registration();
          registration.setStudent(student);
          registration.setModule(module);
          return registrationRepository.save(registration);
        });
  }
}
