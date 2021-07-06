package com.cloudmusic.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "api_log")
@Entity
public class ApiLog {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String methodName;
    private String apiName;
    private String method;
    private String params;
    private Long responseTime;
    private String result;
    private Integer log_type;
    private Date requestDate;
    private String url;
    private String requestIp;
}
