package com.userservice.Controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.userservice.Enum.BloodGroup;
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
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import javax.ws.rs.core.MediaType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private  UserController userController;
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

    private static  User createOneUser(){


        User user= new User("1","Natsu","Igneel",
                "Dragneel","9710532160",new Date(2022-03-21), Gender.MALE,
                "Bhilai","123",
                BloodGroup.A_POS,"qw@ex.com","123");
        return user;
    }

    private static  UserDTO createOneUserDto() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c= sdf.parse("2015-05-26");


        UserDTO userdto= new UserDTO("1","Natsu","Igneel",
                "Dragneel","9710532160",c, "MALE",
                "Bhilai","123",
                "A+","qw@ex.com");
        return userdto;
    }

    private static  List<User> createListUser(){

        List<User> users = new ArrayList<>();
        User user= new User(null,"Natsu","Igneel",
                "Dragneel","9710532160",new Date(2022-03-21), Gender.MALE,
                "Bhilai","123",
                BloodGroup.A_POS,"qw@ex.com","123");
        User user1= new User(null,"Natsu","Igneel",
                "Dragneel","9710532160",new Date(2022-03-21), Gender.MALE,
                "Bhilai","123",
                BloodGroup.A_POS,"qw1@ex.com","123");
        users.add(user);
        users.add(user1);
        return users;
    }
    private static  List<UserDTO> createListDTOUser(){

        List<UserDTO> users = new ArrayList<>();
        UserDTO user= new UserDTO(null,"Natsu","Igneel",
                "Dragneel","9710532160",new Date(2022-03-21), "MALE",
                "Bhilai","123",
                "A_POS","qw@ex.com");

        UserDTO user1= new UserDTO(null,"fg","Igneel",
                "Dragneel","9710532160",new Date(2022-03-21), "MALE",
                "Bhilai","123",
                "A_POS","qw1@ex.com");
        users.add(user);
        users.add(user1);
        return users;
    }

    @Test
    void showAllUsers() throws Exception {


//         List<User> userList= createListUser();
         List<UserDTO> userDTOList=createListDTOUser();

        Mockito.when(userService.showAllUsers(null,null)).thenReturn(userDTOList);

        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", Matchers.is("Natsu")))
                .andExpect(jsonPath("$[1].firstName", Matchers.is("fg")));
    }

    @Test
    void findByID() throws Exception {

        UserDTO userDTO = createOneUserDto();
        Mockito.when(userService.findByID("1")).thenReturn(userDTO);
        mockMvc.perform(get("/users/1"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$",Matchers.aMapWithSize(11)))
                .andExpect(jsonPath("$.firstName",Matchers.is("Natsu")));
    }





    @Test
    void changeDetails() {
    }

    @Test
    void deleteUserById() throws Exception {
//        User user =createOneUser();
        UserDTO userDTO=createOneUserDto();
        System.out.println("Date "+userDTO.getDateOfBirth());
//        Mockito.when(userService.deleteUserById("1")).thenReturn("Deleted Succcessfully");
        Mockito.when(userService.findByID("3")).thenReturn(userDTO);
//        Mockito.when(userService.saveUser(user)).thenReturn(user);

        mockMvc.perform(delete("/users/2"))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    void userByEmail() throws Exception {
        User user = createOneUser();
        Mockito.when(userService.userByEmail("qw@ex.com")).thenReturn(user);
        mockMvc.perform(get("/users/getUserByEmail/qw@ex.com"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$",Matchers.aMapWithSize(12)))
                .andExpect(jsonPath("$.email",Matchers.is("qw@ex.com")));
    }
}