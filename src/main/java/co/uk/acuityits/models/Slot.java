package co.uk.acuityits.models;

import java.util.Date;

public class Slot {

    private Date startTime;

    private Date endTime;

    private String employeeId;

    public Slot(String employeeId, Date startTime, Date endTime) {
        super();
        this.startTime = startTime;
        this.employeeId = employeeId;
        this.endTime = endTime;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }


}
