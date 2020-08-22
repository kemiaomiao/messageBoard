DROP TABLE IF EXISTS  user;
CREATE TABLE  user   (
   id  int(11) PRIMARY KEY ,
   account  varchar(10)  ,
   password  varchar(10)  ,
   name  varchar(50)  ,
   role  tinyint(4) NOT NULL
) ;
alter table user alter column id int identity(1,1);
insert into user(account,password,name,role) value ('admin','admin','管理员',1);

DROP TABLE IF EXISTS  message;
CREATE TABLE  message   (
   id  int(11) PRIMARY KEY ,
   title  varchar(100)  ,
   authorId  int(11)  ,
   authorName  varchar(50)  ,
   content  varchar(2000)  ,
   resolve  int(4)  ,
   status  int(4)  ,
   publishDate  varchar(20)  ,
   updateDate  varchar(20)
) ;
alter table message alter column id int identity(1,1);



