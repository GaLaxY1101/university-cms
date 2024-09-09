create table if not exists disciplines
(
    id          bigint       not null
        constraint disciplines_pkey
            primary key,
    description text         not null,
    name        varchar(255) not null
);

create index if not exists idx_disciplines_name
    on disciplines (name);

create table if not exists lesson_types
(
    id   bigint       not null
        constraint lesson_types_pkey
            primary key,
    name varchar(255) not null
);

create table if not exists lessons
(
    id               bigint  not null
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
    id   bigint not null
        constraint roles_pkey
            primary key,
    name varchar(255)
        constraint uk_roles_name
            unique
);

create table if not exists specialities
(
    id          bigint not null
        constraint specialities_pkey
            primary key,
    code        integer
        constraint uk_specialities_code
            unique,
    description text,
    name        varchar(255)
        constraint uk_specialities_name
            unique
);

create table if not exists groups
(
    id            bigint       not null
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

create index if not exists idx_groups_name
    on groups (name);

create index if not exists idx_specialities_name
    on specialities (name);

create index if not exists idx_specialities_code
    on specialities (code);

create table if not exists users
(
    id            bigint       not null
        constraint users_pkey
            primary key,
    date_of_birth date         not null,
    email         varchar(255)
        constraint uk_users_email
            unique,
    first_name    varchar(255) not null,
    last_name     varchar(255) not null,
    password      varchar(255),
    phone_number  varchar(255)
        constraint uk_users_phonenumber
            unique
);

create table if not exists admins
(
    id      integer not null
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
    id       bigint not null
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
    id      bigint not null
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

create index if not exists idx_users_email
    on users (email);

create index if not exists idx_users_phonenumber
    on users (phone_number);





create sequence admin_sequence;

create sequence discipline_sequence;


create sequence group_sequence;


create sequence lesson_sequence;


create sequence lesson_type_sequence;


create sequence role_sequence;


create sequence speciality_sequence;


create sequence student_sequence;


create sequence teacher_sequence;


create sequence user_sequence;



