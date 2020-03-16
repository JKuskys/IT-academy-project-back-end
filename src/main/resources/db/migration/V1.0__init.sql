CREATE TABLE user (
    id bigint PRIMARY KEY,
    email varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    is_admin boolean NOT NULL default false
);

CREATE TABLE application (
    id bigint NOT NULL PRIMARY KEY,
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

INSERT INTO user values (1, 'aaaa', 'aaaaaaaaa', true);
INSERT INTO application values (1, 1, 'name', 'phonenumber', 'education', 'freetime', true,
    'comment', true, 'reason', 'tech', 'sauce', TO_DATE('2020-03-13', 'YYYY-MM-DD'));