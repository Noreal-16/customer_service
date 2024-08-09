create table if not exists person (
                        id      SERIAL primary key,
                        name    varchar(50) not null ,
                        gender  varchar(10) not null,
                        age     int not null ,
                        identification varchar(13) unique not null,
                        direction varchar(50) not null,
                        phone varchar(10) not null
);

create table if not exists customer (
                          id      SERIAL primary key,
                          password    varchar(50) not null ,
                          status  boolean default true,
                          person_id     int not null
);