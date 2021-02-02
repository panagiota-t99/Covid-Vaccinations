package com.example.tychala_panagiota_18162_androidjan21;

public class CovidItem {

    private String area;
    private int daytotal;
    private int daydiff;
    private int totalvaccinations;

    public CovidItem(){}

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getDaytotal() {
        return daytotal;
    }

    public void setDaytotal(int daytotal) {
        this.daytotal = daytotal;
    }

    public int getDaydiff() {
        return daydiff;
    }

    public void setDaydiff(int daydiff) {
        this.daydiff = daydiff;
    }

    public int getTotalvaccinations() {
        return totalvaccinations;
    }

    public void setTotalvaccinations(int totalvaccinations) {
        this.totalvaccinations = totalvaccinations;
    }
}
