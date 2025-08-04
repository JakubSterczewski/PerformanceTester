package testClasses;

import java.util.Objects;

public class Person implements Comparable<Person> {
    private final int birthYear;
    private final String name;

    public Person(int birthYear, String name) {
        this.birthYear = birthYear;
        this.name = name;
    }

    @Override
    public int compareTo(Person o) {
        int comp = birthYear - o.birthYear;
        return comp != 0 ? comp : name.compareTo(o.name);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return birthYear == person.birthYear && Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(birthYear, name);
    }
}