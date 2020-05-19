import React from "react";
import {currentUser, invalidateAuth} from "../../redux/actions/AuthActions";
import {getWhoAmI} from "../../redux/services/AuthService";
import '../../css/timeline.css'
import {connect} from "react-redux";
import Line from "./Line";
import Calendar from "./Calendar";
import {makeTwoCharacter} from "../../utils/FormaliseTime";
import { Component } from 'react';
import moment from 'moment';
import randomColor from 'randomcolor';
import Timeline, { TimelineHeaders, SidebarHeader, DateHeader } from 'react-calendar-timeline/lib';
import EventPopup from './EventPopup';
import {getUserEventsInInterval, getUser} from "../../redux/services/UserService";


import 'react-calendar-timeline/lib/Timeline.css';

const keys = {
  groupIdKey: 'id',
  groupTitleKey: 'title',
  groupRightTitleKey: 'rightTitle',
  itemIdKey: 'id',
  itemTitleKey: 'title',
  itemDivTitleKey: 'title',
  itemGroupKey: 'group',
  itemTimeStartKey: 'start',
  itemTimeEndKey: 'end',
  groupLabelKey: 'title',
};

class TimelineCurevent extends Component {

    // componentDidMount() {
    //     const id = this.props.userInfo.id;
    //     getUser(id).then(user => {
    //         this.props.saveUser(user);
    //         this.setState(user);
    //     });
    // }

  constructor(props) {
    super(props);
    const defaultTimeStart = moment().startOf('day').toDate();
    const defaultTimeEnd = moment().startOf('day').add(1, 'day').toDate();
    this.state = {
      defaultTimeStart,
      defaultTimeEnd,
      openedEvent: null,
    };
  }

  filterEventsData(events) {
    const items = [];

    for (const event of events) {
      items.push({
        id: event.id,
        className:
        moment(event.time).day() === 6 || moment(event.time).day() === 0 ? 'item-weekend' : '',
        start: Number(new Date(event.time)),
        end: Number(new Date(event.time)) + (event.duration * 60 * 1000),
        title: event.title,
        itemProps: {
          'data-tip': event.description,
          onDoubleClick: () => {
            this.setState({ openedEvent: event });
          },
        },
        group: event.group,
      });
    }

    return items;
  }

  createOwnerGroups(events) {
    const groups = [];
    let id = -1;
    for (const event of events) {
      if (!groups.some((group) => group.title === this.props.user.username)) {
        id++;
        const randomSeed = Math.floor(Math.random() * 1000);
        groups.push({
          id,
          title: this.props.user.username,
          bgColor: randomColor({ luminosity: 'light', seed: randomSeed + id }),
        });
      }
      event.group = id;
    }

    return groups;
  }

  render() {
    const { defaultTimeStart, defaultTimeEnd, openedEvent } = this.state;
    const groups = this.createOwnerGroups(this.props.listEvent);
    const items = this.filterEventsData(this.props.listEvent);

    return (
        <div className="timeline-container">
      <Calendar/>
      <div className="timeline">
        <Timeline
          groups={groups}
          items={items}
          keys={keys}
          sidebarContent={<div>Above The Left</div>}
          itemsSorted
          itemTouchSendsClick={false}
          stackItems
          itemHeightRatio={0.75}
          showCursorLine
          canMove={false}
          canResize={false}
          defaultTimeStart={defaultTimeStart}
          defaultTimeEnd={defaultTimeEnd}
        >
          <TimelineHeaders className="sticky">
            <SidebarHeader>
              {({ getRootProps }) => {
                return <div {...getRootProps()}></div>;
              }}
            </SidebarHeader>
            <DateHeader unit="primaryHeader" />
            <DateHeader />
          </TimelineHeaders>
        </Timeline>
        {openedEvent && (
          <EventPopup event={openedEvent} onClose={() => this.setState({ openedEvent: null })} />
        )}
      </div>
      </div>
    );
  }
}

const mapStateToProps = state => {
    return {
        userInfo: state.currentUser.userInfo,
        // events: state.listEvents,
    }
};

const mapDispatchToProps = {

};

export default connect(mapStateToProps, mapDispatchToProps)(TimelineCurevent);
