package model;

import java.util.*;

public class Preferences extends LinkedHashMap<ParticipantAndEventId, Integer> {

    private static final long serialVersionUID = -7177001201934209651L;

    public Integer get(final ParticipantId participant, final EventId event) {
        return this.get(new ParticipantAndEventId(participant, event));
    }

    public Integer put(final int participant, final int event, final int preference) {
        return this.put(new ParticipantAndEventId(new ParticipantId(participant), new EventId(event)), preference);
    }

}
