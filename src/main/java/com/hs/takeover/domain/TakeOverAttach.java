package com.hs.takeover.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

	@Getter
	@Setter
	@Entity
	@Table(name = "TAKEOVERATTACH")
	public class TakeOverAttach {
		
		private Integer takeOverId;
		
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer attachId;

	    @Column(length = 400)
	    private String fileName;

	    private Integer fileSize;
	}
