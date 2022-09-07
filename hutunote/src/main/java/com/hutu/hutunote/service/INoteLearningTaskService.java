package com.hutu.hutunote.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hutu.hutunote.model.entity.NoteLearningTask;

public interface INoteLearningTaskService extends IService<NoteLearningTask> {

    public void buildFirstTask(String noteId);

    public void buildNextTask(String noteId);

}
