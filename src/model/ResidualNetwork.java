package model;

import java.util.*;

public class ResidualNetwork {

    private static final Comparator<Map.Entry<EventId, Integer>> EVENT_DISTANCE_COMPARATOR =
        new Comparator<Map.Entry<EventId, Integer>>() {

            @Override
            public int compare(final Map.Entry<EventId, Integer> entry1, final Map.Entry<EventId, Integer> entry2) {
                return Integer.compare(entry1.getValue(), entry2.getValue());
            }

        };

    private final Map<ParticipantId, Integer> participantDistance;
    private final Map<EventId, Integer> eventDistance;
    private final Map<ParticipantId, EventId> participantPredecessor;
    private final Map<EventId, ParticipantId> eventPredecessor;
    private final Preferences preferences;
    private final Set<EventId> freeEvents;
    private final Assignment currentAssignment;
    private final int numberOfEvents;

    public ResidualNetwork(
        final int numberOfEvents,
        final Set<ParticipantId> unassignedParticipants,
        final Preferences preferences,
        final Set<EventId> freeEvents,
        final Assignment currentAssignment
    ) {
        this.numberOfEvents = numberOfEvents;
        this.preferences = preferences;
        this.freeEvents = freeEvents;
        this.currentAssignment = currentAssignment;
        this.participantDistance = new LinkedHashMap<>();
        this.eventDistance = new LinkedHashMap<>();
        this.participantPredecessor = new LinkedHashMap<>();
        this.eventPredecessor = new LinkedHashMap<>();
        for (final ParticipantId participant : unassignedParticipants) {
            this.participantDistance.put(participant, 0);
        }
    }

    public Optional<Assignment> getBestAugmentingAssignment() {
        this.computeShortestPathFromSourceToSink();
        final Optional<Map.Entry<EventId, Integer>> eventEntryWithShortestDistance =
            this
            .eventDistance
            .entrySet()
            .stream()
            .filter(entry -> this.freeEvents.contains(entry.getKey()))
            .min(ResidualNetwork.EVENT_DISTANCE_COMPARATOR);
        if (!eventEntryWithShortestDistance.isPresent()) {
            return Optional.empty();
        }
        return Optional.of(this.computeAssignment(eventEntryWithShortestDistance.get().getKey()));
    }

    private Assignment computeAssignment(final EventId bestEvent) {
        final Assignment result = new Assignment();
        EventId event = bestEvent;
        ParticipantId participant = this.eventPredecessor.get(event);
        if (participant == null) {
            throw new IllegalStateException(
                String.format("Event %d has no predecessor although it has a finite distance!", event.getId())
            );
        }
        result.put(participant, event);
        while (this.participantPredecessor.containsKey(participant)) {
            event = this.participantPredecessor.get(participant);
            participant = this.eventPredecessor.get(event);
            if (participant == null) {
                throw new IllegalStateException(
                    String.format("Event %d has no predecessor although it has a finite distance!", event.getId())
                );
            }
            result.put(participant, event);
        }
        return result;
    }

    private void computeShortestPathFromSourceToSink() {
        // Bellman-Ford algorithm for shortest paths
        for (int i = 0; i < 2 * this.numberOfEvents - 1; i++) {
            for (final Map.Entry<ParticipantAndEventId, Integer> edge : this.preferences.entrySet()) {
                final ParticipantAndEventId key = edge.getKey();
                final ParticipantId participant = key.getParticipant();
                final EventId event = key.getEvent();
                final int edgeDistance = edge.getValue();
                if (
                    this.currentAssignment.containsKey(participant) &&
                    this.currentAssignment.get(participant).equals(event)
                ) {
                    if (this.eventDistance.containsKey(event)) {
                        final int currentEventDistance = this.eventDistance.get(event);
                        if (
                            !this.participantDistance.containsKey(participant) ||
                            this.participantDistance.get(participant) > currentEventDistance - edgeDistance
                        ) {
                            this.participantDistance.put(participant, currentEventDistance - edgeDistance);
                            this.participantPredecessor.put(participant, event);
                        }
                    }
                } else {
                    if (this.participantDistance.containsKey(participant)) {
                        final int currentParticipantDistance = this.participantDistance.get(participant);
                        if (
                            !this.eventDistance.containsKey(event) ||
                            this.eventDistance.get(event) > currentParticipantDistance + edgeDistance
                        ) {
                            this.eventDistance.put(event, currentParticipantDistance + edgeDistance);
                            this.eventPredecessor.put(event, participant);
                        }
                    }
                }
            }
        }
    }

}
