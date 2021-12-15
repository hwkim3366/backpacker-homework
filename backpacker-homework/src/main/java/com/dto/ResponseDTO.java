package com.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.domain.Order;

@Getter
@NoArgsConstructor
public class ResponseDTO {
	
    private Long uid;
    private String email;
    private String name;
    private String password;
    private LocalDateTime createdAt;

    private Order order;

    public ResponseDTO(BigInteger uid
    						, String name
    						, String password
    						, String email
    						, Timestamp createdAt
    						, BigInteger orderUid
    						, String orderNumber
    						, BigInteger orderOwnerUid
    						, String orderProductName
    						, Timestamp orderCreatedAt) {
    	
        this.uid = uid.longValue();
        this.email = email;
        this.name = name;
        this.password = password;
        this.createdAt = createdAt.toLocalDateTime();

        if (orderUid != null) {
            this.order = Order.builder().uid(orderUid == null ? null : orderUid.longValue())
			                    		.orderNumber(orderNumber == null ? null : orderNumber)
			                    		.ownerUid(orderOwnerUid == null ? null : orderOwnerUid.longValue())
			                    		.productName(orderProductName == null ? null : orderProductName)
			                    		.createdAt(orderCreatedAt == null ? null : orderCreatedAt.toLocalDateTime())
			                    		.build();
        }
    }
}
