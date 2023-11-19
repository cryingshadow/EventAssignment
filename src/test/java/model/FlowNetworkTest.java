package model;

import java.util.*;

import org.testng.*;
import org.testng.annotations.Test;

public class FlowNetworkTest {

    @Test
    public void computeFreeEvents1of4() {
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
        final Assignment assignment = network.getAssignment();
        assignment.put(new ParticipantId(0), new EventId(0));
        final Set<EventId> actual = network.computeFreeEvents();
        Assert.assertEquals(actual.size(), 1);
        Assert.assertTrue(actual.contains(new EventId(1)));
    }

    @Test
    public void computeFreeEvents3of4() {
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
        final Assignment assignment = network.getAssignment();
        assignment.put(new ParticipantId(0), new EventId(0));
        assignment.put(new ParticipantId(1), new EventId(0));
        assignment.put(new ParticipantId(3), new EventId(1));
        final Set<EventId> actual = network.computeFreeEvents();
        Assert.assertEquals(actual.size(), 1);
        Assert.assertTrue(actual.contains(new EventId(1)));
    }

}
