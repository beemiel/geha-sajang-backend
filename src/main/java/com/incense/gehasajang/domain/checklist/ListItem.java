package com.incense.gehasajang.domain.checklist;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class ListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "list_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "checklist_id")
    private Checklist checklist;

    private String contents;

    private LocalDateTime createdAt;

    private LocalDateTime updated_at;

    private boolean checked;

    private LocalDateTime checkedAt;

}
