package com.onetoone.mapping.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "address")
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "userId")
public class Address {
    @Id
    @Column(name = "user_id")
    private int userId;

    @Column(name = "street")
    private String street;

    @Column(name = "pincode")
    private String pinCode;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}
