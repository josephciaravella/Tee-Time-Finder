/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package joseph.ciaravella.TeeTimeFinder.model;
import java.sql.Time;
import java.sql.Date;

// line 24 "../../model.ump"
// line 66 "../../model.ump"
public class TeeTimeAvailability
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TeeTimeAvailability Attributes
  private int id;
  private String clubName;
  private String courseName;
  private Time availableTeeTime;
  private Date date;

  //TeeTimeAvailability Associations
  private CourseAdminAccount courseAdminAccount;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TeeTimeAvailability(int aId, String aClubName, String aCourseName, Time aAvailableTeeTime, Date aDate, CourseAdminAccount aCourseAdminAccount)
  {
    id = aId;
    clubName = aClubName;
    courseName = aCourseName;
    availableTeeTime = aAvailableTeeTime;
    date = aDate;
    if (!setCourseAdminAccount(aCourseAdminAccount))
    {
      throw new RuntimeException("Unable to create TeeTimeAvailability due to aCourseAdminAccount. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setClubName(String aClubName)
  {
    boolean wasSet = false;
    clubName = aClubName;
    wasSet = true;
    return wasSet;
  }

  public boolean setCourseName(String aCourseName)
  {
    boolean wasSet = false;
    courseName = aCourseName;
    wasSet = true;
    return wasSet;
  }

  public boolean setAvailableTeeTime(Time aAvailableTeeTime)
  {
    boolean wasSet = false;
    availableTeeTime = aAvailableTeeTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }

  public String getClubName()
  {
    return clubName;
  }

  public String getCourseName()
  {
    return courseName;
  }

  public Time getAvailableTeeTime()
  {
    return availableTeeTime;
  }

  public Date getDate()
  {
    return date;
  }
  /* Code from template association_GetOne */
  public CourseAdminAccount getCourseAdminAccount()
  {
    return courseAdminAccount;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setCourseAdminAccount(CourseAdminAccount aNewCourseAdminAccount)
  {
    boolean wasSet = false;
    if (aNewCourseAdminAccount != null)
    {
      courseAdminAccount = aNewCourseAdminAccount;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    courseAdminAccount = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "clubName" + ":" + getClubName()+ "," +
            "courseName" + ":" + getCourseName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "availableTeeTime" + "=" + (getAvailableTeeTime() != null ? !getAvailableTeeTime().equals(this)  ? getAvailableTeeTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "courseAdminAccount = "+(getCourseAdminAccount()!=null?Integer.toHexString(System.identityHashCode(getCourseAdminAccount())):"null");
  }
}