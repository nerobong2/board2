use board;

create table user(

userNo int not null auto_increment primary key,
userId varchar(50) not null,
userName varchar(30)not null,
userPassword varchar(50)not null,
regDate Date not null
);

create table board(
boardNo int not null auto_increment primary key,
boardSubject varchar(150) not null,
boardContent text,
regDate Date not null,
writerNo int not null
);

select * from user;
select * from board;
commit;

insert into user(userid,userName,userPassword,regDate)
values ("aaa","aaa","aaa",now());
insert into user(userid,userName,userPassword,regDate)
values ("bbb","bbb","bbb",now());
insert into user(userid,userName,userPassword,regDate)
values ("ccc","ccc","ccc",now());



insert into board(boardSubject,boardContent, regDate, writerNo)
values ("1번글 제목","1번글 내용",now(),1);
insert into board(boardSubject,boardContent, regDate, writerNo)
values ("2번글 제목","2번글 내용",now(),2);
insert into board(boardSubject,boardContent, regDate, writerNo)
values ("3번글 제목","3번글 내용",now(),3);
insert into board(boardSubject,boardContent, regDate, writerNo)
values ("4번글 제목","4번글 내용",now(),1);
insert into board(boardSubject,boardContent, regDate, writerNo)
values ("5번글 제목","5번글 내용",now(),1);
insert into board(boardSubject,boardContent, regDate, writerNo)
values ("6번글 제목","6번글 내용",now(),1);


select t1.boardNo ,t1.boardSubject ,t1.regDate, t2.userName
from board t1,user t2
where t1.writerNo = t2.userNo
order by t1.boardNo asc;

