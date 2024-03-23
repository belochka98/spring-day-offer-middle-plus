package com.onedayoffer.taskdistribution.services;

import com.onedayoffer.taskdistribution.web.api.v1.dto.EmployeeDto;
import com.onedayoffer.taskdistribution.web.api.v1.dto.TaskDto;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author Sviridov_V_A
 * @version 1.0.0-SNAPSHOT
 * @since 2024-03-23
 */
public abstract class AbstractTaskDistributor implements TaskDistributor {
    protected static final int overheadTime = 420;

    protected abstract void action(List<EmployeeDto> employees, List<TaskDto> tasks);

    @Override
    public void distribute(List<EmployeeDto> employees, List<TaskDto> tasks) {
        if (CollectionUtils.isEmpty(employees)) {
            throw new IllegalArgumentException("Employees is empty");
        }
        if (CollectionUtils.isEmpty(tasks)) {
            throw new IllegalArgumentException("Tasks is empty");
        }

        this.action(employees, tasks);
    }
}
