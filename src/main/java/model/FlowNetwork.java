package model;

import java.util.*;

public class FlowNetwork {

    private final Assignment assignment;
    private final Events events;
    private final Participants participants;
    private final Preferences preferences;
    private final int tolerance;

    public FlowNetwork(
        final Participants participants,
        final Events events,
        final Preferences preferences,
        final int tolerance
    ) {
        if (tolerance < 1) {
            throw new IllegalArgumentException(String.format("Tolerance must be positive but was %d!", tolerance));
        }
        this.participants = participants;
        this.events = events;
        this.preferences = preferences;
        this.tolerance = tolerance;
        this.assignment = new Assignment();
    }

    public void fordFulkerson() {
        while (this.augment()) {
            // Ford-Fulkerson
        }
    }

    public Assignment getAssignment() {
        return this.assignment;
    }

    Set<EventId> computeFreeEvents() {
        final Set<EventId> result = new LinkedHashSet<>();
        final Map<EventId, Integer> eventLoad = this.assignment.getEventLoad();
        final int min =
            this
            .events
            .keySet()
            .stream()
            .mapToInt(event -> eventLoad.containsKey(event) ? eventLoad.get(event) : 0)
            .min()
            .orElse(0);
        for (final EventId event : this.events.keySet()) {
            final int load = eventLoad.containsKey(event) ? eventLoad.get(event) : 0;
            if (min - load + this.tolerance > 0) {
                result.add(event);
            }
        }
        return result;
    }

    private boolean augment() {
        final ResidualNetwork residualNetwork = this.computeResidualNetwork();
        final Optional<Assignment> augmentingAssignment =
            residualNetwork.getBestAugmentingAssignment();
        if (!augmentingAssignment.isPresent()) {
            return false;
        }
        for (final Map.Entry<ParticipantId, EventId> assignmentEntry : augmentingAssignment.get().entrySet()) {
            this.assignment.put(assignmentEntry.getKey(), assignmentEntry.getValue());
        }
        return true;
    }

    private ResidualNetwork computeResidualNetwork() {
        return
            new ResidualNetwork(
                this.events.size(),
                this.computeUnassignedParticipants(),
                this.preferences,
                this.computeFreeEvents(),
                this.assignment
            );
    }

    private Set<ParticipantId> computeUnassignedParticipants() {
        final Set<ParticipantId> result = new LinkedHashSet<>();
        result.addAll(this.participants.keySet());
        result.removeAll(this.assignment.keySet());
        return result;
    }

}
