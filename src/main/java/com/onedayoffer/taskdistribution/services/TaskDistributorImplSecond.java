package com.onedayoffer.taskdistribution.services;

import com.onedayoffer.taskdistribution.web.api.v1.dto.EmployeeDto;
import com.onedayoffer.taskdistribution.web.api.v1.dto.TaskDto;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author Sviridov_V_A
 * @version 1.0.0-SNAPSHOT
 * @since 2024-03-23
 */
@Component("TaskDistributorImplSecond")
public class TaskDistributorImplSecond implements TaskDistributor {
    private static final int overheadTime = 420;

    @Override
    public void distribute(List<EmployeeDto> employees, List<TaskDto> tasks) {
        if (CollectionUtils.isEmpty(employees)) {
            throw new IllegalArgumentException("Employees is empty");
        }
        if (CollectionUtils.isEmpty(tasks)) {
            throw new IllegalArgumentException("Tasks is empty");
        }

        final var var1 = new ArrayBlockingQueue<EmployeeDto>(employees.size());
        var1.addAll(employees);
        tasks.parallelStream()
                .sorted(Comparator.comparingInt(TaskDto::getPriority))
                .forEachOrdered(task -> var1.forEach(employee -> {
                    if (employee.getTotalLeadTime() + task.getLeadTime() < overheadTime) {
                        employee.getTasks().add(task);
                    } else {
                        var1.remove(employee);
                    }
                }));
    }
}
