package co.uk.acuityits.repository;

import co.uk.acuityits.models.MeetingRequest;
import co.uk.acuityits.models.MeetingRequestComparator;
import co.uk.acuityits.models.Slot;

import com.uk.acuityits.utils.DateUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.uk.acuityits.utils.DateUtils.convertStringToBookingDate;
import static com.uk.acuityits.utils.DateUtils.convertStringToRequestDate;

public class MeetingRepositoryTest {

    List<MeetingRequest> requests = new ArrayList<MeetingRequest>();


    private Repository repository;


    private MeetingRequest request1;


    private MeetingRequest request2;


    private MeetingRequest request3;


    private MeetingRequest request4;


    private MeetingRequest request5;

    @Before
    public void setup() throws ParseException {
        repository = new MeetingRepository(
                DateUtils.convertStringToWorkingHour("0900"),
                DateUtils.convertStringToWorkingHour("1730"));
        request1 = new MeetingRequest("EMP001",
                convertStringToRequestDate("2011-03-17 10:17:06"),
                convertStringToBookingDate("2011-03-21 10:00"), 2);

        request2 = new MeetingRequest("EMP002",
                convertStringToRequestDate("2011-03-16 12:34:56"),
                convertStringToBookingDate("2011-03-21 09:00"), 2);

        request3 = new MeetingRequest("EMP003",
                convertStringToRequestDate("2011-03-16 09:28:23"),
                convertStringToBookingDate("2011-03-22 14:00"), 2);

        request4 = new MeetingRequest("EMP004",
                convertStringToRequestDate("2011-03-17 11:23:45"),
                convertStringToBookingDate("2011-03-22 16:00"), 1);

        request5 = new MeetingRequest("EMP005",
                convertStringToRequestDate("2011-03-15 17:29:12"),
                convertStringToBookingDate("2011-03-21 16:00"), 3);

    }

    @Test
    public void addingNewRequestToAvailableSlotReturnsTrue() {
        repository.addRequest(request1);
        Map<Date, List<Slot>> bookings = repository.getBookings();
        Assert.assertEquals(1, bookings.values().size());
    }

    @Test
    public void addingNewRequestOutSideWorkingHoursShouldContainNoBookings() {
        repository.addRequest(request5);
        Map<Date, List<Slot>> bookings = repository.getBookings();
        Assert.assertEquals(0, bookings.values().size());
    }


    @Test
    public void addingNewRequestToFullyBookedSlotReturnOnlyFirstBookingRequest() {
        repository.addRequest(request2);
        repository.addRequest(request1);
        Map<Date, List<Slot>> bookings = repository.getBookings();
        Assert.assertEquals(1, bookings.values().size());

    }

    @Test
    public void addingNewRequestToAdjucentSlotReturnValidBookingRequest() {
        repository.addRequest(request3);
        repository.addRequest(request4);
        Map<Date, List<Slot>> bookings = repository.getBookings();
        Assert.assertEquals(1, bookings.values().size());

    }

    @Test
    public void addingNewRequestsInOrderOfRequestShouldReturnValidBookings() {
        List<MeetingRequest> requests = new ArrayList<MeetingRequest>();
        requests.add(request1);
        requests.add(request2);
        requests.add(request3);
        requests.add(request4);
        requests.add(request5);
        Collections.sort(requests, new MeetingRequestComparator());

        for (MeetingRequest request : requests) {
            repository.addRequest(request);
        }
        Map<Date, List<Slot>> bookings = repository.getBookings();
        Assert.assertEquals(2, bookings.values().size());
    }

    @Test
    public void addingNewRequestWithZeroDurationReturnsZeroBooking()
            throws ParseException {
        request1 = new MeetingRequest("EMP001",
                convertStringToRequestDate("2011-03-17 10:17:06"),
                convertStringToBookingDate("2011-03-21 09:00"), -10);
        repository.addRequest(request1);
        Map<Date, List<Slot>> bookings = repository.getBookings();
        Assert.assertEquals(0, bookings.values().size());
    }

    @Test
    public void addingNewRequestWithNegativeDurationReturnsZeroBooking()
            throws ParseException {
        request1 = new MeetingRequest("EMP001",
                convertStringToRequestDate("2011-03-17 10:17:06"),
                convertStringToBookingDate("2011-03-21 09:00"), -10);
        repository.addRequest(request1);
        Map<Date, List<Slot>> bookings = repository.getBookings();
        Assert.assertEquals(0, bookings.values().size());
    }
}
