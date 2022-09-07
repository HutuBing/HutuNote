package com.hutu.hutunote.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.hutunote.mapper.NoteLearningTaskMapper;
import com.hutu.hutunote.model.entity.NoteLearningTask;
import com.hutu.hutunote.service.INoteLearningTaskService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class NoteLearningTaskServiceImpl extends ServiceImpl<NoteLearningTaskMapper, NoteLearningTask> implements INoteLearningTaskService {

    private Map<Integer, Long> gapTimeMap = new HashMap();

    @PostConstruct
    public void initGapTimeMap() {
        //5分钟、30分钟、12小时、1天、2天、4天、7天、15天、1月、3月、6月
        gapTimeMap.put(1, 5 * 60 * 1000L);
        gapTimeMap.put(2, 30 * 60 * 1000L);
        gapTimeMap.put(3, 12 * 60 * 60 * 1000L);
        gapTimeMap.put(4, 1 * 24 * 60 * 60 * 1000L);
        gapTimeMap.put(5, 2 * 24 * 60 * 60 * 1000L);
        gapTimeMap.put(6, 4 * 24 * 60 * 60 * 1000L);
        gapTimeMap.put(7, 7 * 24 * 60 * 60 * 1000L);
        gapTimeMap.put(8, 15 * 24 * 60 * 60 * 1000L);
        gapTimeMap.put(9, 1 * 30 * 24 * 60 * 60 * 1000L);
        gapTimeMap.put(10, 3 * 30 * 24 * 60 * 60 * 1000L);
        gapTimeMap.put(11, 6 * 30 * 24 * 60 * 60 * 1000L);
    }

    @Override
    public void buildFirstTask(String noteId) {
        NoteLearningTask task = new NoteLearningTask();
        task.setNoteId(noteId);
        task.setPlanTime(new Date());
        task.setActualTime(task.getPlanTime());
        task.setMemoryPercentage(100);
        baseMapper.insert(task);
        //生成第二次学习计划
        this.buildNextTask(noteId);
    }

    @Override
    public void buildNextTask(String noteId) {
        List<NoteLearningTask> list = baseMapper.selectList(
                new LambdaQueryWrapper<NoteLearningTask>()
                        .eq(NoteLearningTask::getNoteId, noteId)
                        .orderByDesc(NoteLearningTask::getActualTime)
        );

        if(list.size() == 0) {
            this.buildFirstTask(noteId);
            return;
        }

        Date lastTime = list.get(0).getActualTime();
        Date nextTime = new Date(lastTime.getTime() + gapTimeMap.get(list.size()));
        //生成学习计划
        NoteLearningTask task = new NoteLearningTask();
        task.setNoteId(noteId);
        task.setPlanTime(nextTime);
        baseMapper.insert(task);
    }
}
