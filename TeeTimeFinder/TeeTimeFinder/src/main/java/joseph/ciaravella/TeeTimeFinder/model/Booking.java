package joseph.ciaravella.TeeTimeFinder.model;
import java.sql.Date;

import jakarta.persistence.*;
import joseph.ciaravella.TeeTimeFinder.model.keys.BookingId;


@Entity
@Table(name = "booking")
public class Booking
{

  //Booking Attributes
  @EmbeddedId
  private BookingId id;
  private Date bookingDate;

  //Booking Associations
  @ManyToOne
  @MapsId("customerAccountId")
  @JoinColumn(name = "customer_account_id")
  private CustomerAccount customerAccount;

  @ManyToOne
  @MapsId("teeTimeAvailabilityId")
  @JoinColumn(name = "tee_time_availability_id")
  private TeeTimeAvailability teeTimeAvailability;

  public Booking() {}

  public Booking(Date aBookingDate, CustomerAccount aCustomerAccount, TeeTimeAvailability aTeeTimeAvailability)
  {
    this.bookingDate = aBookingDate;
    this.customerAccount = aCustomerAccount;
    this.teeTimeAvailability = aTeeTimeAvailability;
    this.id = new BookingId(aCustomerAccount.getId(), aTeeTimeAvailability.getId());

    if (!setCustomerAccount(aCustomerAccount))
    {
      throw new RuntimeException("Unable to create Booking due to aCustomerAccount. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setTeeTimeAvailability(aTeeTimeAvailability))
    {
      throw new RuntimeException("Unable to create Booking due to aTeeTimeAvailability. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }


  public BookingId getId() { return this.id; }

  public Date getBookingDate() { return this.bookingDate; }

  public CustomerAccount getCustomerAccount() { return this.customerAccount; }
  
  public TeeTimeAvailability getTeeTimeAvailability() { return this.teeTimeAvailability; }
  
  
  public boolean setId(BookingId aId)
  {
    boolean wasSet = false;
    this.id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setBookingDate(Date aBookingDate)
  {
    boolean wasSet = false;
    this.bookingDate = aBookingDate;
    wasSet = true;
    return wasSet;
  }
  
  public boolean setCustomerAccount(CustomerAccount aNewCustomerAccount)
  {
    boolean wasSet = false;
    if (aNewCustomerAccount != null)
    {
      this.customerAccount = aNewCustomerAccount;
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
      this.teeTimeAvailability = aNewTeeTimeAvailability;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    customerAccount = null;
    teeTimeAvailability = null;
  }


  // public String toString()
  // {
  //   return super.toString() + "["+
  //           "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
  //           "  " + "bookingDate" + "=" + (getBookingDate() != null ? !getBookingDate().equals(this)  ? getBookingDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
  //           "  " + "customerAccount = "+(getCustomerAccount()!=null?Integer.toHexString(System.identityHashCode(getCustomerAccount())):"null") + System.getProperties().getProperty("line.separator") +
  //           "  " + "teeTimeAvailability = "+(getTeeTimeAvailability()!=null?Integer.toHexString(System.identityHashCode(getTeeTimeAvailability())):"null");
  // }
}