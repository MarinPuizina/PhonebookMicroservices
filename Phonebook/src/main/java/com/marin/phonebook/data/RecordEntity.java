package com.marin.phonebook.data;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="records")
public class RecordEntity implements Serializable {

    private static final long serialVersionUID = -2092928229379314635L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String personName;

    @Column(nullable = false)
    private String recordType;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

}
