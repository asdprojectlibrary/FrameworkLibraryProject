package config;

public class TableCreation {
    private String script="" +
            "\n" +
            "CREATE TABLE `address` (\n" +
            "  `idAddress` int(11) NOT NULL auto_increment,\n" +
            "  `city` varchar(100) default NULL,\n" +
            "  `state` varchar(100) default NULL,\n" +
            "  `street` varchar(100) default NULL,\n" +
            "  `zipCode` varchar(100) default NULL,\n" +
            "  PRIMARY KEY  (`idAddress`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=latin1;\n" +
            "\n" +
            "\n" +
            "CREATE TABLE `book` (\n" +
            "  `bookId` int(11) NOT NULL auto_increment,\n" +
            "  `isbn` varchar(100) NOT NULL,\n" +
            "  `title` varchar(200) NOT NULL,\n" +
            "  `maxCheckoutLength` int(11) default NULL,\n" +
            "  PRIMARY KEY  (`bookId`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=latin1;\n" +
            "\n" +
            "\n" +
            "CREATE TABLE `bookcopy` (\n" +
            "  `id` int(11) NOT NULL auto_increment,\n" +
            "  `copyNum` int(11) NOT NULL,\n" +
            "  `isAvailable` tinyint(1) default NULL,\n" +
            "  `bookId` int(11) NOT NULL,\n" +
            "  PRIMARY KEY  (`id`),\n" +
            "  KEY `FK_bookCopy_bookId` (`bookId`),\n" +
            "  CONSTRAINT `FK_bookCopy_bookId` FOREIGN KEY (`bookId`) REFERENCES `book` (`bookId`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=latin1;\n" +
            "\n" +
            "\n" +
            "\n" +
            "CREATE TABLE `person` (\n" +
            "  `idPerson` int(11) NOT NULL auto_increment,\n" +
            "  `firstName` varchar(100) default NULL,\n" +
            "  `lastName` varchar(100) default NULL,\n" +
            "  `telephone` varchar(100) default NULL,\n" +
            "  `idAddress` int(11) default NULL,\n" +
            "  PRIMARY KEY  (`idPerson`),\n" +
            "  KEY `FK_person_address` (`idAddress`),\n" +
            "  CONSTRAINT `FK_person_address` FOREIGN KEY (`idAddress`) REFERENCES `address` (`idAddress`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=latin1;\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "CREATE TABLE `author` (\n" +
            "  `authorId` int(11) NOT NULL auto_increment,\n" +
            "  `bio` varchar(300) default NULL,\n" +
            "  `idPerson` int(11) NOT NULL,\n" +
            "  PRIMARY KEY  (`authorId`),\n" +
            "  KEY `FK_author_idPerson` (`idPerson`),\n" +
            "  CONSTRAINT `FK_author_idPerson` FOREIGN KEY (`idPerson`) REFERENCES `person` (`idPerson`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=latin1;\n" +
            "\n" +
            "\n" +
            "CREATE TABLE `librarymember` (\n" +
            "  `memberId` int(11) NOT NULL auto_increment,\n" +
            "  `idPerson` int(11) NOT NULL,\n" +
            "  `idCheckoutRecord` int(11) default NULL,\n" +
            "  PRIMARY KEY  (`memberId`),\n" +
            "  KEY `FK_librarryMemmber_idPerson` (`idPerson`),\n" +
            "  CONSTRAINT `FK_librarryMemmber_idPerson` FOREIGN KEY (`idPerson`) REFERENCES `person` (`idPerson`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=latin1;\n" +
            "\n" +
            "\n" +
            "CREATE TABLE `bookauthor` (\n" +
            "  `id` int(11) NOT NULL auto_increment,\n" +
            "  `bookId` int(11) NOT NULL,\n" +
            "  `authorId` int(11) NOT NULL,\n" +
            "  PRIMARY KEY  (`id`),\n" +
            "  KEY `FK_bookAuthor_bookId` (`bookId`),\n" +
            "  KEY `FK_bookAuthor_authorId` (`authorId`),\n" +
            "  CONSTRAINT `FK_bookAuthor_bookId` FOREIGN KEY (`bookId`) REFERENCES `book` (`bookId`),\n" +
            "  CONSTRAINT `FK_bookAuthor_authorId` FOREIGN KEY (`authorId`) REFERENCES `author` (`authorId`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=latin1;\n" +
            "\n" +
            "\n" +
            "\n" +
            "CREATE TABLE `checkoutentry` (\n" +
            "  `id` int(11) NOT NULL auto_increment,\n" +
            "  `bookcopyId` int(11) NOT NULL,\n" +
            "  `memberId` int(11) NOT NULL,\n" +
            "  `duedate` datetime NOT NULL,\n" +
            "  `checkoutdate` datetime NOT NULL,\n" +
            "  PRIMARY KEY  (`id`),\n" +
            "  KEY `FK_checkoutentry_bookcopyId` (`bookcopyId`),\n" +
            "  KEY `FK_checkoutentry_MemberId` (`memberId`),\n" +
            "  CONSTRAINT `FK_checkoutentry_bookcopyId` FOREIGN KEY (`bookcopyId`) REFERENCES `bookcopy` (`id`),\n" +
            "  CONSTRAINT `FK_checkoutentry_MemberId` FOREIGN KEY (`memberId`) REFERENCES `librarymember` (`memberId`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=latin1;\n" +
            "\n" +
            "\n" +
            "CREATE TABLE `user` (\n" +
            "  `id` int(11) NOT NULL auto_increment,\n" +
            "  `userId` varchar(100) NOT NULL,\n" +
            "  `FullName` varchar(200) default NULL,\n" +
            "  `password` varchar(100) NOT NULL,\n" +
            "  PRIMARY KEY  (`id`),\n" +
            "  UNIQUE KEY `Index_unique_userId` (`userId`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=latin1;\n" +
            "\n" +
            "\n" +
            "CREATE TABLE `permission` (\n" +
            "  `userId` varchar(100) NOT NULL,\n" +
            "  `permission` varchar(100) NOT NULL,\n" +
            "  `id` int(11) NOT NULL auto_increment,\n" +
            "  PRIMARY KEY  (`id`),\n" +
            "  KEY `FK_permission_userId` (`userId`),\n" +
            "  CONSTRAINT `FK_permission_userId` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=latin1 ;\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n";

    public String getScript(){
        return script;
    }
}
