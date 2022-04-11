package com.userservice.Service;

import com.userservice.Const.ConstantFile;
import com.userservice.Enum.Gender;
import com.userservice.Exception.EmailAlreadyExistsException;
import com.userservice.Exception.EnumException;
import com.userservice.Exception.UserNotFoundException;
import com.userservice.Model.User;
import com.userservice.Model.UserDTO;
import com.userservice.Repository.UserRepo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceTest {

    @InjectMocks
    UserService service;

    @Mock
    UserRepo userRepo;


    @Test
    void testCreateUser() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c= sdf.parse("2015-05-26");
        UserDTO userWithOutPassword = createOneUserToResponse();
        User userRequest = createOneUserRequestToPost();
        User user = new User();
        user.setUserID("1");
        user.setFirstName("firstTest");
        user.setMiddleName("J");
        user.setLastName("S");
        user.setPhoneNumber("9090909090");
        user.setEmail("natsu@mail.com");
        user.setDateOfBirth(c);
        user.setEmployeeNumber("12345");
        user.setBloodGroup("O+");
        user.setGender(Gender.MALE);
        user.setPassword("1234");

        Mockito.when(userRepo.save(any(User.class))).thenReturn(user);
        User savedUser = userRepo.save(user);
//        when(service.saveUser(userRequest)).thenReturn(userWithOutPassword);
        assertThat(savedUser.getFirstName()).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo("natsu@mail.com");
    }



    @Test
    void testDeleteUserById() throws ParseException {


        userRepo.deleteById("1");
        verify(userRepo, times(1)).deleteById("1");


    }

    @Test
    void testExceptionThrownWhenUserNotFound() {
        Mockito.doThrow(UserNotFoundException.class).when(userRepo).deleteById(any());
        Exception userNotFoundException = assertThrows(UserNotFoundException.class, () -> service.deleteUserById("1"));
        assertTrue(userNotFoundException.getMessage().contains(ConstantFile.ERRORCODE));
    }

    @Test
    void testExceptionThrownWhenUserIdNotFound() throws ParseException {
        User user1 = createOneUserToUpdate();
        UserDTO userWithOutPassword = createOneUserToResponse();
        User userRequest = createOneUserRequestToPost();
        Mockito.when(userRepo.findById("1")).thenReturn(Optional.ofNullable(user1));
        assertThat(service.changeDetails(userRequest, "1")).isEqualTo(userWithOutPassword);
        assertThrows(UserNotFoundException.class, () -> service.changeDetails(userRequest, null));
    }

    @Test
    void testGetUserById() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c= sdf.parse("2015-05-26");
        User user = new User();
        user.setUserID("1");
        user.setFirstName("firstTest");
        user.setMiddleName("J");
        user.setLastName("S");
        user.setPhoneNumber("9090909090");
        user.setEmail("natsu@mail.com");
        user.setDateOfBirth(c);
        user.setEmployeeNumber("12345");
        user.setBloodGroup("O+");
        user.setGender(Gender.MALE);
        user.setPassword("1234");


        UserDTO UserWithOutPassword = createOneUserToResponse();
        Mockito.when(userRepo.findById("1")).thenReturn(Optional.ofNullable(user));

        assertThat(service.findByID(user.getUserID())).isEqualTo(UserWithOutPassword);
        assertThrows(UserNotFoundException.class, () -> service.findByID(null));
    }

    @Test
    void testGetUserDetailsByEmail() throws ParseException {
        User user = new User();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c= sdf.parse("2015-05-26");
        user.setUserID("1");
        user.setFirstName("firstTest");
        user.setMiddleName("J");
        user.setLastName("S");
        user.setPhoneNumber("9090909090");
        user.setEmail("natsu@mail.com");
        user.setDateOfBirth(c);
        user.setEmployeeNumber("12345");
        user.setBloodGroup("O+");
        user.setGender(Gender.MALE);
        user.setPassword("1234");

        UserDTO UserWithOutPassword = createOneUserToResponse();

        Mockito.when(userRepo.findByemail("natsu@mail.com")).thenReturn(user);
        assertThat(service.userByEmail(user.getEmail())).isEqualTo(user);
        assertThrows(UserNotFoundException.class, () -> service.userByEmail(null));
    }

    @Test
    void testGetAllUsers() throws ParseException {
        User user = createOneUserToCheck();
        ArrayList<User> users = new ArrayList<>();
        users.add(user);
        PageImpl<User> pageImpl = new PageImpl<>(users);

        when(this.userRepo.findAll((org.springframework.data.domain.Pageable) any())).thenReturn(pageImpl);
        assertEquals(1, this.service.showAllUsers(1, 3).size());

    }

    private UserDTO createOneUserToResponse() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c= sdf.parse("2015-05-26");
        UserDTO userWithOutPassword = new UserDTO();
        userWithOutPassword.setUserID("1");
        userWithOutPassword.setFirstName("firstTest");
        userWithOutPassword.setMiddleName("J");
        userWithOutPassword.setLastName("S");
        userWithOutPassword.setPhoneNumber("9090909090");
        userWithOutPassword.setEmail("natsu@mail.com");
        userWithOutPassword.setDateOfBirth(c);
        userWithOutPassword.setEmployeeNumber("12345");
        userWithOutPassword.setBloodGroup("O+");
        userWithOutPassword.setGender("MALE");
        return userWithOutPassword;
    }

    private User createOneUserRequestToPost() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c= sdf.parse("2015-05-26");
        User userRequest = new User();
        userRequest.setUserID("1");
        userRequest.setFirstName("firstTest");
        userRequest.setMiddleName("J");
        userRequest.setLastName("S");
        userRequest.setPhoneNumber("9090909090");
        userRequest.setEmail("natsu@mail.com");
        userRequest.setDateOfBirth(c);
        userRequest.setEmployeeNumber("12345");
        userRequest.setBloodGroup("O+");
        userRequest.setGender(Gender.MALE);
        userRequest.setPassword("12345");
        return userRequest;
    }


    private User createOneUserToUpdate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c= sdf.parse("2015-05-26");
        User user = new User();
        user.setUserID("1");
        user.setFirstName("firstTest");
        user.setMiddleName("J");
        user.setLastName("S");
        user.setPhoneNumber("9090909090");
        user.setEmail("natsu@mail.com");
        user.setDateOfBirth(c);
        user.setEmployeeNumber("12345");
        user.setBloodGroup("O+");
        user.setGender(Gender.MALE);
        user.setPassword("1234");

        return user;
    }

    private User createOneUserToCheck() throws ParseException {
        User user = new User();
        user.setUserID("1");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c= sdf.parse("2015-05-26");
        user.setFirstName("firstTest");
        user.setMiddleName("J");
        user.setLastName("S");
        user.setPhoneNumber("9090909090");
        user.setEmail("natsu@mail.com");
        user.setDateOfBirth(c);
        user.setEmployeeNumber("12345");
        user.setBloodGroup("O+");
        user.setGender(Gender.MALE);
        user.setPassword("1234");

        return user;
    }


    private List<User> createUsersList1() throws ParseException {
        List<User> users = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c= sdf.parse("2015-05-26");
        User UserWithOutPassword1 = new User();
        UserWithOutPassword1.setUserID("1");
        UserWithOutPassword1.setFirstName("firstTest");
        UserWithOutPassword1.setMiddleName("J");
        UserWithOutPassword1.setLastName("S");
        UserWithOutPassword1.setPhoneNumber("9090909090");
        UserWithOutPassword1.setEmail("natsu@mail.com");
        UserWithOutPassword1.setDateOfBirth(c);
        UserWithOutPassword1.setEmployeeNumber("12345");
        UserWithOutPassword1.setBloodGroup("O+");
        UserWithOutPassword1.setGender(Gender.MALE);

        User UserWithOutPassword2 = new User();
        UserWithOutPassword2.setUserID("1");
        UserWithOutPassword2.setFirstName("secondTest");
        UserWithOutPassword2.setMiddleName("J");
        UserWithOutPassword2.setLastName("S");
        UserWithOutPassword2.setPhoneNumber("9090909090");
        UserWithOutPassword2.setEmail("natsu@mail.com");
        UserWithOutPassword2.setDateOfBirth(c);
        UserWithOutPassword2.setEmployeeNumber("12345");
        UserWithOutPassword2.setBloodGroup("O+");
        UserWithOutPassword2.setGender(Gender.MALE);

        users.add(UserWithOutPassword1);
        users.add(UserWithOutPassword2);
        return users;
    }

    private List<UserDTO> createUsersList() throws ParseException {
        List<UserDTO> userWithOutPasswordList = new ArrayList<>();
        UserDTO userWithOutPassword = new UserDTO();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c= sdf.parse("2015-05-26");
        userWithOutPassword.setUserID("623e238c0f3e41623a004497");
        userWithOutPassword.setFirstName("Natsu");
        userWithOutPassword.setMiddleName("D");
        userWithOutPassword.setLastName("S");
        userWithOutPassword.setPhoneNumber("9876543210");
        userWithOutPassword.setEmail("natsu@mail.com");
        userWithOutPassword.setDateOfBirth(c);
        userWithOutPassword.setEmployeeNumber("7048");
        userWithOutPassword.setBloodGroup("O+");
        userWithOutPassword.setGender(String.valueOf(Gender.MALE));

        userWithOutPasswordList.add(userWithOutPassword);
        return userWithOutPasswordList;
    }
}