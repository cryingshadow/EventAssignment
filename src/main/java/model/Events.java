package model;

import java.util.*;

public class Events extends LinkedHashMap<EventId, String> {

    private static final long serialVersionUID = 3512505320291826003L;

    public String put(final int id, final String event) {
        return this.put(new EventId(id), event);
    }

}
