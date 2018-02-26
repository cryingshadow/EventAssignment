package io;

import java.io.*;

import model.*;

public class ConsoleAndFileWriter implements AssignmentWriter {

    private final String filePath;

    public ConsoleAndFileWriter(final String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void writeAssignment(final Participants participants, final Events events, final Assignment assignment)
    throws IOException {
        // TODO Auto-generated method stub

    }

}
