CREATE TABLE user (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    email varchar(255) NOT NULL,
    password varchar(255) NOT NULL
);

CREATE TABLE application (
    id bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_id bigint NOT NULL,
    full_name varchar(255) NOT NULL,
    phone_number varchar(20) NOT NULL,
    education varchar(255) NOT NULL,
    free_time varchar(1500) NOT NULL,
    agreement boolean NOT NULL,
    comment varchar(1500) NOT NULL,
    academy_time boolean NOT NULL,
    reason varchar(1500) NOT NULL,
    technologies varchar(1500) NOT NULL,
    source varchar(255) NOT NULL,
    application_date date NOT NULL,
    view_status varchar(30) default 'nauja',
    acceptance_status varchar(30) default null,
    FOREIGN KEY (user_id) REFERENCES user(id)
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);

create table roles(
    id bigint not null primary key AUTO_INCREMENT,
    role varchar(20)
);

CREATE table user_roles (
    id bigint not null primary key AUTO_INCREMENT,
    role_id bigint NOT NULL,
    user_id bigint NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id)
        ON UPDATE NO ACTION
        ON DELETE CASCADE,
    FOREIGN KEY (role_id) references roles(id)
        on update no action
        on delete cascade
);

create table admin_comments (
    id bigint not null primary key AUTO_INCREMENT,
    admin_id bigint not null,
    application_id bigint not null,
    comment varchar(1500) not null,
    comment_date date not null,
    foreign key (admin_id) references user(id)
        on update no action
        on delete cascade,
    foreign key (application_id) references application(id)
        on update no action
        on delete cascade
);

INSERT INTO user values (1, 'aaaa@testemail.com', 'aaaaaaaaa');
INSERT INTO user values (2, 'test@testemail.com', 'password');
INSERT INTO application values (1, 1, 'name', 'phonenumber', 'education', 'freetime', true,
    'comment', true, 'reason', 'tech', 'sauce', TO_DATE('2020-03-13', 'YYYY-MM-DD'), 'nauja', null);
INSERT INTO roles values (1, 'USER');
INSERT INTO roles values (2, 'ADMIN');
insert into user_roles values (1, 1, 1);
insert into user_roles values (2, 2, 2);
insert into user_roles values (3, 2, 1);
insert into admin_comments values (1, 2, 1, 'test comment aaaaaaaaaaaaaaaa', TO_DATE('2020-03-20', 'YYYY-MM-DD'));