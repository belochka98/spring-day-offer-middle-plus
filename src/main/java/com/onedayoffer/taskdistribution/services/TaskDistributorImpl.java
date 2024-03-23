package com.onedayoffer.taskdistribution.services;

import com.onedayoffer.taskdistribution.web.api.v1.dto.EmployeeDto;
import com.onedayoffer.taskdistribution.web.api.v1.dto.TaskDto;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;

/**
 * @author Sviridov_V_A
 * @version 1.0.0-SNAPSHOT
 * @since 2024-03-23
 */
@Component("TaskDistributorImpl")
public class TaskDistributorImpl extends AbstractTaskDistributor {
    @Override
    protected void action(List<EmployeeDto> employees, List<TaskDto> tasks) {
        tasks.stream()
                .sorted(Comparator.comparingInt(TaskDto::getPriority))
                .forEach(task -> employees.forEach(employee -> {
                    if (employee.getTotalLeadTime() + task.getLeadTime() < overheadTime) {
                        employee.getTasks().add(task);
                    }
                }));
    }
}
