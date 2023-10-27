package control;

import java.util.*;

import org.testng.*;
import org.testng.annotations.Test;

import model.*;

public class MainTest {

    @Test
    public void computeAssignmentFor4ParticipantsAnd2Events() {
        final Participants participants = new Participants();
        participants.put(0, "Albert");
        participants.put(1, "Berta");
        participants.put(2, "Charlie");
        participants.put(3, "Doris");
        final Events events = new Events();
        events.put(0, "Slot 1");
        events.put(1, "Slot 2");
        final Preferences preferences = new Preferences();
        preferences.put(0, 0, 1);
        preferences.put(0, 1, 4);
        preferences.put(1, 0, 1);
        preferences.put(1, 1, 4);
        preferences.put(2, 0, 1);
        preferences.put(2, 1, 4);
        preferences.put(3, 0, 4);
        preferences.put(3, 1, 1);
        final FlowNetwork network = new FlowNetwork(participants, events, preferences, 1);
        network.fordFulkerson();
        final Assignment actual = network.getAssignment();
        Assert.assertEquals(actual.get(new ParticipantId(3)), new EventId(1));
        final Map<EventId, Integer> load = actual.getEventLoad();
        Assert.assertEquals(load.get(new EventId(0)), Integer.valueOf(2));
        Assert.assertEquals(load.get(new EventId(1)), Integer.valueOf(2));
    }

}
