package com.example.bitirme;

public class Blog{

    public Blog() {
    }

    public String getDers() { return ders; }

    public String getDerskodu() {
        return derskodu;
    }

    public void setDerskodu(String derskodu) {
        this.derskodu = derskodu;
    }

    public void setDers(String ders) {
        this.ders = ders;
    }




    private Long qrcodevalue;
    private String derskodu;
    private String ders;

    public Long getQrcodevalue() {
        return qrcodevalue;
    }

    public void setQrcodevalue(Long qrcodevalue) {
        this.qrcodevalue = qrcodevalue;
    }

    public Blog(Long qrcodevalue, String ders, String derskodu) {
        this.qrcodevalue = qrcodevalue;
        this.ders = ders;
        this.derskodu = derskodu;
    }
    public Blog(Long qrcodevalue){


        this.qrcodevalue = qrcodevalue;
    }
}