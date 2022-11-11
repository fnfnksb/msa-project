package com.msa.domain;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthInfo implements Serializable {

    private static final long serialVersionUID = 248695190893364620L;

    /**
     * 관리자 고유번호
     */
    private String adminSeq;

    /**
     * 업체 고유번호
     */
    private String compSeq;

    /**
     * 관리자 ID
     */
    private String adminId;

    /**
     * 관리자 상태 코드
     */
    private String adminStatCd;

    /**
     * 관리자 유형 코드
     */
    private String adminTypeCd;
}
