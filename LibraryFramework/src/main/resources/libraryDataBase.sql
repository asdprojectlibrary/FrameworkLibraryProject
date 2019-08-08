
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address` (
  `idAddress` int(11) NOT NULL,
  `city` varchar(100) default NULL,
  `state` varchar(100) default NULL,
  `street` varchar(100) default NULL,
  `zipCode` varchar(100) default NULL,
  PRIMARY KEY  (`idAddress`)
) TYPE=InnoDB;




DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `bookId` int(11) NOT NULL,
  `isbn` varchar(100) NOT NULL,
  `title` varchar(200) NOT NULL,
  `maxCheckoutLength` int(11) default NULL,
  PRIMARY KEY  (`bookId`)
) TYPE=InnoDB;





DROP TABLE IF EXISTS `bookcopy`;
CREATE TABLE `bookcopy` (
  `id` int(11) NOT NULL,
  `copyNum` int(11) NOT NULL,
  `isAvailable` tinyint(1) default NULL,
  `bookId` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_bookCopy_bookId` (`bookId`),
  CONSTRAINT `FK_bookCopy_bookId` FOREIGN KEY (`bookId`) REFERENCES `book` (`bookId`)
) TYPE=InnoDB;







DROP TABLE IF EXISTS `person`;
CREATE TABLE `person` (
  `idPerson` int(11) NOT NULL,
  `firstName` varchar(100) default NULL,
  `lastName` varchar(100) default NULL,
  `telephone` varchar(100) default NULL,
  `idAddress` int(11) default NULL,
  PRIMARY KEY  (`idPerson`),
  KEY `FK_person_address` (`idAddress`),
  CONSTRAINT `FK_person_address` FOREIGN KEY (`idAddress`) REFERENCES `address` (`idAddress`)
) TYPE=InnoDB;


DROP TABLE IF EXISTS `author`;
CREATE TABLE `author` (
  `authorId` int(11) NOT NULL,
  `bio` varchar(300) default NULL,
  `idPerson` int(11) NOT NULL,
  PRIMARY KEY  (`authorId`),
  KEY `FK_author_idPerson` (`idPerson`),
  CONSTRAINT `FK_author_idPerson` FOREIGN KEY (`idPerson`) REFERENCES `person` (`idPerson`)
) TYPE=InnoDB;


DROP TABLE IF EXISTS `bookauthor`;
CREATE TABLE `bookauthor` (
  `id` int(11) NOT NULL,
  `bookId` int(11) NOT NULL,
  `authorId` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_bookAuthor_bookId` (`bookId`),
  KEY `FK_bookAuthor_authorId` (`authorId`),
  CONSTRAINT `FK_bookAuthor_bookId` FOREIGN KEY (`bookId`) REFERENCES `book` (`bookId`),
  CONSTRAINT `FK_bookAuthor_authorId` FOREIGN KEY (`authorId`) REFERENCES `author` (`authorId`)
) TYPE=InnoDB;


DROP TABLE IF EXISTS `librarymember`;
CREATE TABLE `librarymember` (
  `memberId` int(11) NOT NULL,
  `idPerson` int(11) NOT NULL,
  `idCheckoutRecord` int(11) default NULL,
  PRIMARY KEY  (`memberId`),
  KEY `FK_librarryMemmber_idPerson` (`idPerson`),
  CONSTRAINT `FK_librarryMemmber_idPerson` FOREIGN KEY (`idPerson`) REFERENCES `person` (`idPerson`)
) TYPE=InnoDB;


DROP TABLE IF EXISTS `checkoutentry`;
CREATE TABLE `checkoutentry` (
  `id` int(11) NOT NULL,
  `bookcopyId` int(11) NOT NULL,
  `memberId` int(11) NOT NULL,
  `duedate` datetime NOT NULL,
  `checkoutdate` datetime NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_checkoutentry_bookcopyId` (`bookcopyId`),
  KEY `FK_checkoutentry_MemberId` (`memberId`),
  CONSTRAINT `FK_checkoutentry_bookcopyId` FOREIGN KEY (`bookcopyId`) REFERENCES `bookcopy` (`id`),
  CONSTRAINT `FK_checkoutentry_MemberId` FOREIGN KEY (`memberId`) REFERENCES `librarymember` (`memberId`)
) TYPE=InnoDB;



DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `userId` varchar(100) NOT NULL,
  `FullName` varchar(200) default NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `Index_unique_userId` (`userId`)
) TYPE=InnoDB;
