package org.fullstack4.cheese.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.cheese.domain.MemberEntity;
import org.fullstack4.cheese.dto.MemberDTO;
import org.fullstack4.cheese.dto.PageRequestDTO;
import org.fullstack4.cheese.dto.PageResponseDTO;
import org.fullstack4.cheese.exception.InsufficientStockException;
import org.fullstack4.cheese.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(rollbackFor = {InsufficientStockException.class, Exception.class})
    public boolean login(String id, String pwd, HttpServletRequest req) throws InsufficientStockException {
        HttpSession session = req.getSession();
        MemberEntity member = memberRepository.findByUserId(id);
        if(id.equals("") || id == null){
            throw new InsufficientStockException("아이디를 입력하세요.");
        }
        if(pwd.equals("") || pwd == null){
            throw new InsufficientStockException("비밀번호를 입력하세요.");
        }
        if(member == null) {
            throw new InsufficientStockException("해당하는 정보가 없습니다.");
        }
        if(member.getLastloginDate() != null) {
            LocalDateTime now = LocalDateTime.now();
            long monthsDifference = ChronoUnit.MONTHS.between(member.getLastloginDate(),now );
            log.info("monthsDifference:{}",monthsDifference);
            if(monthsDifference >= 6){
                throw new InsufficientStockException("마지막으로 로그인한지 6개월 이상된 계정입니다. 관리자에게 문의하세요.");
            }
        }

        if(member.getLogincount() >= 5){
            throw new InsufficientStockException("비밀번호를 5회 이상 틀린 계정입니다. 관리자에게 문의하세요.");
        }else {
            if (member.getUserPwd().equals(pwd)) {


                session.setAttribute("user_id", id);
                session.setAttribute("user_name", member.getUserName());

                MemberDTO dto = modelMapper.map(member, MemberDTO.class);
                dto.setLastloginDate(LocalDateTime.now());
                dto.setLogincount(0);

                MemberEntity member1 = modelMapper.map(dto, MemberEntity.class);
                memberRepository.save(member1);

                return true;
            } else {

                MemberDTO dto = modelMapper.map(member, MemberDTO.class);
                dto.setLogincount(member.getLogincount()+1);
                MemberEntity member1 = modelMapper.map(dto, MemberEntity.class);
                memberRepository.save(member1);
                return false;
            }
        }
    }

    @Override
    @Transactional(rollbackFor = {InsufficientStockException.class, Exception.class})
    public void join(MemberDTO memberDTO) throws InsufficientStockException {
        if(memberDTO.getUserId() == null || memberDTO.getUserId().trim().equals("")){
            throw new InsufficientStockException("아이디를 입력해주세요.");
        }
        if(memberDTO.getUserPwd() == null || memberDTO.getUserPwd().trim().equals("")){
            throw new InsufficientStockException("비밀번호를 입력해주세요.");
        }
        MemberEntity memberEntity = modelMapper.map(memberDTO, MemberEntity.class);
        memberRepository.save(memberEntity);
    }

    @Override
    public void modify(MemberDTO memberDTO) {

    }

    @Override
    public int pwdmodify(String id, String pwd) {
        return 0;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public String findemail(String id) {
        return "";
    }

    @Override
    public String findpwd(String id) {
        return "";
    }

    @Override
    public MemberDTO view(String id) {
        return null;
    }

    @Override
    public PageResponseDTO<MemberDTO> search(PageRequestDTO pageRequestDTO) {
        return null;
    }

    @Override
    public int checkId(String id) throws InsufficientStockException {
        return 0;
    }

    @Override
    public int checkEmail(String email1, String email2) throws InsufficientStockException {
        return 0;
    }
}
