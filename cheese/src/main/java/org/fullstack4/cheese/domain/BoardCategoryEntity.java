package org.fullstack4.cheese.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="che_bbs_category")
public class BoardCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_idx", unique = true, nullable = false)
    private int categoryIdx;
    @Column(name="bbs_category_code")
    private int bbsCategoryCode;
    @Column(name="bbs_category_name")
    private String bbsCategoryName;
}
