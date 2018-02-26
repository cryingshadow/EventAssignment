package model;

public abstract class IntegerId {

    private final int id;

    public IntegerId(final int id) {
        this.id = id;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        return this.getId() == ((IntegerId)o).getId();
    }

    @Override
    public int hashCode() {
        return this.getId();
    }

    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return Integer.toString(this.id);
    }

}
