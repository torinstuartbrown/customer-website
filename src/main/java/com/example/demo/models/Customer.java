package com.example.demo.models;

import com.sun.istack.Nullable;
import lombok.*;

import javax.persistence.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customers")
@Builder
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fullName;
    private String emailAddress;
    private Integer age;
    private String address;
    @Transient
    private Long instrumentId;

    @OneToOne(optional = true)
    public Instrument instrument;
}