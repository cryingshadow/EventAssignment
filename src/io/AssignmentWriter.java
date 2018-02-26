package io;

import java.io.*;

import model.*;

public interface AssignmentWriter {

    void writeAssignment(
        final Participants participants,
        final Events events,
        final Preferences preferences,
        final Assignment assignment
    ) throws IOException;

}
