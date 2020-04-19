package com.curevent;

import com.curevent.exceptions.ConflictException;
import com.curevent.models.entities.Category;
import com.curevent.models.entities.Relationship;
import com.curevent.models.transfers.CategoryTransfer;
import com.curevent.models.transfers.RelationshipTransfer;
import com.curevent.repositories.RelationshipRepository;
import com.curevent.services.RelationshipService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RelationshipServiceTests {
    public static final UUID USER_ID = UUID.fromString("d5338977-923a-4913-92a6-243246084a57");
    public static final UUID NEW_USER_ID = UUID.fromString("d5338977-923a-4913-92a6-243246084a58");
    public static final UUID NEW_FRIEND_ID = UUID.fromString("d5338977-923a-4913-92a6-243246084a61");
    public static final UUID RELATIONSHIP_ID = UUID.fromString("d5338977-923a-4913-92a6-243246084a59");
    public static final UUID NEW_RELATIONSHIP_ID = UUID.fromString("d5338977-923a-4913-92a6-243246084a60");
    public static final Long CATEGORY_ID = 1L;
    public static final String CATEGORY = "category";

    private CategoryTransfer categoryTransfer;
    private Relationship relationship;

    @MockBean
    private RelationshipRepository relationshipRepository;

    @Resource
    private RelationshipService relationshipService;

    @BeforeAll
    public void setUp(){
        Category category = new Category(CATEGORY_ID, CATEGORY, USER_ID);
        categoryTransfer = new CategoryTransfer(CATEGORY_ID, CATEGORY, USER_ID);
        relationship = new Relationship(RELATIONSHIP_ID, USER_ID, NEW_USER_ID, category);
    }

    @Test
    public void create_and_get_new_relationship_with_user_id_new_user_id_and_category_test () {
        when(relationshipRepository.findEqualsRelationship(USER_ID, NEW_USER_ID, CATEGORY_ID)).thenReturn(Optional.empty());
        when(relationshipRepository.save(Mockito.any(Relationship.class))).thenAnswer(i -> i.getArguments()[0]);

        RelationshipTransfer relationship = new RelationshipTransfer(RELATIONSHIP_ID, USER_ID, NEW_USER_ID, categoryTransfer);
        relationship = relationshipService.add(relationship);
        assertEquals(USER_ID, relationship.getOwnerId());
        assertEquals(NEW_USER_ID, relationship.getFriendId());
        assertEquals(CATEGORY_ID, relationship.getCategory().getId());
    }

    @Test
    public void create_equals_relationship_and_get_exception_test () {
        when(relationshipRepository.findEqualsRelationship(USER_ID, NEW_USER_ID, CATEGORY_ID)).thenReturn(Optional.of(relationship));
        when(relationshipRepository.save(Mockito.any(Relationship.class))).thenAnswer(i -> i.getArguments()[0]);

        RelationshipTransfer relationship = new RelationshipTransfer(NEW_RELATIONSHIP_ID, USER_ID, NEW_USER_ID, categoryTransfer);
        assertThrows(ConflictException.class , () -> relationshipService.add(relationship));
    }

    @Test
    public void send_update_equals_relationship_and_get_exception_test () {
        when(relationshipRepository.existsById(NEW_RELATIONSHIP_ID)).thenReturn(true);
        when(relationshipRepository.findEqualsRelationship(USER_ID, NEW_USER_ID, CATEGORY_ID)).thenReturn(Optional.of(relationship));
        when(relationshipRepository.save(Mockito.any(Relationship.class))).thenAnswer(i -> i.getArguments()[0]);

        RelationshipTransfer relationship = new RelationshipTransfer(NEW_RELATIONSHIP_ID, USER_ID, NEW_USER_ID, categoryTransfer);
        assertThrows(ConflictException.class , () -> relationshipService.update(relationship));
    }

    @Test
    public void update_relationship_and_get_relationship_with_new_friend_id_test () {
        when(relationshipRepository.existsById(NEW_RELATIONSHIP_ID)).thenReturn(true);
        when(relationshipRepository.findEqualsRelationship(USER_ID, NEW_USER_ID, CATEGORY_ID)).thenReturn(Optional.of(relationship));
        when(relationshipRepository.save(Mockito.any(Relationship.class))).thenAnswer(i -> i.getArguments()[0]);

        RelationshipTransfer relationship = new RelationshipTransfer(NEW_RELATIONSHIP_ID, USER_ID, NEW_FRIEND_ID, categoryTransfer);
        relationship = relationshipService.update(relationship);
        assertEquals(USER_ID, relationship.getOwnerId());
        assertEquals(NEW_FRIEND_ID, relationship.getFriendId());
        assertEquals(CATEGORY_ID, relationship.getCategory().getId());
    }

    @Test
    public void send_update_already_exists_relationship_and_get_relationship_back () {
        when(relationshipRepository.existsById(RELATIONSHIP_ID)).thenReturn(true);
        when(relationshipRepository.findEqualsRelationship(USER_ID, NEW_USER_ID, CATEGORY_ID)).thenReturn(Optional.of(relationship));
        when(relationshipRepository.save(Mockito.any(Relationship.class))).thenAnswer(i -> i.getArguments()[0]);

        RelationshipTransfer relationship = new RelationshipTransfer(RELATIONSHIP_ID, USER_ID, NEW_USER_ID, categoryTransfer);
        relationship = relationshipService.update(relationship);
        assertEquals(USER_ID, relationship.getOwnerId());
        assertEquals(NEW_USER_ID, relationship.getFriendId());
        assertEquals(CATEGORY_ID, relationship.getCategory().getId());
    }
}
