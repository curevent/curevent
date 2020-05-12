import React, {useState} from "react";
import {connect} from "react-redux";
import {getUser} from "../../redux/services/UserService";
import {saveUser} from "../../redux/actions/UserActions";
import {deleteRelationship, postRelationship} from "../../redux/services/RelationshipService";
import {relationshipReducer} from "../../redux/reducers/RelationshipReducer";

const AddFriendButton = ({id, userInfo}) => {

    const [isFriends, setFriends] = useState(false);

    const addFriend = () => {
        const relationship = {
            ownerId: userInfo.id,
            friendId: id,
            category: {
                id: 1,
                description: 'Все друзья',
                ownerId: null
            }
        };
        postRelationship(relationship).then(relationship => {
            getUser(userInfo.id).then(user => saveUser(user));
            setFriends(relationship.id);
        })
    };

    const deleteFromFriends = (id) => {
        deleteRelationship(id).then(ignore => {
            getUser(userInfo.id).then(user => saveUser(user));
            setFriends(false);
        })
    };

    getUser(userInfo.id).then(user => {
        user.relationships.map(relationship => {
            if (relationship.friendId === id) {
                setFriends(relationship.id);
            }
        })
    });

    if (userInfo.id !== id) {
        if (!isFriends) {
            return (
                <button className="add-friend-button" onClick={addFriend}>
                    Add friend
                </button>
            );
        } else {
            return (
                   <div className="friends-icon" onClick={e => deleteFromFriends(isFriends)}>

                   </div>
            );
        }
    } else {
        return (
            <div className="you-icon">
                You
            </div>
        );
    }
};

const mapStateToProps = state => {
    return {
        userInfo: state.currentUser.userInfo
    }
};



export default connect(mapStateToProps, null)(AddFriendButton);
