package co.uk.acuityits.models;

import java.util.Comparator;

public class MeetingRequestComparator implements Comparator<MeetingRequest> {

    public int compare(MeetingRequest request, MeetingRequest anotherRequest) {
        return request.getRequestDate().compareTo(
                anotherRequest.getRequestDate());
    }

}
