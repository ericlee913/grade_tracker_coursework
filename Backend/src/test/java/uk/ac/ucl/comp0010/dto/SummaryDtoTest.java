package uk.ac.ucl.comp0010.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for SummaryDto class.
 * 
 */
public class SummaryDtoTest {

  private SummaryDto summaryDto;

  /**
   * Set up instance of summaryDto with initial values before each test.
   */
  @BeforeEach
  public void setUp() {
    summaryDto = new SummaryDto(0, 0, 0.0);
  }

  /**
   * Tests setTotalStudents and getTotalStudents methods.
   */
  @Test
  public void testSetTotalStudents() {
    summaryDto.setTotalStudents(100);
    assertEquals(100, summaryDto.getTotalStudents());
  }

  /**
   * Tests setTotalModules and getTotalModules method.
   */
  @Test
  public void testSetTotalModules() {
    summaryDto.setTotalModules(10);
    assertEquals(10, summaryDto.getTotalModules());
  }

  /**
   * Tests setOverallGrade and getOverallGrade method.
   */
  @Test
  public void testSetOverallGrade() {
    summaryDto.setOverallGrade(85.5);
    assertEquals(85.5, summaryDto.getOverallGrade());
  }
}
