package co.uk.acuityits.models;

import java.util.Date;

public class MeetingRequest {

    private String empolyeeId;
    
    private Date requestDate;
    
    private Date bookingDate;
    
    private Integer duration;

    public MeetingRequest(String empolyeeId, Date requestDate, Date bookingDate,
            Integer duration) {
        super();
        this.empolyeeId = empolyeeId;
        this.requestDate = requestDate;
        this.bookingDate = bookingDate;
        this.duration = duration;
    }

    public String getEmpolyeeId() {
        return empolyeeId;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public Integer getDuration() {
        return duration;
    }
    
    
}
