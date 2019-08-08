package Service;

import model.Member;
import repository.BaseRepository;

import java.util.List;

final public class MemberService {

    private BaseRepository<Member> repository;

    public MemberService() {
        repository = new BaseRepository<>(Member.class);
    }


    public void save(Member member) throws Exception {

        if (
                member.getId().isEmpty() ||
                member.getFirstName().isEmpty() ||
                member.getLastName().isEmpty() ||
                member.getAddress().getStreet().isEmpty() ||
                member.getAddress().getCity().isEmpty() ||
                member.getAddress().getState().isEmpty() ||
                member.getAddress().getZip().isEmpty() ||
                member.getTelephone().isEmpty()) {

            throw new Exception("All fields are required");
        }

        repository.getDataAccess().save(member);
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
