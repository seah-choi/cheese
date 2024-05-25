package org.fullstack4.cheese.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDTO {
    private int idx;
    private int bbsIdx;
    private String bbsTitle;
    private String bbsContent;
    private String userId;
    private LocalDateTime regDate;
    private String bbsFile;
    private String fileorgname;
    private LocalDateTime modifyDate;
    private String bbsTag;
    private String bbsPart;
    private int bbsGood;

    private String requestId;
    private String request_name;
    private String responseId;
    private String response_name;

    private int categoryIdx;
    private int bbsCategoryCode;
    private String bbsCategoryName;
}
