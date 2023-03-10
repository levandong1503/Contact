package com.example.contact;


public class DanhBa {
    private int Id;
    private String Name;
    private String Phone;

    public DanhBa(String name, String phone) {
        Name = name;
        Phone = phone;
    }
    public DanhBa(int id,String name, String phone) {
        Name = name;
        Phone = phone;
        Id = id;
    }

    public DanhBa() {}

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}

