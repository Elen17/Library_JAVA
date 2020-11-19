package com.library.author;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

public abstract class Person implements Comparable<Person> {
    private static final StringBuilder builder;

    static {
        builder = new StringBuilder();
    }

    private final int id;
    private final String name;
    private final String surname;
    private final Date birthDate;
    private final Date deathDate;//can be null
    private final Address address;//not null, immutable

    public Person(int id, String name, String surname, Date birthDate, Date deathDate, Address address) {
        this.name = Objects.requireNonNull(name);
        this.surname = Objects.requireNonNull(surname);
        this.birthDate = Objects.requireNonNull(birthDate);
        this.deathDate = deathDate;
        this.address = Objects.requireNonNull(address);
        this.id = id;
    }

    private static int calculateAge(Date birthDate, Date currentDate) {
        int age = currentDate.getYear() - birthDate.getYear() - 1;
        if (birthDate.getMonth() < currentDate.getMonth() && birthDate.getDate() < currentDate.getDate()) {
            ++age;
        }
        return age;
    }

    public int getAge() {
        return this.deathDate == null ? calculateAge(birthDate, Date.valueOf(LocalDate.now())) : calculateAge(birthDate, deathDate);
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Date getDeathDate() {
        return deathDate;
    }

    public Address getAddress() {
        return address;
    }

    public String getFullName() {
        return this.name +  " " + this.surname;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return name.equals(person.name) &&
                surname.equals(person.surname) &&
                birthDate.equals(person.birthDate) &&
                Objects.equals(deathDate, person.deathDate) &&
                address.equals(person.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, birthDate, deathDate, address);
    }

    @Override
    public int compareTo(Person o) {
        return Objects.compare(this, o, Comparator.comparing(Person::getFullName));
    }


    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthDate=" + birthDate +
                ", deathDate=" + deathDate +
                ", address=" + address +
                '}';
    }sql
}
