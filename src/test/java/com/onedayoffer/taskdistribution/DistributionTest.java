package com.onedayoffer.taskdistribution;

import com.onedayoffer.taskdistribution.web.api.v1.dto.EmployeeDto;
import com.onedayoffer.taskdistribution.utils.DataGenerator;
import com.onedayoffer.taskdistribution.web.api.v1.dto.TaskDto;
import com.onedayoffer.taskdistribution.services.TaskDistributor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class DistributionTest {

    @Autowired
    private DataGenerator dataGenerator;

    @Autowired
    private TaskDistributor taskDistributor;

    @Test
    public void shouldDistribute() {
        List<EmployeeDto> employees = dataGenerator.getEmployees();
        List<TaskDto> tasks = dataGenerator.getTasks();
        taskDistributor.distribute(employees, tasks);

        employees.forEach((e) -> System.out.println(e.getFio() + ' ' + e.getTotalLeadTime()));

        assert employees.get(0).getTasks().size() >= 2;
        assert employees.get(1).getTasks().size() >= 2;
        assert employees.get(2).getTasks().size() >= 2;
        assert employees.get(3).getTasks().size() >= 2;
        assert employees.get(4).getTasks().size() >= 2;
        assert employees.get(0).getTotalLeadTime() >= 360;
        assert employees.get(1).getTotalLeadTime() >= 360;
        assert employees.get(2).getTotalLeadTime() >= 360;
        assert employees.get(3).getTotalLeadTime() >= 360;
        assert employees.get(4).getTotalLeadTime() >= 360;
        assert employees.get(0).getTotalLeadTime() <= 420;
        assert employees.get(1).getTotalLeadTime() <= 420;
        assert employees.get(2).getTotalLeadTime() <= 420;
        assert employees.get(3).getTotalLeadTime() <= 420;
        assert employees.get(4).getTotalLeadTime() <= 420;
    }
}