package com.hs.takeOver.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

	@Getter
	@Setter
	@Entity
	@Table(name = "takeOver")
	public class takeOver {
	    @Id
	    //@Column(name = "takeOver_id")
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer takeOverId;//인계ID PK

	    @Column(length = 9)
	    private String handOverUserId;//인계자 ID
	    
	    @Column(length = 60)
	    private String handOverUserName;//인계자명
	    
	    @Column(length = 9)
	    private String takeOverUserId;//인수자 ID
	    
	    @Column(length = 60)
	    private String takeOverUserName;//인수자명

	    private LocalDateTime takeOverDate; //인수일자

	    @Column(length = 9)
	    private String observeUserId;//입회자 ID
	    
	    @Column(length = 60)
	    private String observeUserName;//입회자명

	    private LocalDateTime observeDate; //입회일자
	    
	    @Column
	    private Integer status;//상태
	    
	    @Column(length = 60)
	    private String registerPositionName;//등록자직위명
	    
	    @Column(length = 9)
	    private String registerDeptId;//입회자 ID
	    
	    @Column(length = 60)
	    private String registerDeptName;//등록자부서명
	    
	    private LocalDateTime registDate; //등록일자
	    
	    /*
	    담당업무
	    예산물품내역
	    */

	}
