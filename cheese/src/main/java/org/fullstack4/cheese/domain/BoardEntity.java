package org.fullstack4.cheese.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="che_bbs")
public class BoardEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bbs_idx",unique = true, nullable = false)
    private int bbsIdx;

    @Column(name= "bbs_title" ,length = 20, nullable = false)
    private String bbsTitle;
    @Column(name= "bbs_content" ,length = 20, nullable = false)
    private String bbsContent;
    @Column(name= "user_id" ,length = 20, nullable = false)
    private String userId;
    @Column(name= "bbs_file" ,length = 20, nullable = true)
    private String bbsFile;
    @Column(name="bbs_good", nullable = true)
    private int bbsGood;
    @Column(name="bbs_category_code", nullable = true)
    private String bbsCategoryCode;
    private String fileorgname;
    public void modify(String user_id, String title, String content){
        this.userId = user_id;
        this.bbsTitle = title;
        this.bbsContent = content;
        super.setModify_date(LocalDateTime.now());
    }
}
