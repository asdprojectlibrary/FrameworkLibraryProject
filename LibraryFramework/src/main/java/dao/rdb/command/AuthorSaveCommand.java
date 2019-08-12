package dao.rdb.command;


import dao.rdb.JDBCFacade.JDBCManager;
import model.Address;
import model.Author;
import model.Member;
import model.Person;

public class AuthorSaveCommand implements Command {
    JDBCManager jdbcManager = JDBCManager.getInstance();

    private Address address;
    private Person person;
    private Author author;

    public AuthorSaveCommand(Address address) {
        this.address = address;
    }

    public AuthorSaveCommand(Person person) {
        this.person = person;
    }

    public AuthorSaveCommand(Author author) {
        this.author = author;
    }

    @Override
    public boolean execute() {
        boolean test1 = true;
        boolean test2 = true;
        boolean test3 = true;


        if (address != null) {
            test1 = saveAddress();
        } else if (person != null) {
            test2 = savePerson();
        } else if (author != null) {
            test3 = saveAuthor();
        }

        if (test1 == false || test2 == false || test3 == false)
            return false;
        else
            return true;
    }

    public boolean saveAddress() {

        String query = " insert into address(city,state,street,zipCode) values("
                + "'" + address.getCity() + "'" + "," + "'" + address.getState() + "'" + ","
                + "'" + address.getStreet() + "'" + "," + "'" + address.getZip() + "'" + ")";

        Integer idAddress = jdbcManager.insertData(query);
        //address.setIdAddress(idAddress);
        address.setId(idAddress.toString());
        if (idAddress == 0)
            return false;
        else
            return true;

    }

    public boolean savePerson() {
        String query = " insert into person(firstName,lastName,telephone,idAddress) values("
                + "'" + person.getFirstName() + "'" + "," + "'" + person.getLastName() + "'" + ","
                + "'" + person.getTelephone() + "'" + "," + "'" + person.getAddress().getId() + "'" + ")";

        Integer idPerson = jdbcManager.insertData(query);

        person.setId(idPerson.toString());
        if (idPerson == 0)
            return false;
        else
            return true;


    }

    public boolean saveAuthor() {
        //Integer idAdress = libMember.getAddress().getIdAddress();
        String idPerson = author.getId();
        String query = " insert into author(bio,idPerson) values("
                + "'"+author.getBio()+"'"+","+"'" + idPerson + "'" + ")";

        Integer idAuthor = jdbcManager.insertData(query);
        author.setId(idAuthor.toString());
        if (idAuthor == 0)
            return false;
        else
            return true;

    }

    public boolean removeAddress() {
        String query = " delete from address where idAddress=" + "'" + address.getId() + "'";
        return jdbcManager.deleteData(query);

    }

    public boolean removePerson() {
        String query = " delete from person where idPerson=" + "'" + person.getId() + "'";
        return jdbcManager.deleteData(query);

    }

    @Override
    public boolean rollBack() {
        boolean test1 = true;
        boolean test2 = true;
        if (address != null) {
            test1 = removeAddress();
        }
        if (person != null) {
            test2 = removePerson();
        }

        if (test1 == true && test2 == true)
            return true;
        else
            return false;

    }
}
