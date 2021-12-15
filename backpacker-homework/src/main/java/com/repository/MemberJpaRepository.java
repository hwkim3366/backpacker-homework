package com.repository;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.dto.ResponseDTO;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

public class MemberJpaRepository {

    private final EntityManager em;

    @Autowired
    public MemberJpaRepository(EntityManager em) {
        this.em = em;
    }

    public Page<ResponseDTO> findAllWithOrder(Pageable pageable, String filterName, String filterValue) {
    	
    	String defaultQry = "SELECT member.*, recentInfo.uid orderUid"
			    			+ ", recentInfo.order_number orderNumber"
			    			+ ", recentInfo.owner_uid orderOwnerUid"
			    			+ ", recentInfo.product_name orderProductName"
			    			+ ", recentInfo.created_at orderCreatedAt "
			    			+ "FROM member LEFT OUTER JOIN "
			    			+ "(SELECT * FROM orders "
			    			+ "WHERE (owner_uid, created_at) IN "
			    			+ "(SELECT owner_uid, MAX(created_at) AS created_at "
			    			+ "FROM orders GROUP BY owner_uid)) AS recentInfo ON member.uid = recentInfo.owner_uid";
        
        String condQry = " WHERE member.:filterName = ':filterValue'";
        
        String limitQry = " LIMIT :offset, :size";
        
        String resultQuery = "";

        if (filterName != null && (filterName.equals("email") || filterName.equals("name"))) {
        	
        	condQry = condQry.replace(":filterName", filterName).replace(":filterValue", filterValue);
            
        	resultQuery = defaultQry + condQry + limitQry;
            
        } else {
        	
        	resultQuery = defaultQry + limitQry;
        }

        Query nativeQry = em.createNativeQuery(resultQuery).setParameter("offset", (int) pageable.getOffset()).setParameter("size", pageable.getPageSize());

        JpaResultMapper jpaResultMapper = new JpaResultMapper();
        
        List<ResponseDTO> result = jpaResultMapper.list(nativeQry, ResponseDTO.class);
        
        Long cnt = ((BigInteger) em.createNativeQuery("SELECT COUNT(*) FROM member "
									        		+ "LEFT OUTER JOIN (SELECT * FROM orders WHERE (owner_uid, created_at) IN "
									        		+ "(SELECT owner_uid, MAX(created_at) AS created_at FROM orders GROUP BY owner_uid)) AS recentInfo "
									        		+ "ON member.uid = recentInfo.owner_uid")
									        		.getSingleResult()).longValue();
        
        return new PageImpl<ResponseDTO>(result, pageable, cnt);
    }

}
