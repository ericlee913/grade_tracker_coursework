package uk.ac.ucl.comp0010.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ucl.comp0010.dto.SummaryDto;
import uk.ac.ucl.comp0010.repository.GradeRepository;
import uk.ac.ucl.comp0010.repository.ModuleRepository;
import uk.ac.ucl.comp0010.repository.StudentRepository;

/**
 * Service class for managing summary of student, grade and module.
 */
@Service
public class SummaryService {

  private final StudentRepository studentRepository;
  private final GradeRepository gradeRepository;
  private final ModuleRepository moduleRepository;

  /**
   * Constructor of instance of SummaryService.
   *
   * @param studentRepository - repository of student.
   * @param gradeRepository - repository of grade.
   * @param moduleRepository - repository of module.
   */
  @Autowired
  public SummaryService(StudentRepository studentRepository, GradeRepository gradeRepository,
      ModuleRepository moduleRepository) {
    this.studentRepository = studentRepository;
    this.gradeRepository = gradeRepository;
    this.moduleRepository = moduleRepository;
  }

  /**
   * Method to get summary data of student, module, and grade.
   *
   * @return a SummaryDto object with the summary data
   */
  public SummaryDto getSummaryData() {
    long totalStudents = studentRepository.count();
    long totalModules = moduleRepository.count();
    Double overallGrade = gradeRepository.calculateAverageGrade();

    if (overallGrade == null) {
      overallGrade = 0.0;
    }
    return new SummaryDto(totalStudents, totalModules, overallGrade);
  }
}
