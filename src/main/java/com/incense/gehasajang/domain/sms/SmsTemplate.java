package com.incense.gehasajang.domain.sms;

import com.incense.gehasajang.domain.house.House;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
public class SmsTemplate {

    @Id
    @GeneratedValue
    @Column(name = "sms_template_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;

    private String title;

    private String contents;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "smsTemplate")
    private List<SmsVariable> smsVariables;

}
