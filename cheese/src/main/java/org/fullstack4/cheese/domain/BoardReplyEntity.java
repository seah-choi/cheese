package org.fullstack4.cheese.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="che_bbs_reply")
public class BoardReplyEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_idx", unique = true, nullable = false)
    private int replyIdx;

    @Column(name = "bbs_idx")
    private int bbsIdx;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "reply_content")
    private String replyContent;
}
