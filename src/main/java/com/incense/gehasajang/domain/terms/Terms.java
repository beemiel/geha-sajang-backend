package com.incense.gehasajang.domain.terms;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Terms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "terms_id")
    private Long id;

    private String type;

    private String contents;

    @Builder
    public Terms(Long id, String type, String contents) {
        this.id = id;
        this.type = type;
        this.contents = contents;
    }

}
