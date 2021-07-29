package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    //    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    // 이렇게 구현체를 바꾸는 순간 코드를 변경해야 하기 때문에 OCP 위반이다.

    private final DiscountPolicy discountPolicy;
    // 인터페이스에만 의존 -> 구현체에 의존X 추상화에만 의존
    // DIP를 어떻게 지키는 것일까?
    // 누군가 구현체를 대신 생성해서 주입해줘야 한다.


    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member,itemPrice);

        return new Order(memberId,itemName,itemPrice,discountPrice);
        // 단일 책임 원칙이 잘 지켜진 예시
    }
}
