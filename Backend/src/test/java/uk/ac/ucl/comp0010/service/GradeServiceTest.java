package uk.ac.ucl.comp0010.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
 * Unit tests for GradeService class.
 */
public class GradeServiceTest {

  private StudentRepository studentRepository;
  private ModuleRepository moduleRepository;
  private GradeRepository gradeRepository;
  private RegistrationRepository registrationRepository;

  private GradeService gradeService;

  /**
   * Set up new GradeService instance and mock StudentRepository, Module Repository, and
   * GradeRepository for testing before each test case.
   */
  @BeforeEach
  public void setUp() {
    studentRepository = mock(StudentRepository.class);
    moduleRepository = mock(ModuleRepository.class);
    gradeRepository = mock(GradeRepository.class);
    registrationRepository = mock(RegistrationRepository.class);

    gradeService = new GradeService(studentRepository, gradeRepository, moduleRepository,
        registrationRepository);
  }

  /**
   * Tests addGrade method works when student is registered to module and valid score is provided.
   *
   * @throws NoRegistrationException when user try to access grades for unregistered modules
   */
  @Test
  public void testAddGradeSuccess() throws NoRegistrationException {
    Long studentId = 1L;
    String moduleCode = "COMP0010";
    int score = 70;

    Student mockStudent = new Student();
    mockStudent.setId(studentId);

    Module mockModule = new Module();
    mockModule.setCode(moduleCode);

    Registration mockRegistration = new Registration();
    mockRegistration.setStudent(mockStudent);
    mockRegistration.setModule(mockModule);

    Grade mockGrade = new Grade();
    mockGrade.setStudent(mockStudent);
    mockGrade.setModule(mockModule);
    mockGrade.setScore(score);

    when(studentRepository.findById(studentId)).thenReturn(Optional.of(mockStudent));
    when(moduleRepository.findById(moduleCode)).thenReturn(Optional.of(mockModule));
    when(registrationRepository.findByStudentAndModule(mockStudent, mockModule))
        .thenReturn(Optional.of(mockRegistration));
    when(gradeRepository.save(Mockito.any(Grade.class))).thenReturn(mockGrade);

    Grade result = gradeService.addGrade(studentId, moduleCode, score);

    assertNotNull(result);
    assertEquals(score, result.getScore());
    assertEquals(mockStudent, result.getStudent());
    assertEquals(mockModule, result.getModule());

    verify(studentRepository, times(1)).findById(studentId);
    verify(moduleRepository, times(1)).findById(moduleCode);
    verify(gradeRepository, times(1)).save(Mockito.any(Grade.class));
  }

  /**
   * Tests addGrade method does not work with score less than 0.
   */
  @Test
  public void testAddGradeInvalidScore() {
    Long studentId = 1L;
    String moduleCode = "COMP0010";
    int invalidScore = -10;

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> gradeService.addGrade(studentId, moduleCode, invalidScore));

    assertEquals("Score must be between 0 and 100.", exception.getMessage());

    verify(studentRepository, never()).findById(anyLong());
    verify(moduleRepository, never()).findById(anyString());
    verify(gradeRepository, never()).save(any(Grade.class));
  }

  /**
   * Tests addGrade method does not work when student Id cannot be found.
   */
  @Test
  public void testAddGradeStudentNotFound() {
    Long studentId = 999L;
    String moduleCode = "COMP0010";
    int score = 70;

    when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> gradeService.addGrade(studentId, moduleCode, score));

    assertEquals("Student not found.", exception.getMessage());
    verify(studentRepository, times(1)).findById(studentId);
    verify(moduleRepository, never()).findById(anyString());
    verify(gradeRepository, never()).save(any(Grade.class));
  }

  /**
   * Tests addGrade method does not work when module cannot be found.
   */
  @Test
  public void testAddGradeModuleNotFound() {
    Long studentId = 1L;
    String moduleCode = "INVALID_MODULE";
    int score = 70;

    Student mockStudent = new Student();
    mockStudent.setId(studentId);

    when(studentRepository.findById(studentId)).thenReturn(Optional.of(mockStudent));
    when(moduleRepository.findById(moduleCode)).thenReturn(Optional.empty());

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> gradeService.addGrade(studentId, moduleCode, score));

    assertEquals("Module not found.", exception.getMessage());
    verify(studentRepository, times(1)).findById(studentId);
    verify(moduleRepository, times(1)).findById(moduleCode);
    verify(gradeRepository, never()).save(any(Grade.class));
  }

  /**
   * Tests addGrade does not work when score is more than 100.
   */
  @Test
  public void testAddGradeScoreTooHigh() {
    Long studentId = 1L;
    String moduleCode = "COMP0010";
    int invalidScore = 101;

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> gradeService.addGrade(studentId, moduleCode, invalidScore));

    assertEquals("Score must be between 0 and 100.", exception.getMessage());
    verify(studentRepository, never()).findById(anyLong());
    verify(moduleRepository, never()).findById(anyString());
    verify(gradeRepository, never()).save(any(Grade.class));
  }

  /**
   * Tests addGrade method updates score with new value of score.
   *
   * @throws NoRegistrationException when user try to access grades for unregistered modules
   */
  @Test
  public void testAddGradeDuplicate() throws NoRegistrationException {
    Long studentId = 1L;
    String moduleCode = "COMP0010";
    int score1 = 90;
    int score2 = 70;

    Student mockStudent = mock(Student.class);
    Module mockModule = mock(Module.class);
    Grade mockGrade = mock(Grade.class);
    Registration mockRegistration = mock(Registration.class);

    when(studentRepository.findById(studentId)).thenReturn(Optional.of(mockStudent));
    when(moduleRepository.findById(moduleCode)).thenReturn(Optional.of(mockModule));
    when(registrationRepository.findByStudentAndModule(mockStudent, mockModule))
        .thenReturn(Optional.of(mockRegistration));
    when(gradeRepository.findByStudentAndModule(mockStudent, mockModule))
        .thenReturn(Optional.of(mockGrade));

    doAnswer(invocation -> {
      int newScore = invocation.getArgument(0);
      when(mockGrade.getScore()).thenReturn(newScore); // Simulate score update
      return null;
    }).when(mockGrade).setScore(Mockito.anyInt());

    when(gradeRepository.save(Mockito.any(Grade.class))).thenReturn(mockGrade);

    // Act
    Grade updatedGrade = gradeService.addGrade(studentId, moduleCode, score2);

    // Assert
    verify(mockGrade).setScore(score2); // Verify score update on the mock grade
    assertEquals(score2, updatedGrade.getScore(),
        "The grade score should be updated to the new score.");
    verify(gradeRepository, times(1)).save(mockGrade);

  }
}
