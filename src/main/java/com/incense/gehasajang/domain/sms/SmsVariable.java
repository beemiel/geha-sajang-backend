package com.incense.gehasajang.domain.sms;

import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class SmsVariable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sms_variable_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "sms_template_id")
    private SmsTemplate smsTemplate;

    private String name;

}
