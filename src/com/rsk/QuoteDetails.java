package com.rsk;

public class QuoteDetails {
    String companyName;
    int price;

    public QuoteDetails(){

    }

    public QuoteDetails(String companyName, int price) {
        this.companyName = companyName;
        this.price = price;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

