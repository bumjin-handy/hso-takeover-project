package com.hs.takeover.domain;

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
	@Table(name = "TAKEOVER")
	public class TakeOverAttach {
		
		private Integer takeOverId;
		
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer attachId;

	    @Column(length = 400)
	    private String fileName;

	    private Integer fileSize;
	    
	    /*	    
	    TAKEOVERID
	    ATTACHID
	    FILENAME
	    FILESIZE*/

	}
