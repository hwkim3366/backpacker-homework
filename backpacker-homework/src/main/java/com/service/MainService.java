package com.service;

import com.domain.Member;
import com.domain.Order;
import com.dto.ResponseDTO;
import com.dto.MemberRegistDTO;
import com.repository.MemberJpaRepository;
import com.repository.MemberRepository;
import com.repository.OrderRepository;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class MainService {
	
	private final PasswordEncoder passwordEncoder;
	
    private final MemberRepository memberRepository;
    
    private final MemberJpaRepository memberJpaRepository;
    
    private final OrderRepository orderRepository;

    /**
     * 회원 가입
     */
    public Long regist(MemberRegistDTO memberRegistDTO) {
    	
    	duplicateCheck(memberRegistDTO);
    	
    	memberRegistDTO.setPassword(passwordEncoder.encode(memberRegistDTO.getPassword()));
        
        return memberRepository.save(memberRegistDTO.toEntity()).getUid();
    }
    
    /**
     * 기 가입여부 체크
     */
    private void duplicateCheck(MemberRegistDTO memberRegistDTO) {
        memberRepository.findByEmail(memberRegistDTO.getEmail()).ifPresent(m -> {throw new IllegalStateException("이미 등록된 이메일입니다");});
    }
    
    /**
     * 로그인
     */
    public Member login(String email, String password) {

        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다"));

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 유효하지 않습니다");
        }

        return member;
    }

    /**
     * 회원 조회
     */
    public Member findMember(Long uid) {
        Member member = memberRepository.findById(uid).orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다"));
        return member;
    }
    
    /**
     * 단일 회원의 주문목록 조회
     */
    public List<Order> findByOwnerUid(Long uid){
        return orderRepository.findByOwnerUid(uid);
    }
    

    /**
     * 전체 회원 조회
     */
    public Page<ResponseDTO> findMembers(Pageable pageable, String filterName, String filterValue) {
        return memberJpaRepository.findAllWithOrder(pageable, filterName, filterValue);
    }

}
