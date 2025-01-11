package uk.ac.ucl.comp0010.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import uk.ac.ucl.comp0010.dto.SummaryDto;
import uk.ac.ucl.comp0010.repository.GradeRepository;
import uk.ac.ucl.comp0010.repository.ModuleRepository;
import uk.ac.ucl.comp0010.repository.StudentRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for SummaryService class.
 */
public class SummaryServiceTest {

  private StudentRepository studentRepository;
  private ModuleRepository moduleRepository;
  private GradeRepository gradeRepository;

  private SummaryService summaryService;

  /**
   * Set up new SummaryService instance and mock StudentRepository, Module Repository, and
   * GradeRepository for testing before each test case.
   */
  @BeforeEach
  public void setUp() {
    studentRepository = mock(StudentRepository.class);
    moduleRepository = mock(ModuleRepository.class);
    gradeRepository = mock(GradeRepository.class);
    summaryService = new SummaryService(studentRepository, gradeRepository, moduleRepository);
  }

  /**
   * Tests the getSummaryData method works.
   */
  @Test
  public void testGetSummaryDataSuccess() {
    when(studentRepository.count()).thenReturn(50L);
    when(moduleRepository.count()).thenReturn(10L);
    when(gradeRepository.calculateAverageGrade()).thenReturn(75.5);

    SummaryDto summaryDto = summaryService.getSummaryData();

    assertNotNull(summaryDto);
    assertEquals(50L, summaryDto.getTotalStudents());
    assertEquals(10L, summaryDto.getTotalModules());
    assertEquals(75.5, summaryDto.getOverallGrade());

    verify(studentRepository, times(1)).count();
    verify(moduleRepository, times(1)).count();
    verify(gradeRepository, times(1)).calculateAverageGrade();
  }

  /**
   * Tests the getSummaryData with no grades.
   */
  @Test
  public void testGetSummaryDataNoGrades() {
    when(studentRepository.count()).thenReturn(30L);
    when(moduleRepository.count()).thenReturn(5L);
    when(gradeRepository.calculateAverageGrade()).thenReturn(null);

    SummaryDto summaryDto = summaryService.getSummaryData();

    assertNotNull(summaryDto);
    assertEquals(30L, summaryDto.getTotalStudents());
    assertEquals(5L, summaryDto.getTotalModules());
    assertEquals(0.0, summaryDto.getOverallGrade());

    verify(studentRepository, times(1)).count();
    verify(moduleRepository, times(1)).count();
    verify(gradeRepository, times(1)).calculateAverageGrade();
  }

  /**
   * Tests the getSummaryData method works with empty repositories.
   */
  @Test
  public void testGetSummaryDataEmptyRepositories() {
    when(studentRepository.count()).thenReturn(0L);
    when(moduleRepository.count()).thenReturn(0L);
    when(gradeRepository.calculateAverageGrade()).thenReturn(null);

    SummaryDto summaryDto = summaryService.getSummaryData();

    assertNotNull(summaryDto);
    assertEquals(0L, summaryDto.getTotalStudents());
    assertEquals(0L, summaryDto.getTotalModules());
    assertEquals(0.0, summaryDto.getOverallGrade());

    verify(studentRepository, times(1)).count();
    verify(moduleRepository, times(1)).count();
    verify(gradeRepository, times(1)).calculateAverageGrade();
  }
}
