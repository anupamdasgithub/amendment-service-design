package com.bank.amendments.model;

/** Structured postal address. */
public class Address {

    private String line1;
    private String line2;
    private String city;
    private String region;
    private String postcode;
    private String countryCode;

    public String getLine1() { return line1; }
    public void setLine1(String v) { this.line1 = v; }

    public String getLine2() { return line2; }
    public void setLine2(String v) { this.line2 = v; }

    public String getCity() { return city; }
    public void setCity(String v) { this.city = v; }

    public String getRegion() { return region; }
    public void setRegion(String v) { this.region = v; }

    public String getPostcode() { return postcode; }
    public void setPostcode(String v) { this.postcode = v; }

    public String getCountryCode() { return countryCode; }
    public void setCountryCode(String v) { this.countryCode = v; }
}
