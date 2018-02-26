package io;

import java.io.*;
import java.util.*;

import model.*;

public class ConsoleAndFileWriter implements AssignmentWriter {

    private final String filePath;

    public ConsoleAndFileWriter(final String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void writeAssignment(final Participants participants, final Events events, final Assignment assignment)
    throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(this.filePath)))) {
            for (final Map.Entry<ParticipantId, EventId> entry : assignment.entrySet()) {
                final String line =
                    String.format("%s -> %s", participants.get(entry.getKey()), events.get(entry.getValue()));
                System.out.println(line);
                writer.write(line);
                writer.newLine();
            }
        }
    }

}
