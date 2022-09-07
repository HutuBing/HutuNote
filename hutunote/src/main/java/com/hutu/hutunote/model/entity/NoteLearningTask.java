package com.hutu.hutunote.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class NoteLearningTask extends BaseEntity{

    private String noteId;
    private Date planTime;
    private Date actualTime;
    private Integer memoryPercentage;

}
