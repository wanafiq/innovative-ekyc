package com.wan.ekyc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ekyc_countries")
public class EkycCountry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "alpha2_code", nullable = false, unique = true)
    private String alpha2Code;

    @Column(name = "alpha3_code", nullable = false, unique = true)
    private String alpha3Code;

    @Column(name = "numeric_code", nullable = false, unique = true)
    private String numericCode;
}
