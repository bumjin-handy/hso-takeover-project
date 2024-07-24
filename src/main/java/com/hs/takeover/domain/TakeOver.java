package com.hs.takeover.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

	@Getter
	@Setter
	@Entity
	@Table(name = "TAKEOVER")
	public class TakeOver {
		@Id
	    @Column(name = "TAKEOVERID", length = 26) private String takeOverId;//인계ID PK
	    
		@Column(length = 9) private String handOverUserId;//인계자 ID
		@Column(length = 60) private String handOverUserName;//인계자명
		@Column(length = 60) private String handOverPositionName;//인계자 직위명
		@Column(length = 60) private String handOverOfficePhone;//인계자 개인전화번호
		@Column(length = 60) private String handOverPersonalPhone;//인계자 회사전화번호
		@Column(length = 9) private String handOverDeptId;//인계자 부서ID
	    @Column(length = 60) private String handOverDeptName;//인계자 부서명(등록부서명과 동일)
	    
		@Column(length = 9) private String takeOverUserId;//인수자 ID
		@Column(length = 60) private String takeOverUserName;//인수자명
		@Column(length = 60) private String takeOverPositionName;//인수자 직위명
		@Column(length = 60) private String takeOverOfficePhone;//인수자 개인전화번호
		@Column(length = 60) private String takeOverPersonalPhone;//인수자 회사전화번호
		@Column(length = 9) private String takeOverDeptId;//인수자 부서ID
	    @Column(length = 60) private String takeOverDeptName;//인수자 부서명
		
	    @Column(length = 9) private String observeUserId;//입회자 ID
	    @Column(length = 60) private String observeUserName;//입회자명
	    @Column(length = 60) private String observePositionName;//입회자 직위명
	    @Column(length = 60) private String observeOfficePhone;//입회자 개인전화번호
	    @Column(length = 60) private String observePersonalPhone;//입회자 회사전화번호
	    @Column(length = 9) private String observeDeptId;//입회자 부서ID
	    @Column(length = 60) private String observeDeptName;//입회자 부서명
		
	    @Column(length = 2000) private String handoverOpinion;//인계의견
		@Column private LocalDateTime takeOverDate; //인수 확인/반려일
	    @Column private LocalDateTime observeDate; //입회확인/반려일
	    @Column private Integer status;//인수인계상태 //　인수요청:1, 인수반려:2, 입회요청:3, 입회반려:4, 완료:5
	    
	    @Column private LocalDateTime registDate; //등록일자
	    @Column(length = 9) private String registDeptId;//등록 부서ID
	    @Column(length = 60) private String registDeptName;//등록 부서명
	    @Column(name = "MAINDUTY", length = 2000) private String mainDuty;//담당업무
	    @Column(name = "BUDGETITEMDETAIL", length = 2000) private String budgetItemDetail;//예산물품내역
	    @Column(name = "BUSINESSPLAN", length = 2000) private String businessPlan;//주요업무계획및 진행상황

	    @PrePersist
	    protected void onCreate() {
	        if (takeOverId == null) {
	        	takeOverId = generateTakeOverId();
	        }
	    }

	    //TAKEOVER20240715175108426 
	    private String generateTakeOverId() {
	        LocalDateTime now = LocalDateTime.now();
	        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
	        String id = "TAKEOVER"+timestamp;
	        return id;
	    }
	    
	    @Override
		public String toString() {
			return "TakeOver [takeOverId=" + takeOverId + ", handOverUserId=" + handOverUserId + ", handOverUserName="
					+ handOverUserName + ", handOverPositionName=" + handOverPositionName + ", handOverOfficePhone="
					+ handOverOfficePhone + ", handOverPersonalPhone=" + handOverPersonalPhone + ", handOverDeptId="
					+ handOverDeptId + ", handOverDeptName=" + handOverDeptName + ", takeOverUserId=" + takeOverUserId
					+ ", takeOverUserName=" + takeOverUserName + ", takeOverPositionName=" + takeOverPositionName
					+ ", takeOverOfficePhone=" + takeOverOfficePhone + ", takeOverPersonalPhone="
					+ takeOverPersonalPhone + ", takeOverDeptId=" + takeOverDeptId + ", takeOverDeptName="
					+ takeOverDeptName + ", observeUserId=" + observeUserId + ", observeUserName=" + observeUserName
					+ ", observePositionName=" + observePositionName + ", observeOfficePhone=" + observeOfficePhone
					+ ", observePersonalPhone=" + observePersonalPhone + ", observeDeptId=" + observeDeptId
					+ ", observeDeptName=" + observeDeptName + ", handoverOpinion=" + handoverOpinion
					+ ", takeOverDate=" + takeOverDate + ", observeDate=" + observeDate + ", status=" + status
					+ ", registDate=" + registDate + ", mainDuty=" + mainDuty + ", budgetItemDetail=" + budgetItemDetail
					+ ", businessPlan=" + businessPlan + "]";
		}
	}
