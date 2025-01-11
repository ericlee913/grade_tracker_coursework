package uk.ac.ucl.comp0010.dto;

/**
 * A class that gives the summary of total number of students, total number of modules, and overall
 * grade.
 */
public class SummaryDto {
  private long totalStudents;
  private long totalModules;
  private double overallGrade;

  /**
   * Constructor for new SummaryDto.
   *
   * @param totalStudents - total number of students
   * @param totalModules - total number of modules
   * @param overallGrade - overall grade
   */
  public SummaryDto(long totalStudents, long totalModules, double overallGrade) {
    this.totalStudents = totalStudents;
    this.totalModules = totalModules;
    this.overallGrade = overallGrade;
  }

  /**
   * A method to get the total number of students.
   *
   * @return total number of students
   */
  public long getTotalStudents() {
    return totalStudents;
  }

  /**
   * A method to set the total number of students.
   *
   * @param totalStudents - total number of students
   */
  public void setTotalStudents(long totalStudents) {
    this.totalStudents = totalStudents;
  }

  /**
   * A method to get the total number of modules.
   *
   * @return total number of modules
   */
  public long getTotalModules() {
    return totalModules;
  }

  /**
   * A method to set the total number of modules.
   *
   * @param totalModules - total number of modules
   */
  public void setTotalModules(long totalModules) {
    this.totalModules = totalModules;
  }

  /**
   * A method to get the overall grade.
   *
   * @return overall grade
   */
  public double getOverallGrade() {
    return overallGrade;
  }

  /**
   * A method to set the overall grade.
   *
   * @param overallGrade - overall grade
   */
  public void setOverallGrade(double overallGrade) {
    this.overallGrade = overallGrade;
  }
}
