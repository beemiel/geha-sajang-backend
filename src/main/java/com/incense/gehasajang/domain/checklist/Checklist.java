package com.incense.gehasajang.domain.checklist;

import com.incense.gehasajang.domain.house.House;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
public class Checklist {

    @Id @GeneratedValue
    @Column(name = "checklist_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;

    @Enumerated(EnumType.STRING)
    private Category category;

    private LocalDateTime targetData;

    private String title;

    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "checklist")
    private List<ListItem> listItems;

}
