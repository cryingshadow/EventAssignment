package io;

import java.io.*;
import java.util.*;
import java.util.Map.*;
import java.util.stream.*;

import model.*;

public class ConsoleAndFileWriter implements AssignmentWriter {

    private static String computeMaximumLoadDifference(final Events events, final Assignment assignment) {
        final Map<EventId, Integer> load = assignment.getEventLoad();
        final List<Integer> eventLoads =
            events
            .keySet()
            .stream()
            .map(event -> load.containsKey(event) ? load.get(event) : Integer.valueOf(0))
            .collect(Collectors.toList());
        final int maxLoadDiff =
            eventLoads.stream().max(Comparator.naturalOrder()).orElse(0) -
            eventLoads.stream().min(Comparator.naturalOrder()).orElse(0);
        return String.format("The maximum load difference is %d.", maxLoadDiff);
    }

    private static List<String> computePreferenceUsage(
        final int numberOfEvents,
        final Preferences preferences,
        final Assignment assignment
    ) {
        final List<String> result = new ArrayList<String>();
        final int[] occurrencesOfPreferences = new int[numberOfEvents];
        Arrays.fill(occurrencesOfPreferences, 0);
        assignment
        .entrySet()
        .stream()
        .map(entry -> ((int)Math.round(Math.sqrt(preferences.get(entry.getKey(), entry.getValue())))) - 1)
        .forEach(preference -> occurrencesOfPreferences[preference]++);
        for (int preference = 0; preference < numberOfEvents; preference++) {
            result.add(String.format("Preference %d: %d", preference + 1, occurrencesOfPreferences[preference]));
        }
        return result;
    }

    private static String computeTotalError(
        final Participants participants,
        final Preferences preferences,
        final Assignment assignment
    ) {
        final int totalError =
            assignment.entrySet().stream().mapToInt(entry -> preferences.get(entry.getKey(), entry.getValue())).sum() -
            participants.size();
        return String.format("The total error is %d.", totalError);
    }

    private static void writeAssignment(
        final BufferedWriter writer,
        final Participants participants,
        final Events events,
        final Preferences preferences,
        final Assignment assignment
    ) throws IOException {
        final List<Map.Entry<ParticipantId, EventId>> sortedEntries =
            new ArrayList<Map.Entry<ParticipantId, EventId>>(assignment.entrySet());
        Collections.sort(sortedEntries, new Comparator<Map.Entry<ParticipantId, EventId>>() {
            @Override
            public int compare(final Entry<ParticipantId, EventId> o1, final Entry<ParticipantId, EventId> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        for (final Map.Entry<ParticipantId, EventId> entry : sortedEntries) {
            final int preference = (int)Math.sqrt(preferences.get(entry.getKey(), entry.getValue()));
            final String line =
                String.format("%s -> (%d) %s", participants.get(entry.getKey()), preference, events.get(entry.getValue()));
            ConsoleAndFileWriter.writeLine(writer, line);
        }
    }

    private static void writeLine(final BufferedWriter writer, final String line) throws IOException {
        System.out.println(line);
        writer.write(line);
        writer.newLine();
    }

    private static void writeStatistics(
        final BufferedWriter writer,
        final Participants participants,
        final Events events,
        final Preferences preferences,
        final Assignment assignment
    ) throws IOException {
        ConsoleAndFileWriter.writeLine(writer, "");
        ConsoleAndFileWriter.writeLine(
            writer,
            ConsoleAndFileWriter.computeTotalError(participants, preferences, assignment)
        );
        ConsoleAndFileWriter.writeLine(writer, ConsoleAndFileWriter.computeMaximumLoadDifference(events, assignment));
        for (final String line : ConsoleAndFileWriter.computePreferenceUsage(events.size(), preferences, assignment)) {
            ConsoleAndFileWriter.writeLine(writer, line);
        }
    }

    private final String filePath;

    public ConsoleAndFileWriter(final String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void writeAssignment(
        final Participants participants,
        final Events events,
        final Preferences preferences,
        final Assignment assignment
    ) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(this.filePath)))) {
            ConsoleAndFileWriter.writeAssignment(writer, participants, events, preferences, assignment);
            ConsoleAndFileWriter.writeStatistics(writer, participants, events, preferences, assignment);
        }
    }

}
