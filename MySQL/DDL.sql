use stu;

drop table if exists booking;
drop table if exists course;
drop table if exists re1ease;
drop table if exists reply;
drop table if exists topic;
drop table if exists reply_liker;
drop table if exists topic_liker;
drop table if exists user;
drop table if exists question;
drop table if exists answer;

create table booking (stuId varchar(20), courseId int, phone varchar(20));

create table course (id int auto_increment primary key, title varchar(50), des varchar(200),lecturer varchar(20), date varchar(50), location varchar(50), verification int, category varchar(50),phone varchar(20), trueName varchar(50));

create table re1ease (teaId varchar(20), courseId int, phone varchar(20), trueName varchar(50));

-- the field likes changed to type, added date and oldCommenter fields
create table reply (
    id int auto_increment primary key, 
    topic_id int, 
    des varchar(200), 
    type int, 
    author varchar(20),
    date varchar(20),
    oldCommenter varchar(30)
);

-- added the date field
create table topic (
    id int auto_increment primary key, 
    title varchar(50), 
    des varchar(200), 
    likes int, 
    replies int, 
    author varchar(20),
    date varchar(20)
);

create table reply_liker (reply int, liker varchar(20));

create table topic_liker (topic int, liker varchar(20));

create table user (open_id varchar(50)  , ID varchar(15) primary key, name varchar(20), identity int, password varchar(65), avatar varchar(100));
-- create table user (open_id varchar(50) primary key , ID varchar(15), name varchar(20), identity int);

create table question (description varchar (10000), id int auto_increment primary key);

create table answer (description varchar (20000), queId int primary key, isRight boolean);