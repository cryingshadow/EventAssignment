package io;

import model.*;

public class Input {

    private final Events events;
    private final Participants participants;
    private final Preferences preferences;

    public Input(final Participants participants, final Events events, final Preferences preferences) {
        this.participants = participants;
        this.events = events;
        this.preferences = preferences;
    }

    public Events getEvents() {
        return this.events;
    }

    public Participants getParticipants() {
        return this.participants;
    }

    public Preferences getPreferences() {
        return this.preferences;
    }

}
