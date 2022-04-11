package com.apanin.todo.service.rest.task;

import com.apanin.todo.config.api.WebConfig;
import com.apanin.todo.sample.rest.api.TaskApiController;
import com.apanin.todo.sample.rest.api.TasksApiController;
import com.apanin.todo.sample.rest.model.Task;
import com.apanin.todo.task.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {TaskApiController.class, TasksApiController.class,
        TaskController.class, MappingJackson2HttpMessageConverter.class})
@AutoConfigureMockMvc
@EnableWebMvc
@ExtendWith(SpringExtension.class)
public class TaskRestServiceTest {

    @MockBean
    private TaskService taskService;

    @MockBean
    private WebConfig webConfig;

    @Autowired
    private TaskController taskController;

    @Autowired
    MockMvc mockMvc;

    private static final ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();

    @Test
    public void createTaskTest() throws Exception {
        final Task task = new Task();
        task.setTitle("Test title");
        task.setDescription("Test description");
        task.setUserId(1L);
        task.setUpdateDate(OffsetDateTime.now());
        Mockito.when(taskService.createTask(ArgumentMatchers.any())).thenReturn(1L);
        Mockito.when(webConfig.getBaseUrl()).thenReturn("http://localhost:8080");
        mockMvc.perform(post("/task", 1L)
                        .content(objectMapper.writeValueAsString(task)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location", "http://localhost:8080/task/1"));
    }

    @Test
    public void updateTaskTest() throws Exception {
        final Task task = new Task();
        task.setTitle("Test title");
        task.setDescription("Test description");
        task.setUserId(1L);
        task.setUpdateDate(OffsetDateTime.now());
        Mockito.when(webConfig.getBaseUrl()).thenReturn("http://localhost:8080");
        mockMvc.perform(put("/task", 1L)
                        .content(objectMapper.writeValueAsString(task)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void getTaskByIdTest() throws Exception {
        final Task task = new Task();
        task.setTitle("Test title");
        task.setDescription("Test description");
        task.setUserId(1L);
        task.setUpdateDate(OffsetDateTime.now());
        Mockito.when(taskService.getTask(1)).thenReturn(task);
        Mockito.when(webConfig.getBaseUrl()).thenReturn("http://localhost:8080");
        mockMvc.perform(get("/task/{taskId}", 1L))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(task)));
    }

    @Test
    public void getTasksListTest() throws Exception {
        final Task task = new Task();
        task.setTitle("Test title");
        task.setDescription("Test description");
        task.setUserId(1L);
        task.setUpdateDate(OffsetDateTime.now());
        final List<Task> taskList = Collections.singletonList(task);
        Mockito.when(taskService.listTasks(1, 1)).thenReturn(taskList);
        Mockito.when(webConfig.getBaseUrl()).thenReturn("http://localhost:8080");
        Mockito.when(webConfig.getItemsOnPage()).thenReturn(10);
        mockMvc.perform(get("/tasks/{userId}", 1L).param("limit", "10").param("after_id",
                        "1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(taskList)));
    }
}
