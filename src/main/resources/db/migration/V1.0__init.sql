CREATE TABLE user (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    email varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    full_name varchar(255) NOT NULL
);

CREATE TABLE application (
    id bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_id bigint NOT NULL,
    phone_number varchar(20) NOT NULL,
    education varchar(255) NOT NULL,
    free_time varchar(1500) NOT NULL,
    agreement boolean NOT NULL,
    comment varchar(1500),
    academy_time boolean NOT NULL,
    reason varchar(1500) NOT NULL,
    technologies varchar(1500) NOT NULL,
    source varchar(255) NOT NULL,
    application_date datetime NOT NULL,
    status ENUM ('NAUJA', 'PERZIURETA', 'POTENCIALUS', 'ATMESTA', 'PRIIMTA') default 'NAUJA',
    is_new_internal_comment boolean default false,
    last_internal_comment_author varchar(255) default null,
    is_new_external_comment boolean default false,
    last_external_comment_author varchar(255) default null,
    FOREIGN KEY (user_id) REFERENCES user(id)
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);

CREATE table user_roles (
    id bigint not null primary key AUTO_INCREMENT,
    roles ENUM ('ADMIN', 'USER') default 'USER',
    user_id bigint NOT NULL,    FOREIGN KEY (user_id) REFERENCES user(id)
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);

create table application_comments (
    id bigint not null primary key AUTO_INCREMENT,
    user_id bigint not null,
    application_id bigint not null,
    comment varchar(1500) not null,
    comment_date datetime not null,
    date_modified datetime,
    is_visible_to_student boolean default false,
    attachment_name varchar(255) default null,
    attachment blob default null,
    foreign key (user_id) references user(id)
        on update no action
        on delete cascade,
    foreign key (application_id) references application(id)
        on update no action
        on delete cascade
);

INSERT INTO user values (1, 'admin@mail.com', '$2a$10$KYQpdsE94e8xY76SVz8aauXYj7RSi3vKQmZXRrMNpZSiXx9wt8QJm', 'Monika Kopaitė');
INSERT INTO user values (2, 'admin@admin.com', '$2a$10$.RaqqkjTdQA985oETEdqWujLtp/ipaXBKWZK1XcGLNU2ExgAC1dcS', 'Linas Tomkus');
INSERT INTO user values (3, 'urte.ruk@mail.com', '$2a$10$.RaqqkjTdQA985oETEdqWujLtp/ipaXBKWZK1XcGLNU2ExgAC1dcS', 'Urtė Rukaitė');
INSERT INTO user values (4, 'arturas.lin@mail.com', '$2a$10$.RaqqkjTdQA985oETEdqWujLtp/ipaXBKWZK1XcGLNU2ExgAC1dcS', 'Artūras Linaukas');
INSERT INTO user values (5, 'simonasjankevicius@mail.lt', '$2a$10$.RaqqkjTdQA985oETEdqWujLtp/ipaXBKWZK1XcGLNU2ExgAC1dcS', 'Simonas Jankevičius');

INSERT INTO application values (1, 3, '+37067712456', 'VU', 'Mėgstu skaityti knygas, keliauti', true,
    '', true, 'Noriu daug išmokti', 'Mėgstu įvairias web technologijas', 'Iš facebook',
    '2020-03-13 10:34:09', 'POTENCIALUS', false, null, false, null);
INSERT INTO application values (2, 4, '+37060982454', 'Kauno technologijos universitetas', 'Mėgstu žaisti kompiuteriu, važinėtis dviračiu', false,
    'nežinau kur kreiptis', true, 'Noriu mokytis iš profesionalų', 'Man labai patinka big data, mokiausi principų savarankiškai', 'Iš draugų',
    '2020-03-21 11:34:09', 'PERZIURETA', false, null, false, null);
INSERT INTO application values (3, 5, '+37060482000', 'VGTU', 'Programuoju', true, '', true,
    'Noriu išmokti naujų dalykų', 'Šiaip daug mokausi savarankiškai, daugiausiai įvairios web technologijos', 'Iš draugo',
    '2020-03-26 12:34:09', 'NAUJA', false, null, false, null);

insert into user_roles values (1, 'ADMIN', 1);
insert into user_roles values (2, 'ADMIN', 2);
insert into user_roles values (3, 'USER', 3);
insert into user_roles values (4, 'USER', 4);
insert into user_roles values (5, 'USER', 5);

insert into application_comments values (1, 1, 1, 'Visai neblogai atrodo',
    '2020-03-22 11:34:09', null, false, null, null );
insert into application_comments values (2, 2, 1, 'Galėjo apie technologjas plačiau',
    '2020-03-23 15:34:09', null, false, null, null );
insert into application_comments values (3, 1, 2, 'Reikėtų susisiekti dėl tos sutarties, žmogus nežino tvarkos matyt',
    '2020-03-23 16:34:09', null, false, null, null );
insert into application_comments values (4, 2, 2, 'Šiaip atrodo neblogai',
    '2020-03-24 11:34:09', null, false, null, null );
insert into application_comments values (5, 1, 2, 'Galbūt galėtumėte plačiau pakomentuoti, kodėl negalite pasirašyti trišalės sutarties?',
    '2020-03-24 14:34:09', null, true, null, null );
insert into application_comments values (6, 4, 2, 'Nežinau, į ką universitete kreiptis šiuo klausimu',
    '2020-03-24 18:34:09', null, true, null, null );
insert into application_comments values (7, 1, 2, 'Paprastai studijų skyrius turetų galįti padėti tokiu klausimu',
    '2020-03-25 10:34:09', null, true, null, null );
