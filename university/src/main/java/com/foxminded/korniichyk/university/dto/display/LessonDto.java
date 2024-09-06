package com.foxminded.korniichyk.university.dto.display;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Data
public class LessonDto {

    private Long id;
    private DisciplineDto discipline;
    private LessonTypeDto lessonType;
    private Integer classroomNumber;
    private Set<TeacherDto> teachers;
    private Set<GroupDto> groups;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

}

