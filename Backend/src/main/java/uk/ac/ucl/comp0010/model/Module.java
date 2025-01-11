package uk.ac.ucl.comp0010.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;

/**
 * A class for Module, containing a unique code, name, and mnc (mandatory non-condonable).
 */
@Entity
public class Module {

  @Id
  private String code;

  private String name;
  private Boolean mnc;

  @OneToMany(mappedBy = "module")
  List<Grade> grades;

  /**
   * A constructor for Module.
   */
  public Module() {

  }

  /**
   * A constructor for Module with code; name; mnc.
   *
   * @param code of module
   * @param name of module
   * @param mnc of module
   */
  public Module(String code, String name, Boolean mnc) {
    this.code = code;
    this.name = name;
    this.mnc = mnc;
  }

  /**
   * A method to get the code of module.
   *
   * @return code of module
   */
  public String getCode() {
    return code;
  }

  /**
   * A method to set the code of module.
   *
   * @param code to set for module code
   */
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * A method to get the name of module.
   *
   * @return name of module
   */
  public String getName() {
    return name;
  }

  /**
   * A method to set the name of module.
   *
   * @param name to set for module name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * A method to get the status of mnc of module.
   *
   * @return mnc of module: true if mandatory, false if not mandatory
   */
  public Boolean getMnc() {
    return mnc;
  }

  /**
   * A method to set the status of mnc of module.
   *
   * @param mnc to set for module mnc: true if mandatory, false if not mandatory
   */
  public void setMnc(Boolean mnc) {
    this.mnc = mnc;
  }
}
