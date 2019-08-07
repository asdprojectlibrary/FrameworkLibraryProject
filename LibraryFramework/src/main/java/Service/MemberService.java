package Service;

import Repository.BaseRepository;
import model.Author;
import model.Member;

import java.util.List;

final public class MemberService {

    private BaseRepository<Member> repository;
    public MemberService() {
        repository = new BaseRepository<>(Member.class);
    }
    public void save(Member Member) {
        repository.getDataAccess().save(Member);
    }
    public void save(List<Member> members) {
        repository.getDataAccess().save(members);
    }
    public List<Member> getAll() {
        return repository.getDataAccess().getAll();
    }
    public Member getOne(String memberId) {
        return repository.getDataAccess().get(memberId);
    }
}
