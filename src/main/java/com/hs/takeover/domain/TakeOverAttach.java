package com.hs.takeover.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.*;

	@Getter
	@Setter
	@Entity
	@Table(name = "TAKEOVER_ATTACH")
	public class TakeOverAttach {
		@Id
		@Column(name = "ATTACHID", length = 26)
		private String attachId;
		
		@ManyToOne 
		private TakeOver takeOver; 
		/*
		 * @Column(name = "TAKEOVERID", length = 26) private String takeOverId;
		 */
		
	    @Column(length = 400)
	    private String fileName;

	    private Integer fileSize;
	    
	    @PrePersist
	    protected void onCreate() {
	        if (attachId == null) {
	        	attachId = generateAttachId();
	        }
	    }

	    //TAKEOVER20240715175108426 
	    private String generateAttachId() {
	        LocalDateTime now = LocalDateTime.now();
	        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
	        String id = "TAKEOVER"+timestamp;
	        return id;
	    }
	}
