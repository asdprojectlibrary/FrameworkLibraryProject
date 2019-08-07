package dao.rdb.ModelDataAccess;

import model.Member;

import java.util.List;


public class MemberDataAccess extends RDBDataAccess<Member> {

    public MemberDataAccess() {
        super(Member.class);
    }

    @Override
    public List<Member> getAll() {
        return rdb.searchAllLibraryMember();
    }

    @Override
    public void save(Member book) {
        rdb.save(book);
    }

    @Override
    public Member get(String key) {
        return rdb.searchLibraryMemberById(key);
    }
}