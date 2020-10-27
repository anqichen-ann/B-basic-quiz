package com.example.demo.controller;

import com.example.demo.dto.Education;
import com.example.demo.dto.User;
import com.example.demo.exception.SourceNotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.EducationService;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {
    @MockBean
    private UserService userService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private EducationService educationService;
    @Autowired
    private MockMvc mockMvc;
//    @Autowired
//    private JacksonTester<User> userJacksonTester;

    private User firstUser;
    private Education education;
    private final List<Education> educationList = new ArrayList<>();

    @BeforeEach
    public void beforeEach(){
        firstUser = User.builder()
                .id(123L)
                .age(18)
                .name("ann")
                .avatar("http://...")
                .description("mock description")
                .build();

        education = Education.builder()
                .id(1)
                .year(2005)
                .title("first year")
                .description("graduate from university")
                .user(firstUser)
                .build();
        educationList.add(education);
    }

    @AfterEach
    public void afterEach(){
        Mockito.reset(userService);
        Mockito.reset(educationService);
    }

    @Nested
    class GetUserById{

        @Nested
        class WhenUserExists {

            @Test
            public void should_get_user_by_id() throws Exception {
                when(userRepository.findById(123L)).thenReturn(Optional.of(firstUser));
                mockMvc.perform(get("/users/{id}", 123L))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.name",is("ann")));
            }
        }

        @Nested
        class WhenUserNotExist {

            @Test
            public void should_throw_exception_when_user_not_exist() throws Exception {
                when(userRepository.findById(123L)).thenReturn(Optional.empty());

                mockMvc.perform(get("/users/{id}", 123L))
                        .andExpect(status().isNotFound())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.message",containsString("User not found")));
                verify(userRepository).findById(123L);
            }
        }
    }

    @Nested
    class getEducation {

        @Nested
        class whenEducationExist {

            @Test
            public void should_get_education_list_when_education_exist() throws Exception {
                when(educationService.getEducationById(123L)).thenReturn(educationList);
                mockMvc.perform(get("/users/{id}/educations", 123L))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$",hasSize(1)))
                        .andExpect(jsonPath("$[0].year",is(2005)))
                        .andExpect(jsonPath("$[0].title",is("first year")))
                        .andExpect(jsonPath("$[0].description",is("graduate from university")));
            }
        }

        @Nested
        class whenEducationNotExist {

            @Test
            public void should_throw_exception_when_education_not_found() throws Exception {
                when(educationService.getEducationById(123L)).thenThrow(new SourceNotFoundException("education not found"));

                mockMvc.perform(get("/users/{id}/educations", 123L))
                        .andExpect(status().isNotFound())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.message",containsString("education not found")));
            }
        }
    }

    @Nested
    class createUser {
        private ObjectMapper objectMapper;

        @BeforeEach
        public void beforeEach() {
            objectMapper = new ObjectMapper();
        }

        @Nested
        class createUserSuccess {

            @Test
            public void should_create_user_success_when_user_valid() throws Exception {
                User newCreateUser = User.builder()
                        .id(111L)
                        .name(firstUser.getName())
                        .age(firstUser.getAge())
                        .avatar(firstUser.getAvatar())
                        .description(firstUser.getDescription())
                        .build();
                String request = objectMapper.writeValueAsString(firstUser);
                when(userService.createUser(firstUser)).thenReturn(newCreateUser);

                MockHttpServletRequestBuilder requestBuilder = post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request);

                MockHttpServletResponse response = mockMvc.perform(requestBuilder)
                        .andReturn()
                        .getResponse();
                assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
                verify(userService).createUser(firstUser);
            }
        }

        @Nested
        class createdUserFailed {

            @Test
            public void should_throw_exception_when_user_name_null() throws Exception {
                User invalidUser = User.builder()
                        .age(20)
                        .avatar("http://....")
                        .description("bob description..")
                        .build();
                String request = objectMapper.writeValueAsString(invalidUser);
                MockHttpServletRequestBuilder requestBuilder = post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request);
                mockMvc.perform(requestBuilder)
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message",is("姓名不为空")));
            }

            @Test
            public void should_throw_exception_when_user_age_not_in_range() throws Exception {
                User invalidUser = User.builder()
                        .name("ann")
                        .age(16)
                        .avatar("http://....")
                        .description("bob description..")
                        .build();
                String request = objectMapper.writeValueAsString(invalidUser);
                MockHttpServletRequestBuilder requestBuilder = post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request);
                mockMvc.perform(requestBuilder)
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message",is("must be greater than or equal to 17")));
            }

            @Test
            public void should_throw_exception_when_user_avatar_null() throws Exception {
                User invalidUser = User.builder()
                        .name("ann")
                        .age(20)
                        .description("bob description..")
                        .build();
                String request = objectMapper.writeValueAsString(invalidUser);
                MockHttpServletRequestBuilder requestBuilder = post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request);
                mockMvc.perform(requestBuilder)
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message",is("头像链接地址不为空")));
            }
        }
    }

    @Nested
    class createEducation {
        private ObjectMapper objectMapper;

        @BeforeEach
        public void beforeEach() {
            objectMapper = new ObjectMapper();
        }

        @Nested
        class createEducationSuccess {

            @Test
            public void should_create_education_success() throws Exception {
                Education newEducation = Education.builder()
                        .id(1L)
                        .user(firstUser)
                        .year(education.getYear())
                        .title(education.getTitle())
                        .description(education.getDescription())
                        .build();
                when(educationService.createEducation(111L, education)).thenReturn(newEducation);
                String request = objectMapper.writeValueAsString(education);
                MockHttpServletRequestBuilder requestBuilder = post("/users/{id}/educations",111L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request);

                MockHttpServletResponse response = mockMvc.perform(requestBuilder)
                        .andReturn()
                        .getResponse();
                assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
                verify(educationService).createEducation(111L, education);
            }
        }

        @Nested
        class createEducationFailed {

            @Test
            public void should_throw_exception_when_user_id_not_exist() throws Exception {
                String request = objectMapper.writeValueAsString(education);
                when(educationService.createEducation(111L, education)).thenThrow(new SourceNotFoundException("用户不存在"));
                mockMvc.perform(post("/users/{id}/educations", 111L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                        .andExpect(status().isNotFound())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.message",containsString("用户不存在")));
                verify(educationService).createEducation(111L, education);
            }

            @Test
            public void should_throw_exception_when_education_title_not_valid() throws Exception {
                Education invalidEducation = Education.builder()
                        .year(2005)
                        .description("graduate from university")
                        .user(firstUser)
                        .build();
                String request = objectMapper.writeValueAsString(invalidEducation);
                MockHttpServletRequestBuilder requestBuilder = post("/users/{id}/educations", 111L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request);
                mockMvc.perform(requestBuilder)
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message",is("标题不为空")));
            }
        }
    }
}
