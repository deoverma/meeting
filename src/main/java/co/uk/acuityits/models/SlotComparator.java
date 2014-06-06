package co.uk.acuityits.models;

import java.util.Comparator;

public class SlotComparator implements Comparator<Slot> {

    public int compare(Slot slot, Slot anotherSlot) {
        return slot.getStartTime().compareTo(anotherSlot.getStartTime());
    }

}
