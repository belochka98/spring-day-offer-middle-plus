package com.onedayoffer.taskdistribution.services;

import com.onedayoffer.taskdistribution.web.api.v1.dto.EmployeeDto;
import com.onedayoffer.taskdistribution.web.api.v1.dto.TaskDto;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.List;

/**
 * @author Sviridov_V_A
 * @version 1.0.0-SNAPSHOT
 * @since 2024-03-23
 */
@Component("TaskDistributorImplSecond")
public class TaskDistributorImplSecond extends AbstractTaskDistributor {
    @Override
    protected void action(List<EmployeeDto> employees, List<TaskDto> tasks) {
        final var var1 = new ArrayDeque<EmployeeDto>(employees.size());
        var1.addAll(employees);

        final var streamTask = tasks.size() < 10000
                               ? tasks.stream()
                               : tasks.parallelStream();

        streamTask.sorted(Comparator.comparingInt(TaskDto::getPriority))
                .forEachOrdered(task -> (employees.size() < 10000 ? employees.stream() : employees.parallelStream()).forEach(employee -> {
                    if (employee.getTotalLeadTime() + task.getLeadTime() < overheadTime) {
                        employee.getTasks().add(task);
                    } else {
                        var1.remove(employee);
                    }
                }));
    }
}
