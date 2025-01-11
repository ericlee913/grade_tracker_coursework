package uk.ac.ucl.comp0010.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * A class for registration of student to module.
 */
@Entity
public class Registration {
  
  @Id
  @GeneratedValue
  private long id;
  
  @ManyToOne
  @JoinColumn(name = "student_id")
  private Student student;

  @ManyToOne
  @JoinColumn(name = "code")
  private Module module;

  /**
   * Constructor for instance of registration.
   */
  public Registration() {
    
  }

  /**
   * Constructor for instance of registration.
   *
   * @param student - student to register
   * @param module - module to register student to
   */
  public Registration(Student student, Module module) {
    this.student = student;
    this.module = module;
  }

  /**
   * Method to get unique id.
   *
   * @return id of registration
   */
  public long getId() {
    return id;
  }

  /**
   * Method to set id.
   *
   * @param id of registration
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * Method to get student to register.
   *
   * @return student to register
   */
  public Student getStudent() {
    return student;
  }

  /**
   * Method to set student to register.
   *
   * @param student to register
   */
  public void setStudent(Student student) {
    this.student = student;
  }

  /**
   * Method to get module to register student to.
   *
   * @return module to register student to
   */
  public Module getModule() {
    return module;
  }

  /**
   * Method to set module to register student to.
   *
   * @param module to register student to
   */
  public void setModule(Module module) {
    this.module = module;
  }
}
