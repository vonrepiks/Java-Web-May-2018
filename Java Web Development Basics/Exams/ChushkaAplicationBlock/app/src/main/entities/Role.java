package entities;

public enum Role {
    USER,
    ADMIN;

    @Override
    public String toString() {
        return this.name().charAt(0) + this.name().substring(1).toLowerCase();
    }
}
