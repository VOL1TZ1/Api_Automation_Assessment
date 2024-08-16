import java.util.HashMap;
import java.util.Map;

public class BookingData {
        private String firstname;
        private String lastname;
        private int totalprice;
        private boolean depositpaid;
        Map<String, String> bookingDates = new HashMap<>();

    public void setBookingDates(Map<String, String> bookingDates) {
        this.bookingDates = bookingDates;
    }

    public void setDepositpaid(boolean depositpaid) {
        this.depositpaid = depositpaid;
    }

    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
}


