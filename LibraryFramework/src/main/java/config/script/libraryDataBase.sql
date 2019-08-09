
CREATE TABLE `address` (
  `idAddress` int(11) NOT NULL auto_increment,
  `city` varchar(100) default NULL,
  `state` varchar(100) default NULL,
  `street` varchar(100) default NULL,
  `zipCode` varchar(100) default NULL,
  PRIMARY KEY  (`idAddress`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `book` (
  `bookId` int(11) NOT NULL auto_increment,
  `isbn` varchar(100) NOT NULL,
  `title` varchar(200) NOT NULL,
  `maxCheckoutLength` int(11) default NULL,
  PRIMARY KEY  (`bookId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `bookcopy` (
  `id` int(11) NOT NULL auto_increment,
  `copyNum` int(11) NOT NULL,
  `isAvailable` tinyint(1) default NULL,
  `bookId` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_bookCopy_bookId` (`bookId`),
  CONSTRAINT `FK_bookCopy_bookId` FOREIGN KEY (`bookId`) REFERENCES `book` (`bookId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



CREATE TABLE `person` (
  `idPerson` int(11) NOT NULL auto_increment,
  `firstName` varchar(100) default NULL,
  `lastName` varchar(100) default NULL,
  `telephone` varchar(100) default NULL,
  `idAddress` int(11) default NULL,
  PRIMARY KEY  (`idPerson`),
  KEY `FK_person_address` (`idAddress`),
  CONSTRAINT `FK_person_address` FOREIGN KEY (`idAddress`) REFERENCES `address` (`idAddress`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;




CREATE TABLE `author` (
  `authorId` int(11) NOT NULL auto_increment,
  `bio` varchar(300) default NULL,
  `idPerson` int(11) NOT NULL,
  PRIMARY KEY  (`authorId`),
  KEY `FK_author_idPerson` (`idPerson`),
  CONSTRAINT `FK_author_idPerson` FOREIGN KEY (`idPerson`) REFERENCES `person` (`idPerson`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `librarymember` (
  `memberId` int(11) NOT NULL auto_increment,
  `idPerson` int(11) NOT NULL,
  `idCheckoutRecord` int(11) default NULL,
  PRIMARY KEY  (`memberId`),
  KEY `FK_librarryMemmber_idPerson` (`idPerson`),
  CONSTRAINT `FK_librarryMemmber_idPerson` FOREIGN KEY (`idPerson`) REFERENCES `person` (`idPerson`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `bookauthor` (
  `id` int(11) NOT NULL auto_increment,
  `bookId` int(11) NOT NULL,
  `authorId` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_bookAuthor_bookId` (`bookId`),
  KEY `FK_bookAuthor_authorId` (`authorId`),
  CONSTRAINT `FK_bookAuthor_bookId` FOREIGN KEY (`bookId`) REFERENCES `book` (`bookId`),
  CONSTRAINT `FK_bookAuthor_authorId` FOREIGN KEY (`authorId`) REFERENCES `author` (`authorId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



CREATE TABLE `checkoutentry` (
  `id` int(11) NOT NULL auto_increment,
  `bookcopyId` int(11) NOT NULL,
  `memberId` int(11) NOT NULL,
  `duedate` datetime NOT NULL,
  `checkoutdate` datetime NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_checkoutentry_bookcopyId` (`bookcopyId`),
  KEY `FK_checkoutentry_MemberId` (`memberId`),
  CONSTRAINT `FK_checkoutentry_bookcopyId` FOREIGN KEY (`bookcopyId`) REFERENCES `bookcopy` (`id`),
  CONSTRAINT `FK_checkoutentry_MemberId` FOREIGN KEY (`memberId`) REFERENCES `librarymember` (`memberId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `user` (
  `id` int(11) NOT NULL auto_increment,
  `userId` varchar(100) NOT NULL,
  `FullName` varchar(200) default NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `Index_unique_userId` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `permission` (
  `userId` varchar(100) NOT NULL,
  `permission` varchar(100) NOT NULL,
  `id` int(11) NOT NULL auto_increment,
  PRIMARY KEY  (`id`),
  KEY `FK_permission_userId` (`userId`),
  CONSTRAINT `FK_permission_userId` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='InnoDB free: 3072 kB; (`userId`) REFER `library/user`(`id`)';




