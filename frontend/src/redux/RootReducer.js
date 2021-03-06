import {combineReducers} from "redux";
import {tokensReducer} from "./reducers/auth/TokensReducer";
import {currentUserReducer} from "./reducers/auth/CurrentUserReducer";
import {userReducer} from "./reducers/UserReducer";
import {tagReducer} from "./reducers/TagReducer";
import {relationshipReducer} from "./reducers/RelationshipReducer";
import {categoryReducer} from "./reducers/CategoryReducer";
import {templateReducer} from "./reducers/TemplateReducer";
import {eventReducer} from "./reducers/EventReducer";
import {timelineReducer} from "./reducers/timeline/TimelineReducer";
import {repositoryReducer} from "./reducers/RepositoryReducer";

export const rootReducer = combineReducers(
    {
            auth: tokensReducer,
            currentUser: currentUserReducer,
            user: userReducer,
            tags: tagReducer,
            templates: templateReducer,
            events: eventReducer,
            categories: categoryReducer,
            friends: relationshipReducer,
            timeline: timelineReducer,
            repo: repositoryReducer
    }
);
