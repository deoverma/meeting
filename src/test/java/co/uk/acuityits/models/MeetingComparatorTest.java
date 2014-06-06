package co.uk.acuityits.models;

import com.uk.acuityits.utils.DateUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MeetingComparatorTest {

    List<MeetingRequest> requets = new ArrayList<MeetingRequest>();
    private String datString1 = "2011-03-17 10:17:06";
    private String datString2 = "2011-03-16 12:34:56";
    private String datString3 = "2011-03-16 09:28:23";
    
    @Before
    public void setup() throws ParseException {
        Date requestDate1 = DateUtils.convertStringToRequestDate(datString1);
        Date requestDate2 = DateUtils.convertStringToRequestDate(datString2);
        Date requestDate3 = DateUtils.convertStringToRequestDate(datString3);
        MeetingRequest request1 = new MeetingRequest(null, requestDate1, null, null);
        MeetingRequest request2 = new MeetingRequest(null, requestDate2, null, null);
        MeetingRequest request3 = new MeetingRequest(null, requestDate3, null, null);
        requets.add(request1);
        requets.add(request2);
        requets.add(request3 );
        
    }
    
    @Test
    public void employeesAreSortedByBookingRequestTime() {
        Collections.sort(requets, new MeetingRequestComparator());
        Assert.assertEquals(datString3, DateUtils
                .convertRequestDateToString(requets.get(0).getRequestDate()));
        Assert.assertEquals(datString2, DateUtils
                .convertRequestDateToString(requets.get(1).getRequestDate()));
        Assert.assertEquals(datString1, DateUtils
                .convertRequestDateToString(requets.get(2).getRequestDate()));
    }

}
