package com.plocky.deador.dto;

import com.plocky.deador.entity.User;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class OrderDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private User userId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String postcode;
    private String townCity;
    private String additionalInformation;
    private String address;
    private String deliveryStatus;
    private String email;

}
