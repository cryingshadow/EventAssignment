package model;

import java.util.*;

import org.testng.*;
import org.testng.annotations.Test;

public class ResidualNetworkTest {

    @Test
    public void bestAugmentingAssignmentIsNotPreferred() {
        final int numberOfEvents = 2;
        final Set<ParticipantId> unassignedParticipants = new LinkedHashSet<>();
        unassignedParticipants.add(new ParticipantId(2));
        final Preferences preferences = new Preferences();
        preferences.put(0, 0, 1);
        preferences.put(0, 1, 4);
        preferences.put(1, 0, 1);
        preferences.put(1, 1, 4);
        preferences.put(2, 0, 1);
        preferences.put(2, 1, 4);
        preferences.put(3, 0, 4);
        preferences.put(3, 1, 1);
        final Set<EventId> freeEvents = new LinkedHashSet<>();
        freeEvents.add(new EventId(1));
        final Assignment currentAssignment = new Assignment();
        currentAssignment.put(new ParticipantId(0), new EventId(0));
        currentAssignment.put(new ParticipantId(1), new EventId(0));
        currentAssignment.put(new ParticipantId(3), new EventId(1));
        final ResidualNetwork network =
            new ResidualNetwork(numberOfEvents, unassignedParticipants, preferences, freeEvents, currentAssignment);
        final Optional<Assignment> optional = network.getBestAugmentingAssignment();
        Assert.assertTrue(optional.isPresent());
        final Assignment actual = optional.get();
        Assert.assertEquals(actual.size(), 1);
        Assert.assertEquals(actual.get(new ParticipantId(2)), new EventId(1));
    }

    @Test
    public void bestAugmentingAssignmentNeedsBacktracking() {
        final int numberOfEvents = 2;
        final Set<ParticipantId> unassignedParticipants = new LinkedHashSet<>();
        unassignedParticipants.add(new ParticipantId(2));
        final Preferences preferences = new Preferences();
        preferences.put(0, 0, 1);
        preferences.put(0, 1, 4);
        preferences.put(1, 0, 1);
        preferences.put(1, 1, 3);
        preferences.put(2, 0, 1);
        preferences.put(2, 1, 4);
        preferences.put(3, 0, 4);
        preferences.put(3, 1, 1);
        final Set<EventId> freeEvents = new LinkedHashSet<>();
        freeEvents.add(new EventId(1));
        final Assignment currentAssignment = new Assignment();
        currentAssignment.put(new ParticipantId(0), new EventId(0));
        currentAssignment.put(new ParticipantId(1), new EventId(0));
        currentAssignment.put(new ParticipantId(3), new EventId(1));
        final ResidualNetwork network =
            new ResidualNetwork(numberOfEvents, unassignedParticipants, preferences, freeEvents, currentAssignment);
        final Optional<Assignment> optional = network.getBestAugmentingAssignment();
        Assert.assertTrue(optional.isPresent());
        final Assignment actual = optional.get();
        Assert.assertEquals(actual.size(), 2);
        Assert.assertEquals(actual.get(new ParticipantId(2)), new EventId(0));
        Assert.assertEquals(actual.get(new ParticipantId(1)), new EventId(1));
    }

    @Test
    public void bestAugmentingAssignmentUsesTolerance() {
        final int numberOfEvents = 2;
        final Set<ParticipantId> unassignedParticipants = new LinkedHashSet<>();
        unassignedParticipants.add(new ParticipantId(2));
        final Preferences preferences = new Preferences();
        preferences.put(0, 0, 1);
        preferences.put(0, 1, 4);
        preferences.put(1, 0, 1);
        preferences.put(1, 1, 4);
        preferences.put(2, 0, 1);
        preferences.put(2, 1, 4);
        preferences.put(3, 0, 4);
        preferences.put(3, 1, 1);
        final Set<EventId> freeEvents = new LinkedHashSet<>();
        freeEvents.add(new EventId(0));
        freeEvents.add(new EventId(1));
        final Assignment currentAssignment = new Assignment();
        currentAssignment.put(new ParticipantId(0), new EventId(0));
        currentAssignment.put(new ParticipantId(1), new EventId(0));
        currentAssignment.put(new ParticipantId(3), new EventId(1));
        final ResidualNetwork network =
            new ResidualNetwork(numberOfEvents, unassignedParticipants, preferences, freeEvents, currentAssignment);
        final Optional<Assignment> optional = network.getBestAugmentingAssignment();
        Assert.assertTrue(optional.isPresent());
        final Assignment actual = optional.get();
        Assert.assertEquals(actual.size(), 1);
        Assert.assertEquals(actual.get(new ParticipantId(2)), new EventId(0));
    }

}
