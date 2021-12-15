package com.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	
	List<Member> findAll();
    Page<Member> findAll(Pageable pageable);
    Page<Member> findByEmail(String Email, Pageable pageable);
    Page<Member> findByName(String Name, Pageable pageable);
    
    Optional<Member> findById(Long uid);
    Optional<Member> findByEmail(String email);
    Optional<Member> findByEmailAndPassword(String email, String password);
    
    Member save(Member member);
    
}
