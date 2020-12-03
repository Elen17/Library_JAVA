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
        this.city = (city);
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
//
//    private String splitNames(String input) {
//        String[] capitalLetters = input.split("[a-z]+"); // [firstMatch, secondMatch ... ""]
//        String[] words = input.split("[A-Z]"); // ["", firstMatch, secondMatch]
//
//        if (words.length == 0 || words.length == 1) {
//            return input;
//        }
//        StringBuilder builder = new StringBuilder();
//        byte index = 0;
//        while (index != capitalLetters.length) {
//            builder.append(capitalLetters[index]).append(words[index + 1]).append(" ");
//            ++index;
//        }
//        return builder.toString();
//    }
}
