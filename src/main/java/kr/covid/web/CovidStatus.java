package kr.covid.web;

public class CovidStatus {
    private String location;
    private String country;
    private String total;

    public CovidStatus(String location, String country, String total) {
        this.location = location;
        this.country = country;
        this.total = total;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "CovidStatus{" +
                "location='" + location + '\'' +
                ", country='" + country + '\'' +
                ", total='" + total + '\'' +
                '}';
    }
}
