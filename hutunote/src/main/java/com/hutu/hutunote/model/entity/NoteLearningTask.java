package com.hutu.hutunote.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@TableName("TB_NOTE_LEARNING_TASK")
public class NoteLearningTask extends BaseEntity{

    private String noteId;
    private LocalDateTime planTime;
    private LocalDateTime actualTime;
    private Integer memoryPercentage;

}
