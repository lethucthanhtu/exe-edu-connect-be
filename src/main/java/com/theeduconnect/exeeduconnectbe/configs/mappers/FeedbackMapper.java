package com.theeduconnect.exeeduconnectbe.configs.mappers;

import com.theeduconnect.exeeduconnectbe.domain.CourseFeedback;
import com.theeduconnect.exeeduconnectbe.features.feedback.payload.request.NewFeedbackRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {
    @Mapping(target = "postdate", ignore = true)
    @Mapping(target = "attendingcourse", ignore = true)
    CourseFeedback NewFeedbackRequestToCourseFeedback(NewFeedbackRequest request);
}
