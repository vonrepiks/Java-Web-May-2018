package models;

public class Cat {
    private String name;
    private String breed;
    private String color;
    private int numberOfLegs;

    public Cat(String name, String breed, String color, int numberOfLegs) {
        this.name = name;
        this.breed = breed;
        this.color = color;
        this.numberOfLegs = numberOfLegs;
    }

    public String getName() {
        return this.name;
    }

    public String getBreed() {
        return this.breed;
    }

    public String getColor() {
        return this.color;
    }

    public int getNumberOfLegs() {
        return this.numberOfLegs;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb
                .append(this.name).append(System.lineSeparator())
                .append(this.breed).append(System.lineSeparator())
                .append(this.color).append(System.lineSeparator())
                .append(this.numberOfLegs).append(System.lineSeparator());

        return sb.toString();
    }
}
