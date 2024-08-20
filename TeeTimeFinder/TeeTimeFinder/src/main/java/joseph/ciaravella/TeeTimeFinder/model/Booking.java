/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/

package joseph.ciaravella.TeeTimeFinder.model;
import java.sql.Date;

// line 35 "../../model.ump"
// line 71 "../../model.ump"
public class Booking
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Booking Attributes
  private Date bookingDate;

  //Booking Associations
  private CustomerAccount customerAccount;
  private TeeTimeAvailability teeTimeAvailability;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Booking(Date aBookingDate, CustomerAccount aCustomerAccount, TeeTimeAvailability aTeeTimeAvailability)
  {
    bookingDate = aBookingDate;
    if (!setCustomerAccount(aCustomerAccount))
    {
      throw new RuntimeException("Unable to create Booking due to aCustomerAccount. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setTeeTimeAvailability(aTeeTimeAvailability))
    {
      throw new RuntimeException("Unable to create Booking due to aTeeTimeAvailability. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setBookingDate(Date aBookingDate)
  {
    boolean wasSet = false;
    bookingDate = aBookingDate;
    wasSet = true;
    return wasSet;
  }

  public Date getBookingDate()
  {
    return bookingDate;
  }
  /* Code from template association_GetOne */
  public CustomerAccount getCustomerAccount()
  {
    return customerAccount;
  }
  /* Code from template association_GetOne */
  public TeeTimeAvailability getTeeTimeAvailability()
  {
    return teeTimeAvailability;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setCustomerAccount(CustomerAccount aNewCustomerAccount)
  {
    boolean wasSet = false;
    if (aNewCustomerAccount != null)
    {
      customerAccount = aNewCustomerAccount;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setTeeTimeAvailability(TeeTimeAvailability aNewTeeTimeAvailability)
  {
    boolean wasSet = false;
    if (aNewTeeTimeAvailability != null)
    {
      teeTimeAvailability = aNewTeeTimeAvailability;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    customerAccount = null;
    teeTimeAvailability = null;
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "bookingDate" + "=" + (getBookingDate() != null ? !getBookingDate().equals(this)  ? getBookingDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "customerAccount = "+(getCustomerAccount()!=null?Integer.toHexString(System.identityHashCode(getCustomerAccount())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "teeTimeAvailability = "+(getTeeTimeAvailability()!=null?Integer.toHexString(System.identityHashCode(getTeeTimeAvailability())):"null");
  }
}