package com.hs.takeover.api.dto;


import javax.xml.bind.annotation.*;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Builder
@Getter
@Setter
@XmlRootElement(name = "session")
@XmlAccessorType(XmlAccessType.FIELD)
public class GWOpenApiResponseDto {

    private String id;
    private String empcode;
    private String deptid;
    private String key;
    private String name;
    private String uname;
    private String cn;
    private boolean isadditionalofficer;
    private boolean skipOtherOffice;
    private int timezoneOffset;
    private String saveDocDeptID;
    private String saveDocDeptName;
    private boolean useDocbox;
    private boolean useSanc;
    private int secLevel;


    @XmlElementWrapper(name = "additionalofficer")
    @XmlElement(name = "user")
    private List<UserDTO> additionalOfficers;

    // Getters and setters
    // ... (생략)

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class UserDTO {
        private String id;
        private String name;
        private String empcode;
        private String department;
        private String uri;
        private String position;
        private String duty;
        private String telephoneNumber;
        private String mobile;
        private String faxNumber;
        private String mail;
        private String homepage;

        // Getters and setters
        // ... (생략)
    }
}
