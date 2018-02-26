package io;

import java.io.*;

import model.*;

public interface AssignmentWriter {

    void writeAssignment(Participants participants, Events events, Assignment assignment) throws IOException;

}
