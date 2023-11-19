package control;

import java.io.*;

import io.*;
import model.*;

public class Main {

    public static void main(final String[] args) {
        try {
            final IO io = new IO(args);
            final Input input = io.getReader().readInput();
            final Participants participants = input.getParticipants();
            final Events events = input.getEvents();
            final Preferences preferences = input.getPreferences();
            final FlowNetwork network = new FlowNetwork(participants, events, preferences, 1);
            network.fordFulkerson();
            final Assignment assignment = network.getAssignment();
            io.getWriter().writeAssignment(participants, events, preferences, assignment);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

}
