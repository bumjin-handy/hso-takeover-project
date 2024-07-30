package com.hs.takeover.api.dto;
import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "error")
public class GWErrorResponseDto {
    private String code;

    private String message;

}