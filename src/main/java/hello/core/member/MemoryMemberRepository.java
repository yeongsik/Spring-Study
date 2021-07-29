package hello.core.member;

import java.util.HashMap;
import java.util.Map;

public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    // 실무에서는 해쉬맵보다 컨커런트 해쉬맵을 쓰는 것이 많다.
    // why? 동시성 이슈가 있기 때문에
    @Override
    public void save(Member member) {
        store.put(member.getId() , member);
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
