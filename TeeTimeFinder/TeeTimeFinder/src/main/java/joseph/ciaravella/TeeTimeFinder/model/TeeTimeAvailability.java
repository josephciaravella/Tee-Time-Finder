package joseph.ciaravella.TeeTimeFinder.model;

import java.sql.Date;
import java.sql.Time;

import jakarta.persistence.*;


@Entity
@Table(name = "teetimeavailability")
public class TeeTimeAvailability
{

  @Id
  @GeneratedValue
  private Integer id;
  private String clubName;
  private String courseName;
  private Date date;
  private Time time;

  @ManyToOne
  private CourseAdminAccount courseAdminAccount;

  public TeeTimeAvailability() {}
  
  public TeeTimeAvailability(Integer aId, String aClubName, String aCourseName, Date aDate, Time aTime, CourseAdminAccount aCourseAdminAccount)
  {
    id = aId;
    clubName = aClubName;
    courseName = aCourseName;
    date = aDate;
    time = aTime;
    if (!setCourseAdminAccount(aCourseAdminAccount))
    {
      throw new RuntimeException("Unable to create TeeTimeAvailability due to aCourseAdminAccount. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }


  public boolean setId(Integer aId)
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

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setTime(Time aTime)
  {
    boolean wasSet = false;
    time = aTime;
    wasSet = true;
    return wasSet;
  }

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


  public Integer getId() { return id; }

  public String getClubName() { return clubName; }

  public String getCourseName() { return courseName; }

  public Date getDate() { return date; }

  public Time getTime() { return time; }
  
  public CourseAdminAccount getCourseAdminAccount() { return courseAdminAccount; }


  public void delete()
  {
    courseAdminAccount = null;
  }


  // public String toString()
  // {
  //   return super.toString() + "["+
  //           "id" + ":" + getId()+ "," +
  //           "clubName" + ":" + getClubName()+ "," +
  //           "courseName" + ":" + getCourseName()+ "]" + System.getProperties().getProperty("line.separator") +
  //           "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
  //           "  " + "time" + "=" + (getTime() != null ? !getTime().equals(this)  ? getTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
  //           "  " + "courseAdminAccount = "+(getCourseAdminAccount()!=null?Integer.toHexString(System.identityHashCode(getCourseAdminAccount())):"null");
  // }
}