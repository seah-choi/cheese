package org.fullstack4.cheese.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardReplyDTO {

    private int replyIdx;
    private int bbsIdx;
    private String userId;
    private String replyContent;
    private LocalDateTime regDate;
    private LocalDateTime modifyDate;

}
