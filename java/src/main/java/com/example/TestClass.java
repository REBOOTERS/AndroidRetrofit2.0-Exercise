package com.example;

/**
 * Created by co-mall on 2016/9/2.
 */
public class TestClass {
    @BindAddress("http://www.google.com.cn")
    String address;
    @BindPort("8888")
    private String port;

    private int number;

    @BindGet
    void get(){

    }


    public void printInfo() {
        System.out.println("info is " + address + ":" + port);
    }



    private void myMethod(int number, String sex) {

    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


}
