CREATE TABLE user (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    email varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    roles varchar(20) NOT NULL default 'USER'
);

CREATE TABLE application (
    id bigint PRIMARY KEY AUTO_INCREMENT,
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
    FOREIGN KEY (user_id) REFERENCES user(id)
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);

CREATE table user_roles (
    roles varchar(20) NOT NULL PRIMARY KEY,
    user_id bigint NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id)
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);

INSERT INTO user values (1, 'aaaa@testemail.com', 'aaaaaaaaa', 'USER');
INSERT INTO user values (2, 'test@testemail.com', 'password', 'ADMIN');
INSERT INTO application values (1, 1, 'name', 'phonenumber', 'education', 'freetime', true,
    'comment', true, 'reason', 'tech', 'sauce', TO_DATE('2020-03-13', 'YYYY-MM-DD'));
INSERT INTO user_roles values ('USER', 1);
INSERT INTO user_roles values ('ADMIN', 2);
