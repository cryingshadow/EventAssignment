package model;

import java.util.*;

/**
 * LinkedHashMap from ParticipantId to EventId.
 * @author cryingshadow
 */
public class Assignment extends LinkedHashMap<ParticipantId, EventId> {

    private static final long serialVersionUID = -7118032527198803847L;

    public Assignment() {
        super();
    }

    public Assignment(final Map<ParticipantId, EventId> map) {
        super(map);
    }

    public Map<EventId, Integer> getEventLoad() {
        final Map<EventId, Integer> result = new LinkedHashMap<>();
        for (final Map.Entry<ParticipantId, EventId> entry : this.entrySet()) {
            final EventId event = entry.getValue();
            if (!result.containsKey(event)) {
                result.put(event, 0);
            }
            result.put(event, result.get(event) + 1);
        }
        return result;
    }

}
