package com.incense.gehasajang.domain.checklist;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class ListItem {

    @Id @GeneratedValue
    @Column(name = "list_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "checklist_id")
    private Checklist checklist;

    private String contents;

    private LocalDateTime createdAt;

    private LocalDateTime updated_at;

    private boolean checked;

    private LocalDateTime checkedAt;

}
