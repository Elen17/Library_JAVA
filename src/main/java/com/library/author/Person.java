package com.library.author;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

public abstract class Person implements Comparable<Person> {
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private static final StringBuilder builder;

    static {
        builder = new StringBuilder();
    }

    private final int id;
    private final String name;
    private final String surname;
    private final long birthDate;
    private final Long deathDate;//can be null
    private final String country;//not null, immutable
    private final String city;//not null, immutable

    public Person(int id, String name, String surname, long birthDate, Long deathDate, String country, String city) {
        this.name = Objects.requireNonNull(name);
        this.surname = Objects.requireNonNull(surname);
        this.birthDate = birthDate;
        this.deathDate = deathDate;
//        this.country = Objects.requireNonNull(country);
//        this.city = Objects.requireNonNull(city);
        this.country = country;
        this.city = city;
        this.id = id;
    }

    private static int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        int age = currentDate.getYear() - birthDate.getYear() - 1;
        if (birthDate.getMonthValue() < currentDate.getMonthValue() && birthDate.getDayOfMonth() < currentDate.getDayOfMonth()) {
            ++age;
        }
        return age;
    }

    public int getAge() {
        return this.deathDate == null ? calculateAge(new Date(birthDate).toLocalDate(), LocalDate.now()) : calculateAge(new Date(birthDate).toLocalDate(),
                new Date(deathDate).toLocalDate());
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Date getBirthDate() {
        return new Date(this.birthDate);
    }

    public Date getDeathDate() {
        return this.deathDate == null ? null : new Date(this.deathDate);
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getFullName() {
        return this.name + " " + this.surname;
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
                birthDate == person.birthDate &&
                Objects.equals(deathDate, person.deathDate) &&
                country.equals(person.country) &&
                city.equalsIgnoreCase(person.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, birthDate, deathDate, country, city);
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
                ", country=" + this.getCountry() +
                ", city=" + this.getCity() +
                '}';
    }


}
