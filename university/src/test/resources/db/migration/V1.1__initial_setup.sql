create table if not exists disciplines
(
    id          bigint generated by default as identity
        constraint disciplines_pkey
            primary key,
    description text         not null,
    name        varchar(255) not null
);

create table if not exists lesson_types
(
    id   bigint generated by default as identity
        constraint lesson_types_pkey
            primary key,
    name varchar(255) not null
);

create table if not exists lessons
(
    id               bigint generated by default as identity
        constraint lessons_pkey
            primary key,
    classroom_number integer not null,
    date             date    not null,
    end_time         time(6) not null,
    start_time       time(6) not null,
    discipline_id    bigint
        constraint fk_lessons_discipline_id
            references disciplines,
    lesson_type_id   bigint
        constraint fk_lessons_lesson_type_id
            references lesson_types
);

create table if not exists roles
(
    id   bigint generated by default as identity
        constraint roles_pkey
            primary key,
    name varchar(255) not null
        constraint uk_roles_name
            unique
);

create table if not exists specialities
(
    id          bigint generated by default as identity
        constraint specialities_pkey
            primary key,
    code        integer      not null
        constraint uk_specialities_code
            unique,
    description text,
    name        varchar(255) not null
        constraint uk_specialities_name
            unique
);

create table if not exists groups
(
    id            bigint generated by default as identity
        constraint groups_pkey
            primary key,
    name          varchar(255) not null,
    speciality_id bigint
        constraint fk_groups_speciality
            references specialities
);

create table if not exists group_lesson
(
    group_id  bigint not null
        constraint fk_groups_lesson_group_id
            references groups,
    lesson_id bigint not null
        constraint fk_groups_lesson_lesson_id
            references lessons,
    constraint group_lesson_pkey
        primary key (group_id, lesson_id)
);

create table if not exists users
(
    id            bigint generated by default as identity
        constraint users_pkey
            primary key,
    date_of_birth date         not null,
    email         varchar(255) not null
        constraint uk_users_email
            unique,
    first_name    varchar(255) not null,
    last_name     varchar(255) not null,
    phone_number  varchar(255) not null
        constraint uk_users_phonenumber
            unique
);

create table if not exists admins
(
    id      integer generated by default as identity
        constraint admins_pkey
            primary key,
    user_id bigint
        constraint uk_admins_user_id
            unique
        constraint fk_admins_user
            references users
);

create table if not exists students
(
    id       bigint generated by default as identity
        constraint students_pkey
            primary key,
    group_id bigint
        constraint fk_students_group
            references groups,
    user_id  bigint
        constraint uk_students_user_id
            unique
        constraint fk_students_user
            references users
);

create table if not exists teachers
(
    id      bigint generated by default as identity
        constraint teachers_pkey
            primary key,
    user_id bigint
        constraint uk_teachers_user_id
            unique
        constraint fk_teachers_user
            references users
);

create table if not exists teacher_discipline
(
    teacher_id    bigint not null
        constraint fk_teacher_discipline_teacher_id
            references teachers,
    discipline_id bigint not null
        constraint fk_teacher_discipline_discipline_id
            references disciplines,
    constraint teacher_discipline_pkey
        primary key (teacher_id, discipline_id)
);

create table if not exists teacher_lesson
(
    teacher_id bigint not null
        constraint fk_teacher_lesson_teacher_id
            references teachers,
    lesson_id  bigint not null
        constraint fk_teacher_lesson_lesson_id
            references lessons,
    constraint teacher_lesson_pkey
        primary key (teacher_id, lesson_id)
);

create table if not exists user_role
(
    user_id bigint not null
        constraint fk_user_role_user_id
            references users,
    role_id bigint not null
        constraint fk_user_role_role_id
            references roles,
    constraint user_role_pkey
        primary key (user_id, role_id)
);

