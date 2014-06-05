package co.uk.acuityits.repository;

public class CalendarRepository implements Repository{

    
    @Override
    public boolean addRequest() {
        return false;
    }

    @Override
    public boolean isSlotAvailable() {
        return false;
    }

}
