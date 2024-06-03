package io;

import java.io.*;
import java.util.*;
import java.util.stream.*;

import model.*;

public class CSVFileReader implements InputReader {

    private static int parseEvents(final BufferedReader reader, final Events events) throws IOException {
        final String line = CSVFileReader.readNextNonEmptyLine(reader);
        final int numberOfEvents = Integer.parseInt(line);
        for (int event = 0; event < numberOfEvents; event++) {
            events.put(event, reader.readLine());
        }
        return numberOfEvents;
    }

    private static String readNextNonEmptyLine(final BufferedReader reader) throws IOException {
        String result = "";
        while ("".equals(result.trim())) {
            result = reader.readLine();
        }
        return result.trim();
    }

    private final String filePath;

    private final Random random;

    public CSVFileReader(final String filePath) {
        this.filePath = filePath;
        this.random = new Random();
    }

    @Override
    public Input readInput() throws IOException {
        final Participants participants = new Participants();
        final Events events = new Events();
        final Preferences preferences = new Preferences();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(this.filePath)))) {
            final int numberOfEvents = CSVFileReader.parseEvents(reader, events);
            this.parseParticipantsAndPreferences(reader, participants, preferences, numberOfEvents);
        }
        return new Input(participants, events, preferences);
    }

    private void parseParticipantsAndPreferences(
        final BufferedReader reader,
        final Participants participants,
        final Preferences preferences,
        final int numberOfEvents
    ) throws IOException {
        String line = CSVFileReader.readNextNonEmptyLine(reader);
        final int numberOfParticipants = Integer.parseInt(line);
        for (int participant = 0; participant < numberOfParticipants; participant++) {
            line = reader.readLine();
            try {
                final String[] columns = line.split(";", -1);
                participants.put(participant, columns[0]);
                final List<Integer> missingEvents =
                        new ArrayList<Integer>(IntStream.range(1, numberOfEvents + 1).boxed().toList());
                for (int preference = 1; preference <= numberOfEvents; preference++) {
                    final int event =
                            preference < columns.length ?
                                    Integer.parseInt(columns[preference].trim()) - 1 :
                                    missingEvents.remove(this.random.nextInt(missingEvents.size()));
                    missingEvents.remove(Integer.valueOf(event));
                    preferences.put(participant, event, preference * preference);
                }
            } catch (Exception e) {
                System.err.printf("Exception while parsing the following line:\n%s\n", line);
                throw e;
            }
        }
    }

}
