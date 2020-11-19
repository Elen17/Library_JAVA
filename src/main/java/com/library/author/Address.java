package com.library.author;

public final class Address {
    private final static StringBuilder builder;

    static {
        builder = new StringBuilder();
    }

    private final String country;
    private final String city;

    public Address(String country, String city) {
        this.country = country;
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        String address = builder.append("Country: ").append(this.country).append("/nCity: ").append(this.city).toString();
        builder.delete(0, address.length());

        return address;
    }
}
