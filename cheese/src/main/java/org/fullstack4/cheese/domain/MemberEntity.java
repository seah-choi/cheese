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
@Table(name="che_user")
public class MemberEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx", unique = true, nullable = false)
    private int userIdx;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_id", unique = true)
    private String userId;
    @Column(name = "user_pwd")
    private String userPwd;
    @Column(name = "user_phone")
    private String userPhone;
    @Column(name = "user_phone2")
    private String userPhone2;
    @Column(name = "user_phone3")
    private String userPhone3;
    @Column(name = "user_email1")
    private String userEmail1;
    @Column(name = "user_email2")
    private String userEmail2;
    @Column(name = "logincount",nullable = true,insertable = false,updatable = true)
    private int logincount;
    @Column(name = "lastlogin_date",nullable = true,insertable = false,updatable = true)
    private LocalDateTime lastloginDate;
    @Column(name = "user_type")
    private String userType;

}
