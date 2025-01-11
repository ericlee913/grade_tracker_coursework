package uk.ac.ucl.comp0010.service;

import org.springframework.stereotype.Service;
import uk.ac.ucl.comp0010.exception.NoRegistrationException;
import uk.ac.ucl.comp0010.model.Grade;
import uk.ac.ucl.comp0010.model.Module;
import uk.ac.ucl.comp0010.model.Registration;
import uk.ac.ucl.comp0010.model.Student;
import uk.ac.ucl.comp0010.repository.GradeRepository;
import uk.ac.ucl.comp0010.repository.ModuleRepository;
import uk.ac.ucl.comp0010.repository.RegistrationRepository;
import uk.ac.ucl.comp0010.repository.StudentRepository;

/**
 * Service class for managing grades.
 */
@Service
public class GradeService {

  private final StudentRepository studentRepository;
  private final ModuleRepository moduleRepository;
  private final GradeRepository gradeRepository;
  private final RegistrationRepository registrationRepository;

  /**
   * Constructor for an instance of GradeService.
   *
   * @param studentRepository repository for students
   * @param gradeRepository repository for grades
   * @param moduleRepository repository for modules
   * @param registrationRepository repository for registrations
   */
  public GradeService(
      StudentRepository studentRepository,
      GradeRepository gradeRepository,
      ModuleRepository moduleRepository,
      RegistrationRepository registrationRepository
  ) {
    this.studentRepository = studentRepository;
    this.gradeRepository = gradeRepository;
    this.moduleRepository = moduleRepository;
    this.registrationRepository = registrationRepository;
  }

  /**
   * Adds or updates a grade for a student in a specific module.
   *
   * @param studentId the ID of the student
   * @param moduleCode the code of the module
   * @param score the grade score (0-100)
   * @return the saved Grade entity
   * @throws NoRegistrationException if no registration exists for the student and module
   * @throws IllegalArgumentException if the score is invalid, or the student/module is not found
   */
  public Grade addGrade(Long studentId, String moduleCode, int score)
      throws NoRegistrationException {
    if (score < 0 || score > 100) {
      throw new IllegalArgumentException("Score must be between 0 and 100.");
    }

    Student student = studentRepository.findById(studentId)
        .orElseThrow(() -> new IllegalArgumentException("Student not found."));

    Module module = moduleRepository.findById(moduleCode)
        .orElseThrow(() -> new IllegalArgumentException("Module not found."));

    Registration registration = registrationRepository.findByStudentAndModule(student, module)
        .orElseThrow(() -> new NoRegistrationException("Registration not found."));

    return gradeRepository.findByStudentAndModule(student, module)
        .map(existingGrade -> {
          existingGrade.setScore(score);
          return gradeRepository.save(existingGrade);
        }).orElseGet(() -> {
          Grade grade = new Grade();
          grade.setStudent(student);
          grade.setModule(module);
          grade.setScore(score);
          return gradeRepository.save(grade);
        });
  }
}
