package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            //등록
//            Member member = new Member();
//            member.setId(2L);
//            member.setName("HelloB");

            //수정
//            Member findMember = em.find(Member.class, 1L);
//            findMember.setName("HelloJpa");

            //JPQL
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                            .getResultList();

            tx.commit();

            // 비영속
            Member member = new Member();
            member.setId(101L);
            member.setName("HelloJPA");

            // 영속 (1차 캐시)
            em.persist(member);
            em.detach(member); // 영속성 컨텍스트에서 분리
            tx.commit();

            Member findMember1 = em.find(Member.class, 101L);
            Member findMember2 = em.find(Member.class, 101L);

            System.out.println("result=" + (findMember1 == findMember2));

            // 영속 (batch)
            Member member1 = new Member(150L, "A");
            Member member2 = new Member(160L, "B");
            System.out.println("===========================");
            tx.commit();

            // 영속 (변경감지)
            Member member3 = em.find(Member.class, 150L);
            member3.setName("ZZZZZ");
//            em.persist(member3); >> 값만 변경했는데 DB에서 변경됨 (update 쿼리 날아감)
            System.out.println("==========================");
            tx.commit();

            // 플러시
            Member member4 = new Member(200L, "member200");
            em.persist(member4);
            em.flush(); // >> 이 시점에서 쿼리 날아감
            System.out.println("==========================");
            tx.commit();

            // 준영속
            Member member5 = em.find(Member.class, 150L);
            member5.setName("AAAA");
            em.detach(member5); // jpa 에서 관리 안함. 쿼리 안날아감
            em.clear(); // 영속성 컨텍스트 통으로 날림 (1차 캐시 날림). 쿼리 안날아감
            System.out.println("==========================");
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}