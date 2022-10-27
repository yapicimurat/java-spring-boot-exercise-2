package com.jwt.security.model;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class UserLog {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String name;

    private LogType type;

    private LocalDate date;

}
