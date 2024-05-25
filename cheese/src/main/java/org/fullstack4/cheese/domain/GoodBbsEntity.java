package org.fullstack4.cheese.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="che_goodbbs")
public class GoodBbsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int idx;
    @Column(name="bbs_idx")
    private int bbsIdx;
    @Column(name="user_id")
    private String userId;
}
