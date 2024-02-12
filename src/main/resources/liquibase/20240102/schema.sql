

CREATE SCHEMA IF NOT EXISTS virtualpets;-- CHARACTER SET=utf8 COLLATE=utf8_bin;

SET search_path TO virtualpets;


CREATE TABLE chat (
                      id serial NOT NULL,
                      addressee int DEFAULT NULL,
                      sender int NOT NULL,
                      send_time timestamp with time zone NOT NULL,
                      message varchar(250) DEFAULT NULL,
                      version int NOT NULL DEFAULT 0,
                      PRIMARY KEY (id)
);


create index idx_chat_addressee on chat(addressee);

create index idx_chat_sender on chat(sender);

create index idx_chat_send_time on chat(send_time);

CREATE TABLE pet (
                     id serial NOT NULL,
                     name varchar(50) NOT NULL,
                     session_key varchar(50) DEFAULT NULL,
                     created_date timestamp with time zone NOT NULL,
                     login_date timestamp with time zone DEFAULT NULL,
                     satiety INT NOT NULL DEFAULT 0,
                     mood INT NOT NULL DEFAULT 0,
                     education INT NOT NULL DEFAULT 0,
                     drink INT NOT NULL DEFAULT 0,
                     comment varchar(50) DEFAULT NULL,
                     user_id INT NOT NULL,
                     pet_type INT NOT NULL DEFAULT 0,
                     hat_id INT DEFAULT NULL,
                     cloth_id INT DEFAULT NULL,
                     bow_id INT DEFAULT NULL,
                     experience INT NOT NULL DEFAULT 0,
                     level_id INT NOT NULL DEFAULT 1,
                     eat_count INT default 0,
                     drink_count INT default 0,
                     teach_count INT default 0,
                     build_count INT default 0,
                     hidden_objects_game_count INT default 0,
                     version INT NOT NULL DEFAULT 0,
                     PRIMARY KEY (id)
);

create unique index idx_pet_session_key on pet(session_key);

create index idx_pet_user_id on pet(user_id);

CREATE TABLE "settings" (
                     id INT NOT NULL,
                     last_process_all timestamp with time zone NOT NULL,
                     db_version varchar(10) NOT NULL,
                     version INT NOT NULL DEFAULT 0,
                     PRIMARY KEY (id)
);


CREATE TABLE "user" (
                     id serial NOT NULL,
                     login varchar(50) DEFAULT NULL,
                     name varchar(50) NOT NULL,
                     password varchar(50) NULL,
                     facebook_key varchar(50) DEFAULT NULL,
                     registration_date timestamp with time zone NOT NULL,
                     login_date timestamp with time zone DEFAULT NULL,
                     active_date timestamp with time zone DEFAULT NULL,
                     sex INT DEFAULT NULL,
                     birthdate timestamp with time zone DEFAULT NULL,
                     comment varchar(50) DEFAULT NULL,
                     country varchar(50) DEFAULT NULL,
                     city varchar(50) DEFAULT NULL,
                     role INT NOT NULL DEFAULT '0',
                     email varchar(100) DEFAULT NULL,
                     photo bytea DEFAULT NULL,
                     recover_password_key varchar(50) DEFAULT NULL,
                     recover_password_valid timestamp with time zone DEFAULT NULL,
                     unid varchar(1000) DEFAULT NULL,
                     version INT NOT NULL DEFAULT 0,
                     PRIMARY KEY (id)
);

create unique index idx_user_login on "user"(login);

INSERT INTO "user"(login, name,password,registration_date,role)
values('admin', 'admin','202CB962AC59075B964B07152D234B70',now(),4);


INSERT INTO pet(name,created_date,login_date,user_id,pet_type)
select 'admin',now(),now(),t_user.id,0
from "user" t_user where t_user.name='admin';

INSERT INTO "settings"(id,last_process_all,db_version) values(1,now(),'0.20.1');



CREATE TABLE pet_cloth (
                           id serial NOT NULL,
                           pet_id INT NOT NULL,
                           cloth_id INT NOT NULL,
                           version INT NOT NULL DEFAULT 0,
                           PRIMARY KEY(id)
);

create unique index idx_cloth_pet_id_food_type on pet_cloth(pet_id, cloth_id);

CREATE TABLE cloth (
                       id INT NOT NULL,
                       cloth_type INT NOT NULL,
                       version INT NOT NULL DEFAULT 0,
                       PRIMARY KEY(id)
);

INSERT INTO cloth(id, cloth_type)
VALUES(1, 0);
INSERT INTO cloth(id, cloth_type)
VALUES(2, 0);
INSERT INTO cloth(id, cloth_type)
VALUES(3, 0);
INSERT INTO cloth(id, cloth_type)
VALUES(4, 1);
INSERT INTO cloth(id, cloth_type)
VALUES(5, 1);
INSERT INTO cloth(id, cloth_type)
VALUES(6, 1);
INSERT INTO cloth(id, cloth_type)
VALUES(7, 2);
INSERT INTO cloth(id, cloth_type)
VALUES(8, 2);
INSERT INTO cloth(id, cloth_type)
VALUES(9, 2);


 create table UserConnection (userId varchar(255) not null,
     providerId varchar(255) not null,
     providerUserId varchar(255),
     rank int not null,
     displayName varchar(255),
     profileUrl varchar(512),
     imageUrl varchar(512),
     accessToken varchar(255) not null,
     secret varchar(255),
     refreshToken varchar(255),
     expireTime bigint,
     primary key (userId, providerId, providerUserId));

create unique index UserConnectionRank on UserConnection(userId, providerId, rank);



alter table chat add constraint fk_chat_addressee
    foreign key (addressee) references "user"(id)
    ON DELETE NO ACTION ON UPDATE NO ACTION;

alter table chat add constraint fk_chat_sender
    foreign key (sender) references "user"(id)
    ON DELETE NO ACTION ON UPDATE NO ACTION;


alter table pet add constraint fk_pet_user_id
    foreign key (user_id) references "user"(id)
    ON DELETE NO ACTION ON UPDATE NO ACTION;

alter table pet add constraint fk_pet_hat_id
    foreign key (hat_id) references cloth(id)
        ON DELETE NO ACTION ON UPDATE NO ACTION;

alter table pet add constraint fk_pet_cloth_id
    foreign key (cloth_id) references cloth(id)
        ON DELETE NO ACTION ON UPDATE NO ACTION;

alter table pet add constraint fk_pet_bow_id
    foreign key (bow_id) references cloth(id)
        ON DELETE NO ACTION ON UPDATE NO ACTION;


alter table pet_cloth add constraint fk_pet_cloth_pet_id
    foreign key (pet_id) references pet(id)
        ON DELETE NO ACTION ON UPDATE NO ACTION;

alter table pet_cloth add constraint fk_pet_cloth_cloth_id
    foreign key (cloth_id) references cloth(id)
        ON DELETE NO ACTION ON UPDATE NO ACTION;



--
alter table "user" add column vkontakte_key varchar(50) DEFAULT NULL;

create unique index idx_user_facebook_key on "user"(facebook_key);

create unique index idx_user_vkontakte_key on "user"(vkontakte_key);

-- 2013-11-09
alter table "user" add column twitter_key varchar(50) DEFAULT NULL;

create unique index idx_user_twitter_key on "user"(twitter_key);


-- 2014-01-17 для версии 0.20


create table refrigerator(
                             id INT NOT NULL,
                             max_food_type INT NOT NULL,
                             version INT NOT NULL DEFAULT 0,
                             PRIMARY KEY (id)
);

insert into refrigerator(id, max_food_type) values(1, 2);
insert into refrigerator(id, max_food_type) values(2, 5);
insert into refrigerator(id, max_food_type) values(3, 8);
insert into refrigerator(id, max_food_type) values(4, 11);
insert into refrigerator(id, max_food_type) values(5, 14);
insert into refrigerator(id, max_food_type) values(6, 17);

-- 2014-01-18
create table building_material(
                                  id INT NOT NULL,
                                  code varchar(50),
                                  version INT NOT NULL DEFAULT 0,
                                  PRIMARY KEY (id)
);

create table pet_building_material(
                                      id serial NOT NULL,
                                      pet_id INT NOT NULL,
                                      building_material_id INT NOT NULL,
                                      building_material_count INT NOT NULL,
                                      version INT NOT NULL DEFAULT 0,
                                      PRIMARY KEY (id)
);

create unique index idx_pet_building_material_unique on pet_building_material(pet_id, building_material_id);

create index idx_pet_building_materials_pet_id on pet_building_material(pet_id);


alter table pet_building_material add constraint fk_pet_building_material_pet_id
    foreign key (pet_id) references pet(id)
        ON DELETE NO ACTION ON UPDATE NO ACTION;

alter table pet_building_material add constraint fk_pet_building_material_building_material_id
    foreign key (building_material_id) references building_material(id)
        ON DELETE NO ACTION ON UPDATE NO ACTION;


insert into building_material(id, code) values(1, 'TIMBER');
insert into building_material(id, code) values(2, 'BOARD');
insert into building_material(id, code) values(3, 'STONE');
insert into building_material(id, code) values(4, 'CHIP');
insert into building_material(id, code) values(5, 'WIRE');
insert into building_material(id, code) values(6, 'IRON');
insert into building_material(id, code) values(7, 'OIL');
insert into building_material(id, code) values(8, 'BLUE_CRYSTAL');
insert into building_material(id, code) values(9, 'RUBBER');

-- 2014-01-19

create table pet_food(
                         id serial NOT NULL,
                         pet_id INT NOT NULL,
                         food_id INT NOT NULL,
                         food_count INT NOT NULL,
                         version INT NOT NULL DEFAULT 0 ,
                         PRIMARY KEY(id)
);

create table food(
                     id INT NOT NULL,
                     code varchar(50) NOT NULL,
                     version INT NOT NULL DEFAULT 0,
                     PRIMARY KEY(id)
);

insert into food(id, code) values(1, 'CARROT');
insert into food(id, code) values(2, 'DRY_FOOD');
insert into food(id, code) values(3, 'FISH');
insert into food(id, code) values(4, 'ICE_CREAM');
insert into food(id, code) values(5, 'APPLE');
insert into food(id, code) values(6, 'CABBAGE');
insert into food(id, code) values(7, 'CHOCOLATE');
insert into food(id, code) values(8, 'FRENCH_FRIES');
insert into food(id, code) values(9, 'JAPANESE_ROLLS');
insert into food(id, code) values(10, 'PIE');
insert into food(id, code) values(11, 'POTATOES');
insert into food(id, code) values(12, 'SANDWICH');
insert into food(id, code) values(13, 'BANANA');
insert into food(id, code) values(14, 'WATERMELON');

create unique index idx_pet_food_unique on pet_food(pet_id, food_id);

alter table pet_food add constraint fk_pet_food_pet_id
    foreign key (pet_id) references pet(id)
        ON DELETE NO ACTION ON UPDATE NO ACTION;

alter table pet_food add constraint fk_pet_food_food_id
    foreign key (food_id) references food(id)
        ON DELETE NO ACTION ON UPDATE NO ACTION;

create table bookcase(
                         id INT NOT NULL,
                         max_book_type INT NOT NULL,
                         version INT NOT NULL default 0,
                         PRIMARY KEY(id)
);

insert into bookcase(id, max_book_type) values(1, 2);
insert into bookcase(id, max_book_type) values(2, 5);
insert into bookcase(id, max_book_type) values(3, 8);
insert into bookcase(id, max_book_type) values(4, 11);
insert into bookcase(id, max_book_type) values(5, 14);
insert into bookcase(id, max_book_type) values(6, 17);

create table drink(
                      id INT NOT NULL,
                      CODE varchar(50) NOT NULL,
                      version INT NOT NULL default 0,
                      PRIMARY KEY(id)
);

insert into drink(id, code) values(1, 'WATER');
insert into drink(id, code) values(2, 'MILK');
insert into drink(id, code) values(3, 'BOTTLE');
insert into drink(id, code) values(4, 'TEA');
insert into drink(id, code) values(5, 'COFFEE');
insert into drink(id, code) values(6, 'ORANGE_JUICE');




create table machine_with_drinks(
                                    id INT NOT NULL,
                                    max_drink_type INT NOT NULL,
                                    version INT NOT NULL default 0,
                                    PRIMARY KEY(id)
);

create table machine_with_drinks_cost(
                                         id serial NOT NULL,
                                         machine_with_drinks_id INT NOT NULL,
                                         building_material_id INT NOT NULL,
                                         cost INT NOT NULL,
                                         version INT NOT NULL default 0,
                                         PRIMARY KEY(id)
);

alter table machine_with_drinks_cost add constraint fk_machine_with_drinks_cost_machine_with_drinks_id
    foreign key(machine_with_drinks_id) references machine_with_drinks(id)
        on update no action on delete no action;

alter table machine_with_drinks_cost add constraint fk_machine_with_drinks_cost_building_material_id
    foreign key(building_material_id) references building_material(id)
        on update no action on delete no action;

insert into machine_with_drinks(id, max_drink_type) values(1, 1);
insert into machine_with_drinks(id, max_drink_type) values(2, 2);
insert into machine_with_drinks(id, max_drink_type) values(3, 3);
insert into machine_with_drinks(id, max_drink_type) values(4, 4);
insert into machine_with_drinks(id, max_drink_type) values(5, 5);
insert into machine_with_drinks(id, max_drink_type) values(6, 6);



insert into machine_with_drinks_cost(machine_with_drinks_id, building_material_id, cost) values(1, 1, 1);
insert into machine_with_drinks_cost(machine_with_drinks_id, building_material_id, cost) values(1, 3, 1);

insert into machine_with_drinks_cost(machine_with_drinks_id, building_material_id, cost) values (2, 2, 2);
insert into machine_with_drinks_cost(machine_with_drinks_id, building_material_id, cost) values (2, 5, 2);

insert into machine_with_drinks_cost(machine_with_drinks_id, building_material_id, cost) values (3, 2, 3);
insert into machine_with_drinks_cost(machine_with_drinks_id, building_material_id, cost) values (3, 4, 1);

insert into machine_with_drinks_cost(machine_with_drinks_id, building_material_id, cost) values (4, 2, 4);
insert into machine_with_drinks_cost(machine_with_drinks_id, building_material_id, cost) values (4, 3, 1);

insert into machine_with_drinks_cost(machine_with_drinks_id, building_material_id, cost) values (5, 2, 5);
insert into machine_with_drinks_cost(machine_with_drinks_id, building_material_id, cost) values (5, 3, 5);
insert into machine_with_drinks_cost(machine_with_drinks_id, building_material_id, cost) values (5, 4, 1);

insert into machine_with_drinks_cost(machine_with_drinks_id, building_material_id, cost) values (6, 2, 6);
insert into machine_with_drinks_cost(machine_with_drinks_id, building_material_id, cost) values (6, 3, 6);





-- 2014-01-24
create table room(
                     pet_id INT NOT NULL,
                     refrigerator_id INT NULL,
                     refrigerator_x INT NULL,
                     refrigerator_y INT NULL,
                     bookcase_id int NULL,
                     bookcase_x INT NULL,
                     bookcase_y INT NULL,
                     box_newbie1 boolean NOT NULL,
                     box_newbie2 boolean NOT NULL,
                     box_newbie3 boolean NOT NULL,
                     machine_with_drinks_id INT,
                     machine_with_drinks_x INT,
                     machine_with_drinks_y INT,
                     journal_on_floor boolean not null default true,
                     version INT NOT NULL DEFAULT 0,
                     PRIMARY KEY(pet_id)
);

alter table room add constraint fk_room_machine_with_drinks_id foreign key (machine_with_drinks_id)
    references machine_with_drinks(id) on update no action on delete no action;

alter table room add constraint fk_room_bookcase_id
    foreign key (bookcase_id) references bookcase(id)
        on update no action on delete no action;

alter table room add constraint fk_room_refrigerator_id
    foreign key (refrigerator_id) references refrigerator(id)
        ON DELETE NO ACTION ON UPDATE NO ACTION;

-- 2014-05-06
create table refrigerator_cost(
                                  id serial NOT NULL,
                                  refrigerator_id INT NOT NULL,
                                  building_material_id INT NOT NULL,
                                  cost INT NOT NULL,
                                  version INT NOT NULL DEFAULT 0,
                                  PRIMARY KEY (id)
);

alter table refrigerator_cost add constraint fk_refrigerator_cost_refrigerator_id
    foreign key (refrigerator_id) references refrigerator(id)
        ON DELETE NO ACTION ON UPDATE NO ACTION;

alter table refrigerator_cost add constraint fk_refrigerator_cost_building_material_id
    foreign key (building_material_id) references building_material(id)
        ON DELETE NO ACTION ON UPDATE NO ACTION;

insert into refrigerator_cost(refrigerator_id, building_material_id, cost)
values(1,1, 2);

create unique index idx_refrigerator_cost_unique on refrigerator_cost(refrigerator_id, building_material_id);

-- 2014-05-13
insert into refrigerator_cost(refrigerator_id, building_material_id, cost)
values(2,1, 5);

-- 2014-05-14
insert into refrigerator_cost(refrigerator_id, building_material_id, cost)
values(2,3, 5);

insert into refrigerator_cost(refrigerator_id, building_material_id, cost)
values(1,3, 2);

insert into refrigerator_cost(refrigerator_id, building_material_id, cost)
values(3,6, 3); -- 3 железа

insert into refrigerator_cost(refrigerator_id, building_material_id, cost)
values(3,4, 3); -- 3 микросхемы

insert into refrigerator_cost(refrigerator_id, building_material_id, cost)
values(4,6, 4); -- 4 железа

insert into refrigerator_cost(refrigerator_id, building_material_id, cost)
values(4,4, 4); -- 4 микросхемы

insert into refrigerator_cost(refrigerator_id, building_material_id, cost)
values(5,6, 8); -- 8 железа

insert into refrigerator_cost(refrigerator_id, building_material_id, cost)
values(5,4, 5); -- 5 микросхемы

insert into refrigerator_cost(refrigerator_id, building_material_id, cost)
values(6,6, 10); -- 10 железа

insert into refrigerator_cost(refrigerator_id, building_material_id, cost)
values(6,4, 6); -- 6 микросхемы




create table bookcase_cost(
                              id serial NOT NULL,
                              bookcase_id INT NOT NULL,
                              building_material_id INT NOT NULL,
                              cost INT NOT NULL,
                              version INT NOT NULL default 0,
                              PRIMARY KEY (id)
);

alter table bookcase_cost add constraint fk_bookcase_cost_bookcase_id
    foreign key (bookcase_id) references bookcase(id)
        on update no action on delete no action;

alter table bookcase_cost add constraint fk_bookcase_cost_building_material_id
    foreign key (building_material_id) references building_material(id)
        on update no action on delete no action;

create unique index idx_bookcase_cost_unique on bookcase_cost(bookcase_id, building_material_id);


insert into bookcase_cost(bookcase_id, building_material_id, cost) values(1, 1, 1);



-- 2014-10-17


insert into bookcase_cost(bookcase_id, building_material_id, cost)
values(2, 1, 2); -- 2 дерева

insert into bookcase_cost(bookcase_id, building_material_id, cost)
values(3, 2, 3); -- 3 доски

insert into bookcase_cost(bookcase_id, building_material_id, cost)
values(4, 2, 4);  -- 4 доски

insert into bookcase_cost(bookcase_id, building_material_id, cost)
values(5, 1, 5); -- 5 досок

insert into bookcase_cost(bookcase_id, building_material_id, cost)
values(6, 1, 6);  -- 6 досок


create table book(
                     id INT,
                     version INT NOT NULL DEFAULT 0,
                     PRIMARY KEY(id)
);

CREATE TABLE pet_book (
                          id serial NOT NULL,
                          pet_id INT NOT NULL,
                          book_id INT NOT NULL,
                          version INT NOT NULL DEFAULT 0,
                          PRIMARY KEY(id)
);

create unique index idx_pet_book_pet_id_book_id on pet_book(pet_id, book_id);

alter table pet_book add constraint fk_pet_book_pet_id foreign key (pet_id)
    references pet(id) on update no action on delete no action;

alter table pet_book add constraint fk_pet_book_book_id foreign key (book_id)
    references book(id) on update no action on delete no action;

insert into book (id) values(1);
insert into book (id) values(2);
insert into book (id) values(3);
insert into book (id) values(4);
insert into book (id) values(5);
insert into book (id) values(6);
insert into book (id) values(7);
insert into book (id) values(8);
insert into book (id) values(9);
insert into book (id) values(10);
insert into book (id) values(11);
insert into book (id) values(12);
insert into book (id) values(13);
insert into book (id) values(14);
insert into book (id) values(15);
insert into book (id) values(16);
insert into book (id) values(17);
insert into book (id) values(18);

insert into pet_book(pet_id, book_id)
select p.id, 1
from pet p
where not exists(select * from pet_book pb where pb.pet_id = p.id and pb.book_id = 1);









create table pet_drink(
                          id serial NOT NULL,
                          pet_id INT NOT NULL,
                          drink_id INT NOT NULL,
                          drink_count INT NOT NULL,
                          version INT NOT NULL DEFAULT 0 ,
                          PRIMARY KEY(id)
);


alter table pet_drink add constraint fk_pet_drink_drink_id foreign key (drink_id)
    references drink(id) on update no action on delete no action;

alter table pet_drink add constraint fk_pet_drink_pet_id foreign key (pet_id)
    references pet(id) on update no action on delete no action;







create table journal_entry(
                              id INT NOT NULL,
                              code character varying(50) NOT NULL,
                              version INT NOT NULL DEFAULT 0,
                              PRIMARY KEY(id)
);

create table pet_journal_entry(
                                  id serial NOT NULL,
                                  created_at timestamp with time zone NOT NULL,
                                  pet_id INT NOT NULL,
                                  journal_entry_id INT NOT NULL,
                                  version INT NOT NULL DEFAULT 0,
                                  PRIMARY KEY(id)
);

insert into journal_entry(id, code) values(1, 'WELCOME');
insert into journal_entry(id, code) values(2, 'OPEN_NEWBIE_BOXES');
insert into journal_entry(id, code) values(3, 'BUILD_MACHINE_WITH_DRINKS');
insert into journal_entry(id, code) values(4, 'DRINK_SOMETHING');
insert into journal_entry(id, code) values(5, 'BUILD_REFRIGERATOR');
insert into journal_entry(id, code) values(6, 'EAT_SOMETHING');
insert into journal_entry(id, code) values(7, 'BUILD_BOOKCASE');
insert into journal_entry(id, code) values(8, 'READ_SOMETHING');


alter table pet_journal_entry add column readed boolean NOT NULL default false;

insert into journal_entry(id, code) values(9, 'LEAVE_ROOM');



create table level(
                      id INT NOT NULL,
                      experience INT NOT NULL,
                      PRIMARY KEY(id)
);

insert into level(id, experience) values(1, 0);
insert into level(id, experience) values(2, 10);
insert into level(id, experience) values(3, 30);


-- 2015-02-11

create table achievement(
                            id INT NOT NULL,
                            code character varying(50),
                            version INT NOT NULL default 0,
                            PRIMARY KEY(id)
);

create table pet_achievement(
                                id serial NOT NULL,
                                pet_id INT NOT NULL,
                                achievement_id INT NOT NULL,
                                version INT NOT NULL default 0,
                                PRIMARY KEY(id)
);


insert into achievement(id, code) values(1, 'BUILD_1');
insert into achievement(id, code) values(2, 'FEED_1');
insert into achievement(id, code) values(3, 'FEED_10');
insert into achievement(id, code) values(4, 'FEED_100');
insert into achievement(id, code) values(5, 'DRINK_1');
insert into achievement(id, code) values(6, 'DRINK_10');
insert into achievement(id, code) values(7, 'DRINK_100');
insert into achievement(id, code) values(8, 'TEACH_1');
insert into achievement(id, code) values(9, 'TEACH_10');
insert into achievement(id, code) values(10, 'TEACH_100');
insert into achievement(id, code) values(11, 'LEAVE_ROOM');
insert into achievement(id, code) values(12, 'HIDDEN_OBJECTS_GAME_1');
insert into achievement(id, code) values(13, 'HIDDEN_OBJECTS_GAME_10');
insert into achievement(id, code) values(14, 'HIDDEN_OBJECTS_GAME_100');


-- 2015-02-12
alter table pet_achievement add column was_shown boolean default false;



-- 2015-02-18
insert into journal_entry(id, code) values(10, 'PLAY_HIDDEN_OBJECT_GAMES');

update "settings" set db_version = '0.21.0' where id = 1;
