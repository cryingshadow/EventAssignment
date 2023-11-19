package model;

public class ParticipantAndEventId {

    private final EventId event;
    private final ParticipantId participant;

    public ParticipantAndEventId(final ParticipantId participant, final EventId event) {
        this.participant = participant;
        this.event = event;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final ParticipantAndEventId other = (ParticipantAndEventId)o;
        return this.getParticipant().equals(other.getParticipant()) && this.getEvent().equals(other.getEvent());
    }

    public EventId getEvent() {
        return this.event;
    }

    public ParticipantId getParticipant() {
        return this.participant;
    }

    @Override
    public int hashCode() {
        return 2 * this.getParticipant().hashCode() + 3 * this.getEvent().hashCode();
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", this.participant.toString(), this.event.toString());
    }

}
