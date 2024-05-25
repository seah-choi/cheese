package org.fullstack4.cheese.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {
    private int userIdx;
    private String userName;
    private String userId;
    private String userPwd;
    private String userPhone;
    private String userPhone2;
    private String userPhone3;
    private String userEmail1;
    private String userEmail2;
    private LocalDateTime regDate;
    private LocalDateTime modifyDate;
    private int logincount;
    private LocalDateTime lastloginDate;

}
