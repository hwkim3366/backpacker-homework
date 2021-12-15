package com.controller;

import com.domain.Member;
import com.domain.Order;
import com.dto.ErrorMsgs;
import com.dto.ResponseDTO;
import com.dto.MemberRegistDTO;
import com.service.MainService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class MainController {

    private final MainService mainService;
    
    /**
     * 회원 가입
     */
    @PostMapping("/regist")
    @ResponseBody
    @Operation(summary = "회원가입", description = "회원가입 처리")
    public ResponseEntity regist(@RequestBody @Valid MemberRegistDTO memberRegistDTO) {
    	
        try {
        	
            Long result = mainService.regist(memberRegistDTO);
            
            return ResponseEntity.ok().body(result);
            
        } catch (IllegalStateException e) {
        	
            Map<String, String> errors = new HashMap<>();
            errors.put("FAIL", e.getMessage());
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorMsgs.builder().errors(errors).build());
        }
    }

    /**
     * 회원 로그인
     */
    @PostMapping("/login")
    @ResponseBody
    @Operation(summary = "로그인", description = "이메일&비밀번호 인증")
    public ResponseEntity login(@ApiIgnore HttpSession session, @RequestParam String email, @RequestParam String password) {
    	
        Member member = mainService.login(email, password);
        session.setAttribute("loginMember", member);
        return ResponseEntity.ok().build();
    }
    
    /**
     * 로그아웃
     */
    @PostMapping("/member/logout")
    @ResponseBody
    public ResponseEntity logout(@ApiIgnore HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok().build();
    }

    /**
     * 단일 회원 상세 정보 조회
     */
    @GetMapping("/member/{uid}")
    @ResponseBody
    @Operation(summary = "단일 회원 상세 정보 조회", description = "회원ID로 상세 조회")
    public ResponseEntity<Member> findMember(@PathVariable Long uid) {
    	
        Member member = mainService.findMember(uid);
        
        return ResponseEntity.ok().body(member);
    }
    
    /**
     * 단일 회원의 주문 목록 조회
     */
    @GetMapping("/orders")
    @ResponseBody
    @Operation(summary = "단일 회원의 주문 목록 조회", description = "회원ID로 주문 목록 조회")
    public ResponseEntity<List<Order>> findByOwnerUid(@RequestParam(required = false) Long uid) {
    	
        return ResponseEntity.ok().body(mainService.findByOwnerUid(uid));
    }
    
    /**
     * 여러 회원 목록 조회
     */
    @GetMapping("/members")
    @ResponseBody
    @Operation(summary = "여러 회원 목록 조회", description = "이메일 또는 이름으로 여러 회원 정보 조회(마지막 주문정보 포함)")
    @ApiImplicitParams({@ApiImplicitParam(name = "filterName", value = "검색 컬럼(email, name)"),
            			@ApiImplicitParam(name = "filterValue", value = "검색 내용"),
            			@ApiImplicitParam(name = "page", value = "페이지")
    					})
    public ResponseEntity<Page<ResponseDTO>> findMembers(@RequestParam(defaultValue = "1") Integer page
    													, @RequestParam(required = false, defaultValue = "") String filterName
    													, @RequestParam(required = false, defaultValue = "") String filterValue) {

        Pageable pageable = PageRequest.of(page - 1, 10);//페이징 사이즈 10

        return ResponseEntity.ok().body(mainService.findMembers(pageable, filterName, filterValue));
    }
}
