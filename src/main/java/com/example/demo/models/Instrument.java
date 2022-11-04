package com.example.demo.models;


import com.sun.istack.Nullable;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "instruments")
@Builder
@Getter
@Setter

public class Instrument {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String brand;
    private long price;
    @Transient
    private Long customerId;

    @OneToOne(optional = true, mappedBy = "instrument")
    public Customer customer;
}

