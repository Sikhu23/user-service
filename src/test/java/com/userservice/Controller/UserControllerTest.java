package com.userservice.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.userservice.Const.ConstantFile;
import com.userservice.Enum.Gender;
import com.userservice.Model.User;
import com.userservice.Model.UserDTO;
import com.userservice.Service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mockMvc;


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetUsers()throws Exception {
        List<UserDTO> userDTO = createUserList();

        Mockito.when(userService.showAllUsers(1, 2)).thenReturn(userDTO);

        mockMvc.perform(get("/users?page=1&pageSize=2"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", Matchers.is("firstTest")));
    }



    private List<UserDTO> createUserList() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c= sdf.parse("2015-05-26");
        List<UserDTO> users = new ArrayList<>();

        UserDTO user1 = new UserDTO();
        user1.setUserID("A123A");
        user1.setFirstName("firstTest");
        user1.setMiddleName("J");
        user1.setLastName("S");
        user1.setPhoneNumber("9090909090");
        user1.setEmail("prabhu@mail.com");
        user1.setDateOfBirth(c);
        user1.setEmployeeNumber("12345");
        user1.setBloodGroup("O+");
        user1.setGender(String.valueOf(Gender.MALE));
        user1.setAddress("Pune");

        UserDTO user2 = new UserDTO();
        user2.setUserID("A123A");
        user2.setFirstName("secondTest");
        user2.setMiddleName("J");
        user2.setLastName("S");
        user2.setPhoneNumber("9090909090");
        user2.setEmail("prabhu@mail.com");
        user2.setDateOfBirth(c);
        user2.setEmployeeNumber("12345");
        user2.setBloodGroup("O+");
        user2.setGender(String.valueOf(Gender.MALE));
        user2.setAddress("Pune");

        users.add(user1);
        users.add(user2);
        return users;
    }

    @Test
    public void testGetUserByID() throws Exception {
        UserDTO UserDTO = createOneUser();

        Mockito.when(userService.findByID("1")).thenReturn(UserDTO);

        mockMvc.perform(get("/users/1"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$", Matchers.aMapWithSize(11)))
                .andExpect(jsonPath("$.firstName", Matchers.is("FirstID")));
    }

    private UserDTO createOneUser() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c= sdf.parse("2015-05-26");
        UserDTO user1 = new UserDTO();

        user1.setUserID("1");
        user1.setFirstName("FirstID");
        user1.setMiddleName("J");
        user1.setLastName("S");
        user1.setPhoneNumber("9090909090");
        user1.setEmail("prabhu@mail.com");
        user1.setDateOfBirth(c);
        user1.setEmployeeNumber("12345");
        user1.setBloodGroup("O+");
        user1.setGender(String.valueOf(Gender.MALE));
        user1.setAddress("Pune");
        return user1;
    }

    @Test
    public void testGetUserByEmail() throws Exception {
        User user = createOneUserToPost();

        Mockito.when(userService.userByEmail("prabhu@mail.com")).thenReturn(user);

        mockMvc.perform(get("/users/getUserByEmail/prabhu@mail.com"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$", Matchers.aMapWithSize(12)))
                .andExpect(jsonPath("$.firstName", Matchers.is("Prabhu")));
    }

    @Test
    public void testDeleteUser() throws Exception {

        Mockito.when(userService.deleteUserById("1")).thenReturn(ConstantFile.SUCCESSDELETE);

        /*mockMvc.perform(delete("/users/1"))
                .andDo(print());*/
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$",Matchers.is(ConstantFile.SUCCESSDELETE)));

    }

    @Test
    public void testCreateUser() throws Exception {
        User user = createOneUserToPost();

        UserDTO userDTO = createUserDTO();

        Mockito.when(userService.saveUser(user)).thenReturn(userDTO);
        mockMvc.perform(post("/users")
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.firstName",Matchers.is("Prabhu")));

    }

    private UserDTO createUserDTO() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c= sdf.parse("2015-05-26");
        UserDTO user = new UserDTO();
        user.setUserID("1");
        user.setFirstName("Prabhu");
        user.setMiddleName("J");
        user.setLastName("S");
        user.setPhoneNumber("9090909090");
        user.setEmail("prabhu@mail.com");
        user.setEmployeeNumber("12345");
        user.setBloodGroup("O+");
        user.setGender(String.valueOf(Gender.MALE));
        user.setAddress("Pune");
        user.setDateOfBirth(c);

        return user;
    }

    private User createOneUserToPost() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c= sdf.parse("2015-05-26");
        User user = new User();
        user.setUserID("1");
        user.setFirstName("Prabhu");
        user.setMiddleName("J");
        user.setLastName("S");
        user.setPhoneNumber("9090909090");
        user.setEmail("prabhu@mail.com");
        user.setEmployeeNumber("12345");
        user.setBloodGroup("O+");
        user.setGender(Gender.MALE);
        user.setPassword("12345");
        user.setAddress("Pune");
        user.setDateOfBirth(c);

        return user;
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = createOneUserToPost();
        UserDTO userDTO =createUserDTO() ;

        Mockito.when(userService.changeDetails(user, "1")).thenReturn(userDTO);
        mockMvc.perform(put("/users/1")
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.firstName",Matchers.is("Prabhu")));
    }

}
