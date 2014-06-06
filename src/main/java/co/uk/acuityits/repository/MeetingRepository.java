package co.uk.acuityits.repository;

import co.uk.acuityits.models.MeetingRequest;
import co.uk.acuityits.models.Slot;

import com.uk.acuityits.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static java.util.Calendar.HOUR_OF_DAY;

public class MeetingRepository implements Repository{

    SortedMap<Date, List<Slot>> bookings = new TreeMap<Date, List<Slot>>();

    private Calendar startWorkingHour;

    private Calendar endWorkignHour;

    public MeetingRepository(Date startWorkingHour, Date endWorkignHour) {
        this.startWorkingHour = new GregorianCalendar();
        this.startWorkingHour.setTime(startWorkingHour);
        this.endWorkignHour = new GregorianCalendar();
        this.endWorkignHour.setTime(endWorkignHour);
    }

    public boolean addRequest(MeetingRequest employee) {
        if (employee.getDuration() <= 0) {
            return false;
        }
        if (!isRequestedSlotAvailable(employee)) {
            return false;
        }

        Date bookingDate = employee.getBookingDate();
        Integer duration = employee.getDuration();
        Calendar calendar = new  GregorianCalendar();
        calendar.setTime(bookingDate);
        
        String employeeId = employee.getEmpolyeeId();
        Date startTime = employee.getBookingDate();
        do {
            calendar.add(HOUR_OF_DAY, 1);
            -- duration;
        } while (duration > 0);

        Date dateKey = DateUtils.convertDateWithoutHours(bookingDate);
        Date endTime = calendar.getTime();
        List<Slot> list = bookings.get(dateKey);
        if(list == null) {
            list = new ArrayList<Slot>();
        }
        list.add(new Slot(
                employeeId, startTime, endTime));
        bookings.put(dateKey, list);

        return true;
    }

    public Map<Date, List<Slot>> getBookings() {
        return bookings;
    }

    private boolean isRequestedSlotAvailable(MeetingRequest employee) {
        Date bookingDate = employee.getBookingDate();
        Integer duration = employee.getDuration();
        Calendar bookingEndTime = new  GregorianCalendar();
        Calendar bookingStartTime = new  GregorianCalendar();
        bookingStartTime.setTime(bookingDate);
        bookingEndTime.setTime(bookingDate);
        
        do {
            bookingEndTime.add(HOUR_OF_DAY, 1);
            --duration;
        } while (duration > 0);

        Date dateKey = DateUtils.convertDateWithoutHours(bookingDate);

        if (bookingStartTime.get(HOUR_OF_DAY) < startWorkingHour
                .get(HOUR_OF_DAY)
                || bookingEndTime.get(HOUR_OF_DAY) > endWorkignHour
                        .get(HOUR_OF_DAY)) {
            return false;
        }

        List<Slot> slots = bookings.get(dateKey);
        if (slots != null) {
            for (Slot slot : slots) {
                Calendar startCal = new GregorianCalendar();
                startCal.setTime(slot.getStartTime());
                Calendar endCal = new GregorianCalendar();
                endCal.setTime(slot.getEndTime());
                if (bookingStartTime.equals(startCal)
                        || (bookingStartTime.after(startCal) && bookingStartTime
                                .before(endCal))
                        || (bookingEndTime.after(startCal) && bookingEndTime
                                .before(endCal))) {
                    return false;
                }
            }
        }
        return true;
    }
}
