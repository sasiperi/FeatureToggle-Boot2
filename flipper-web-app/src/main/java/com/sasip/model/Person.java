package com.sasip.model;

public class Person
{

    private String firstName;
    private String lastName;
    private String whoIs;

    public Person(String firstName, String lastName, String whoIs)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.whoIs = whoIs;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getWhoIs()
    {
        return whoIs;
    }

    public void setWhoIs(String who)
    {
        this.whoIs = who;
    }

}
