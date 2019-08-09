insert into author(bio,idPerson) values('A happy man is he',2);
insert into author(bio,idPerson) values('A happy wife is she',1);
insert into author(bio,idPerson) values('Thinker of thoughts',3);
insert into author(bio,idPerson) values('Author of childrens books',4);
insert into author(bio,idPerson) values('Known for her clever style',5);

insert into bookauthor(bookId,authorId) values(1,2);
insert into bookauthor(bookId,authorId) values(2,2);
insert into bookauthor(bookId,authorId) values(3,2);
insert into bookauthor(bookId,authorId) values(4,2);
insert into bookauthor(bookId,authorId) values(5,2);
insert into bookauthor(bookId,authorId) values(6,2);


insert into bookCopy(copyNum,bookId,isAvailable) values(1,1,1);
insert into bookCopy(copyNum,bookId,isAvailable) values(2,1,1);
insert into bookCopy(copyNum,bookId,isAvailable) values(3,1,1);

insert into bookCopy(copyNum,bookId,isAvailable) values(1,2,1);
insert into bookCopy(copyNum,bookId,isAvailable) values(2,2,1);
insert into bookCopy(copyNum,bookId,isAvailable) values(3,2,1);

insert into bookCopy(copyNum,bookId,isAvailable) values(1,3,1);
insert into bookCopy(copyNum,bookId,isAvailable) values(2,3,1);
insert into bookCopy(copyNum,bookId,isAvailable) values(3,3,1);

insert into bookCopy(copyNum,bookId,isAvailable) values(1,4,1);
insert into bookCopy(copyNum,bookId,isAvailable) values(2,4,1);
insert into bookCopy(copyNum,bookId,isAvailable) values(3,4,1);

insert into bookCopy(copyNum,bookId,isAvailable) values(1,5,1);
insert into bookCopy(copyNum,bookId,isAvailable) values(2,5,1);
insert into bookCopy(copyNum,bookId,isAvailable) values(3,5,1);

insert into user(userId,fullname,password) values("101","Peter","101");
insert into user(userId,fullname,password) values("102","Marc","102");
insert into user(userId,fullname,password) values("103","Joseph","103");


insert into permission(userId,permission) values("101","librarian");
insert into permission(userId,permission) values("102","admin");
insert into permission(userId,permission) values("103","both");


