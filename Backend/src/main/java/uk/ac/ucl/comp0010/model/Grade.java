package uk.ac.ucl.comp0010.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * A class for Grade, containing a unique id, module, and student.
 */
@Entity
public class Grade {

  @Id
  @GeneratedValue
  Long id;

  private int score;

  @ManyToOne
  @JoinColumn(name = "code")
  private Module module;

  @ManyToOne
  @JoinColumn(name = "student_id")
  @JsonIgnoreProperties({"grades"})
  private Student student;

  /**
   * Constructor for Grade.
   */
  public Grade() {

  }

  /**
   * Constructor for Grade with score and module.
   *
   * @param score of grade
   * @param module of grade
   */
  public Grade(int score, Module module) {
    this.setScore(score);
    this.module = module;
  }

  /**
   * Method to get score of grade.
   *
   * @return score of grade
   */
  public int getScore() {
    return score;
  }

  /**
   * Method to set score of grade.
   *
   * @param score of grade
   */
  public void setScore(int score) {
    this.score = score;
  }

  /**
   * Method to get id of grade.
   *
   * @return id of grade
   */
  public Long getId() {
    return id;
  }

  /**
   * Method to set id of grade.
   *
   * @param id of grade
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Method to get module of grade.
   *
   * @return module of grade
   */
  public Module getModule() {
    return module;
  }

  /**
   * Method to set module of grade.
   *
   * @param module of grade.
   */
  public void setModule(Module module) {
    this.module = module;
  }

  /**
   * Method to get student of grade.
   *
   * @return student of grade
   */
  public Student getStudent() {
    return student;
  }

  /**
   * Method to set student of grade.
   *
   * @param student of grade
   */
  public void setStudent(Student student) {
    this.student = student;
  }
}
