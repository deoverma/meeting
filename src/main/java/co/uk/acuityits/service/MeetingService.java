package co.uk.acuityits.service;

import co.uk.acuityits.models.MeetingRequest;
import co.uk.acuityits.models.MeetingRequestComparator;
import co.uk.acuityits.models.Slot;
import co.uk.acuityits.models.SlotComparator;
import co.uk.acuityits.repository.MeetingRepository;
import co.uk.acuityits.repository.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.uk.acuityits.utils.DateUtils.convertBookingDateToString;
import static com.uk.acuityits.utils.DateUtils.convertDateToWorkingHourString;
import static com.uk.acuityits.utils.DateUtils.convertStringToBookingDate;
import static com.uk.acuityits.utils.DateUtils.convertStringToRequestDate;
import static com.uk.acuityits.utils.DateUtils.convertStringToWorkingHour;
import static java.lang.Integer.parseInt;

public class MeetingService {

    Repository repository;

    public static void main(String[] a) throws ParseException {
        if(a == null || a.length == 0) {
            System.out
                    .println("Usage: For more tests; please change the input in meeting-request.txt file.");
        }
        File file = new File("meeting-request.txt");
        new MeetingService().run(file);
    }
    
    public void run(File file) throws ParseException {

        List<MeetingRequest> requests = processFile(file);
        
        Collections.sort(requests, new MeetingRequestComparator());

        for (MeetingRequest request : requests) {
            repository.addRequest(request);
        }

        Map<Date, List<Slot>> bookings = repository.getBookings();

        for (Date dateKey : bookings.keySet()) {
            List<Slot> slots = bookings.get(dateKey);
            Collections.sort(slots, new SlotComparator());
            System.out.println(convertBookingDateToString(dateKey));
            for (Slot slot : slots) {
                System.out.println(convertDateToWorkingHourString(slot
                        .getStartTime())
                        + " "
                        + convertDateToWorkingHourString(slot.getEndTime())
                        + " " + slot.getEmployeeId());
            }
        }
    }

    private List<MeetingRequest> processFile(File file)
            throws ParseException {
        List<MeetingRequest> requests = new ArrayList<MeetingRequest>();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(
                    file));
            String currentLine;
            String requestDate = null;
            String empolyeeId = null;
            String bookingDate = null;
            String duration = null;
            boolean construtEmployeePart1 = false;
            boolean construtEmployeePart2 = false;

            while ((currentLine = bufferedReader.readLine()) != null) {

                if (currentLine.matches("^\\d{4} \\d{4}$")) {
                    String[] hours = currentLine.split(" ");
                    Date startWorkingHour = convertStringToWorkingHour(hours[0]);
                    Date endWorkignHour = convertStringToWorkingHour(hours[1]);
                    repository = new MeetingRepository(startWorkingHour,
                            endWorkignHour);
                }

                if (currentLine
                        .matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} \\w+$")) {
                    int lastIndexOf = currentLine.lastIndexOf(" ");
                    requestDate = currentLine.substring(0, lastIndexOf);
                    empolyeeId = currentLine.substring(lastIndexOf + 1);
                    construtEmployeePart1 = true;
                }

                if (currentLine
                        .matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2} \\d$")) {
                    int lastIndexOf = currentLine.lastIndexOf(" ");
                    bookingDate = currentLine.substring(0, lastIndexOf);
                    duration = currentLine.substring(lastIndexOf + 1);
                    construtEmployeePart2 = true;
                }

                if (construtEmployeePart1 && construtEmployeePart2) {
                    requests.add(new MeetingRequest(empolyeeId,
                            convertStringToRequestDate(requestDate),
                            convertStringToBookingDate(bookingDate),
                            parseInt(duration)));
                    construtEmployeePart1 = false;
                    construtEmployeePart2 = false;
                    requestDate = null;
                    empolyeeId = null;
                    bookingDate = null;
                    duration = null;

                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return requests;
    }
}
