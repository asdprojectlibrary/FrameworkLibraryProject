package dao;

import dao.TestData;
import dao.rdb.command.AuthorSaveCommand;
import dao.rdb.command.Command;
import dao.rdb.command.MemberSaveCommand;
import dao.rdb.dataaccessadapter.DBAdapter;
import model.Author;
import model.Member;
import model.Person;
import service.AuthorService;
import service.BookService;
import service.MemberService;
import config.DataLoader;
import service.UserService;

import java.util.List;
import java.util.Stack;

public class DataLoaderImplDB extends DataLoader {


    @Override
    public void loadBooks() {
        if(new UserService().getAll().isEmpty()){
           new BookService().save(TestData.allBooks);
        }

    }

    @Override
    public void loadCopies() {
        if(new UserService().getAll().isEmpty()){
            BookService bookService = new BookService();

            bookService.addCopies(TestData.allBooks.get(0).getIsbn(), 2);
            bookService.addCopies(TestData.allBooks.get(1).getIsbn(), 1);
            bookService.addCopies(TestData.allBooks.get(3).getIsbn(), 1);
            bookService.addCopies(TestData.allBooks.get(2).getIsbn(), 4);
            bookService.addCopies(TestData.allBooks.get(4).getIsbn(), 2);
        }


    }

    @Override
    public void loadAuthors() {
        if(new UserService().getAll().isEmpty()){
            new AuthorService().save(TestData.allAuthors);
        }

    }

    @Override
    public void loadMembers() {
        if(new UserService().getAll().isEmpty()){
            TestData.addMembers();
            new MemberService().save(TestData.members);
        }

    }

    @Override
    public void loadUsers() {
        if(new UserService().getAll().isEmpty()){
           new UserService().save(TestData.allUsers);
        }


    }


}
