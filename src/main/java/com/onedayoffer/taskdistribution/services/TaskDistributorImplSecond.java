package com.onedayoffer.taskdistribution.services;

import com.onedayoffer.taskdistribution.web.api.v1.dto.EmployeeDto;
import com.onedayoffer.taskdistribution.web.api.v1.dto.TaskDto;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author Sviridov_V_A
 * @version 1.0.0-SNAPSHOT
 * @since 2024-03-23
 */
@Component("TaskDistributorImplSecond")
public class TaskDistributorImplSecond extends AbstractTaskDistributor {
    /**
     * <p>Some overhead for parallel logic, usually just using another methods and/or interfaces</p>
     */
    @Override
    protected void action(List<EmployeeDto> employees, List<TaskDto> tasks) {
        // if we're using parallel logic we need to be sure that collections is tread-safe
        final var var1 = employees.size() < 10000
                         ? new ArrayDeque<EmployeeDto>(employees.size())
                         : new ArrayBlockingQueue<EmployeeDto>(employees.size());
        var1.addAll(employees);

        // just pick a stream
        final var streamTask = tasks.size() < 10000
                               ? tasks.stream()
                               : tasks.parallelStream();

        // some moment confusing me, mean - O(n*(log(y))) assymph, but now i can't do better, #needmoretime
        // ToDo: woops... if employees multiple less than tasks - we have a problem with overheadTime,
        //  I'll think about it later
        streamTask.sorted(Comparator.comparingInt(TaskDto::getPriority)).forEachOrdered(task ->
                (employees.size() < 10000 ? employees.stream() : employees.parallelStream()).forEach(employee -> {
                    if (employee.getTotalLeadTime() + task.getLeadTime() < overheadTime) {
                        employee.getTasks().add(task);
                    } else {
                        var1.remove(employee);
                    }
                }));
    }
}
