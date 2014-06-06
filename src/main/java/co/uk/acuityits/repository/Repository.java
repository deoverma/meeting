package co.uk.acuityits.repository;

import co.uk.acuityits.models.MeetingRequest;
import co.uk.acuityits.models.Slot;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface Repository {

    boolean addRequest(MeetingRequest employee);

    Map<Date, List<Slot>> getBookings();
}
