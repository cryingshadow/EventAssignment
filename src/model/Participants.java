package model;

import java.util.*;

public class Participants extends LinkedHashMap<ParticipantId, String> {

    private static final long serialVersionUID = -243031049954989116L;

    public String put(final int id, final String participant) {
        return this.put(new ParticipantId(id), participant);
    }

}
