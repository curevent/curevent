package com.curevent;

import com.curevent.exceptions.ConflictException;
import com.curevent.models.entities.Category;
import com.curevent.models.entities.Relationship;
import com.curevent.models.entities.UserEntity;
import com.curevent.models.transfers.UserTransfer;
import com.curevent.repositories.UserRepository;
import com.curevent.services.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTests {
    public static final String USERNAME = "test";
    public static final String NEW_USERNAME = "test2";
    public static final String EMAIL = "test@test.com";
    public static final String NEW_EMAIL = "test@test.com";
    public static final String CITY = "city";
    public static final String NAME = "name";
    public static final String CATEGORY = "category";
    public static final long CATEGORY_ID = 1L;
    public static final UUID USER_ID = UUID.fromString("d5338977-923a-4913-92a6-243246084a57");
    public static final UUID NEW_USER_ID = UUID.fromString("d5338977-923a-4913-92a6-243246084a58");
    public static final UUID RELATIONSHIP_ID = UUID.fromString("d5338977-923a-4913-92a6-243246084a59");

    private UserEntity user;
    private UserEntity newUser;

    @MockBean
    private UserRepository userRepository;

    @Resource
    private UserService userService;


    @BeforeAll
    public void setUp() {
        user = createUser(USERNAME, EMAIL, USER_ID);
        newUser = createUser(NEW_USERNAME, NEW_EMAIL, NEW_USER_ID);

        Category category = new Category(CATEGORY_ID, CATEGORY, USER_ID);
        Relationship relationship = new Relationship(RELATIONSHIP_ID, USER_ID, NEW_USER_ID, category);
        user.setRelationships(List.of(relationship));
    }

    @Test
    public void update_and_return_user_with_new_city_and_name_test() {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(userRepository.save(Mockito.any(UserEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        UserTransfer user = userService.getOneById(USER_ID);
        assertNull(user.getCity());
        assertNull(user.getName());
        user.setCity(CITY);
        user.setName(NAME);
        user = userService.update(user);
        assertEquals(CITY, user.getCity());
        assertEquals(NAME, user.getName());
    }

    @Test
    public void set_user_already_exist_username_and_get_exception_test() {
        when(userRepository.findById(NEW_USER_ID)).thenReturn(Optional.of(newUser));
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));

        UserTransfer newUser = userService.getOneById(NEW_USER_ID);
        newUser.setUsername(USERNAME);
        assertThrows(ConflictException.class, () ->
                userService.update(newUser));
    }

    @Test
    public void get_one_user_friend_with_new_user_id_test() {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(userRepository.getOne(NEW_USER_ID)).thenReturn(newUser);

        List<UserTransfer> friends = userService.getUserFriends(USER_ID);
        assertEquals(1, friends.size());
        assertEquals(NEW_USER_ID, friends.get(0).getId());
    }

    private UserEntity createUser(String username, String email, UUID id){
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setEmail(email);
        userEntity.setId(id);
        return userEntity;
    }
}
