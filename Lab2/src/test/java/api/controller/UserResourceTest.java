package api.controller;

import api.dto.UserDTO;
import api.service.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class UserResourceTest {
    private static final UserResource userResource=new UserResource();
    private static final UserService userService=new UserService();
    private final static UserDTO userDTO=new UserDTO();
    @BeforeAll
    static void  settingUp(){
        userDTO.setName("peasant2");
        userDTO.setPassword("password");
        Set<String> roles=new HashSet<>(Arrays.asList("peasant"));
        userDTO.setRoles(roles);
    }
    @Test
    void postUserTest_ExpectServiceReadEqual_ValidDTO(){
        userResource.postUser(userDTO);
        assertEquals(userDTO,userService.readById("peasant2"));
    }
    @Test
    void getUserTest_ExpectServiceReadEqual_ValidUserName(){
        assertEquals(userDTO,userResource.getUser("peasant2").getEntity());
    }
    @Test
    void getUserTest_ExpectServiceReadEqual_InvalidUserName(){
        assertEquals(400,userResource.getUser("peasant1").getStatus());
    }
    @Test
    void putUserTest_ExpectServiceReadEqual_ValidUserDTO(){
        UserDTO nu_peasant=userDTO;
        nu_peasant.setPassword("123");
        System.out.println(userResource.updateUser(nu_peasant));
        assertEquals(nu_peasant,userService.readById("peasant2"));
    }
    @Test
    void deleteUserTest_ExpectServiceReadEqual_ValidUserId(){
        UserDTO nu_peasant=userDTO;
        nu_peasant.setName("123");
        nu_peasant.setPassword("123");
        userResource.updateUser(nu_peasant);
        userResource.deleteUser("123");
        assertNull(userService.readById("123"));
    }
    @Test
    void deleteUserTest_ExpectServiceReadEqual_InvalidUserId(){
        UserDTO nu_peasant=userDTO;
        nu_peasant.setName("123");
        nu_peasant.setPassword("123");
        userResource.updateUser(nu_peasant);
        userResource.deleteUser("132");
        assertEquals(400,userResource.deleteUser("132").getStatus());
        userResource.deleteUser("123");
    }
    @AfterAll
    static void closing(){
        userService.delete("peasant2");
    }



}