package com.springbootLearn.bookTicket.app.Entity;

import javax.persistence.*;

@Entity
@Table(name="ticket_Detail")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", nullable=false,length = 10)
    private Integer Id;
    @Column(name="first_Name")
    private String firstName;
    @Column(name="last_Name")
    private String lastName;
    @Column(name="source_STN")
    private String sourceSTN;
    @Column(name="dest_STN")
    private String destSTN;
    @Column(name="email")
    private String email;
    @Column(name="phone_Number")
    private Long phNum;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSourceSTN() {
        return sourceSTN;
    }

    public void setSourceSTN(String sourceSTN) {
        this.sourceSTN = sourceSTN;
    }

    public String getDestSTN() {
        return destSTN;
    }

    public void setDestSTN(String destSTN) {
        this.destSTN = destSTN;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhNum() {
        return phNum;
    }

    public void setPhNum(Long phNum) {
        this.phNum = phNum;
    }
}
