package uk.ac.ucl.comp0010.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import uk.ac.ucl.comp0010.exception.NoGradeAvailableException;
import uk.ac.ucl.comp0010.exception.NoRegistrationException;

/**
 * A class for student, containing id, firstName, lastName, userName, and email.
 */
@Entity
public class Student {

  @Id
  private long id;

  private String firstName;
  private String lastName;
  private String userName;
  private String email;

  @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Grade> grades = new ArrayList<>();

  @ManyToMany
  @JoinTable(name = "student_modules", joinColumns = @JoinColumn(name = "student_id"),
      inverseJoinColumns = @JoinColumn(name = "module_code"))
  private List<Registration> registrations = new ArrayList<>();

  /**
   * Constructor for instance of student.
   */
  public Student() {

  }

  /**
   * Constructor for instance of student.
   *
   * @param id - id of student
   * @param firstName - first name of student
   * @param lastName - last name of student
   * @param userName - user name of student
   * @param email - email of student
   */
  public Student(long id, String firstName, String lastName, String userName, String email) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.userName = userName;
    this.email = email;
  }

  /**
   * Method to get id of student.
   *
   * @return id of student
   */
  public long getId() {
    return id;
  }

  /**
   * Method to set id of student.
   *
   * @param id of student
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * Method to get first name of student.
   *
   * @return first name of student
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Method to set first name of student.
   *
   * @param firstName - first name of student
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Method to get last name of student.
   *
   * @return last name of student
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Method to set last name of student.
   *
   * @param lastName - last name of student
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Method to get user name of student.
   *
   * @return user name of student
   */
  public String getUserName() {
    return userName;
  }

  /**
   * Method to set user name of student.
   *
   * @param userName - user name of student
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }

  /**
   * Method to get email of student.
   *
   * @return email of student
   */
  public String getEmail() {
    return email;
  }

  /**
   * Method to set email of student.
   *
   * @param email of student
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Method to get list of grades of student.
   *
   * @return list of grades of student
   */
  public List<Grade> getGrades() {
    return grades;
  }

  /**
   * Method to get list of registration of student modules.
   *
   * @return list of registrations
   */
  public List<Registration> getRegistrations() {
    return registrations;
  }

  /**
   * Method to set list of registration of student modules.
   *
   * @param registrations - list of registrations
   */
  public void setRegistrations(List<Registration> registrations) {
    this.registrations = registrations;
  }

  /**
   * Method to add grade to student's grade list.
   *
   * @param grade - grade to add
   */
  public void addGrade(Grade grade) {
    grades.add(grade);
    grade.setStudent(this);
  }

  /**
   * Method to get grade of student for a module.
   *
   * @param module - module of student to retrieve grade
   * @return grade of student for the module
   * @throws NoRegistrationException when user try to access grades for unregistered modules
   * @throws NoGradeAvailableException when there is no grade available at all or not available for
   *         a specific module
   */
  public Grade getGrade(Module module) throws NoRegistrationException, NoGradeAvailableException {
    for (Registration registration : registrations) {
      if (registration.getModule() != module) {
        continue;
      } else {
        for (Grade grade : grades) {
          if (grade.getModule() == module) {
            return grade;
          }
        }
        throw new NoGradeAvailableException("No grades available");
      }
    }
    throw new NoRegistrationException("Module is not registered");
  }

  /**
   * Method to compute average score of student's grade.
   *
   * @return average score of student's grade
   * @throws NoGradeAvailableException when there is no grade available at all or not available for
   *         a specific module
   */
  public float computeAverage() throws NoGradeAvailableException {
    if (grades.isEmpty()) {
      throw new NoGradeAvailableException("No grades available");
    }

    float total = 0;
    for (Grade grade : grades) {
      total += grade.getScore();
    }

    return total / grades.size();
  }

  /**
   * Method to register student to a module.
   *
   * @param module to register student to
   */
  public void registerModule(Module module) {
    for (Registration registration : registrations) {
      if (registration.getModule() != module) {
        continue;
      } else {
        return;
      }
    }
    Registration newRegistration = new Registration(this, module);
    registrations.add(newRegistration);
  }
}
