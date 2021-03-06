package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Set;

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
//            List<Member> result = em.createQuery("select m from Member as m", Member.class)
//                            .getResultList();

            // 비영속
//            Member member = new Member();
//            member.setId(101L);
//            member.setName("HelloJPA");

            // 영속 (1차 캐시)
//            em.persist(member);
//            em.detach(member); // 영속성 컨텍스트에서 분리
//            tx.commit();
//
//            Member findMember1 = em.find(Member.class, 101L);
//            Member findMember2 = em.find(Member.class, 101L);

//            System.out.println("result=" + (findMember1 == findMember2));

            // 영속 (batch)
//            Member member1 = new Member(150L, "A");
//            Member member2 = new Member(160L, "B");
//            System.out.println("===========================");
//            tx.commit();

            // 영속 (변경감지)
//            Member member3 = em.find(Member.class, 150L);
//            member3.setName("ZZZZZ");
//            em.persist(member3); >> 값만 변경했는데 DB에서 변경됨 (update 쿼리 날아감)
//            System.out.println("==========================");
//            tx.commit();

            // 플러시
//            Member member4 = new Member(200L, "member200");
//            em.persist(member4);
//            em.flush(); // >> 이 시점에서 쿼리 날아감
//            System.out.println("==========================");
//            tx.commit();

            // 준영속
//            Member member5 = em.find(Member.class, 150L);
//            member5.setName("AAAA");
//            em.detach(member5); // jpa 에서 관리 안함. 쿼리 안날아감
//            em.clear(); // 영속성 컨텍스트 통으로 날림 (1차 캐시 날림). 쿼리 안날아감
//            System.out.println("==========================");
//            tx.commit();

            // 저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            em.persist(member);

            team.addMember(member);

            em.flush();
            em.clear();

            Team findTeam = em.find(Team.class, team.getId()); //1차 캐시에 존대

            // 조회
            Member findMember = em.find(Member.class, member.getId());
            List<Team> members =  findMember.getTeam().getMembers(); // 양방향 연관관계 (mapping)
            Team fineTeam = findMember.getTeam();
            System.out.println("findTeam= " + fineTeam.getName());

            for (Team m : members) {
                System.out.println("m = " + m.getMembers());
            }

            // 수정
//            Team newTeam =  em.find(Team.class, 100L);
//            findMember.changeTeam(newTeam);

            Movie movie = new Movie();
            movie.setDirector("aaa");
            movie.setActor("bbb");
            movie.setName("avengers");
            movie.setPrice(10000);

            em.persist(movie);

            em.flush();
            em.clear();

            Movie findMovie = em.find(Movie.class, movie.getId());

            //proxy
            Member member1 = em.find(Member.class, 1L);
            printMember(member1);
            printMemberAndTeam(member1);

            Member member2 = new Member();
            member2.setUsername("hello2");

            Member member3 = new Member();
            member3.setUsername("hello3");

            em.flush();
            em.clear();

             Member m1 = em.find(Member.class, member2.getId());
             Member m2 = em.getReference(Member.class, member3.getId());
             // type 비교는 절대 == 으로 하면 안됨. instanceOf로!!!!
            System.out.println("m1 == m2: " + (m1.getClass() == m2.getClass()));
            System.out.println("m1 == m2: " + (m1 instanceof Member));
            System.out.println("m1 == m2: " + (m2 instanceof Member));

            System.out.println("m1 = " + m1.getClass());

            Member reference = em.getReference(Member.class, member1.getId());
            Member reference1 = em.find(Member.class, member1.getId());
            System.out.println("reference = " + reference.getClass()); // proxy
            System.out.println("reference1 = " + reference1.getClass());
            System.out.println("a == a: " + (m1 == reference));
            System.out.println("reference == reference1: " + (reference == reference1));
            // 이미 영속성 컨텍스트에 m1이 캐시로 올라와있음
            // == 비교가 한 영속성 컨텍스트에서 온거면 true를 반환
            // proxy에서 한번 호출되면 em.find 해도 proxy 반환
            // proxy건 아니건 문제없게 개발해야함

            // 프록시 인스턴스 초기화 여부 확인
            System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(reference));

//            em.clear(); 영속석 컨텍스트 닫거나,
//            em.detach(reference); 영속성 컨텍스트에서 reference 빼버리면
//            아래 proxy 초기화 못함 (오류 발생 > org.hibernate.LazyInInitializeException : could not initialize proxy)
            reference.getUsername(); // proxy 강제 초기화

            // 프록시 강제 초기화 좋은 방법 (JPA 표준에는 강제 초기화 없음)
            Hibernate.initialize(reference);

            // 프록시 인스턴스 초기화 여부 확인
            System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(reference));

            Member findMember2 = em.getReference(Member.class, member2.getId());
//            Member findMember =  em.find(Member.class, member2.getId());
            System.out.println("before findMember = " + findMember2.getClass());
            System.out.println("findMemberId = " + findMember2.getId());
            System.out.println("findMemberUsername = " + findMember2.getUsername());
            System.out.println("after findMember = " + findMember2.getClass());

            // 지연로딩
            Team team1 = new Team();
            team1.setName("teamA");
            em.persist(team1);

            Member member4 = new Member();
            member4.setUsername("member4");
            member4.setTeam(team1);

            em.flush();
            em.clear();

            Member m = em.find(Member.class, member4.getId());
            System.out.println("m = " + m.getTeam().getClass()); // team은 porxy로 가져옴

            System.out.println("=================");
            m.getTeam().getName(); // 이 시점에서 proxy 초기화되고 쿼리 날아감
            System.out.println("=================");

            // cascade
            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);

            em.persist(parent);
//            em.persist(child1);
//            em.persist(child2);

            em.flush();
            em.clear();

            // orphanRemoval
            Parent findParent = em.find(Parent.class, parent.getId());
            findParent.getChildList().remove(0);
            em.remove(findParent);

            // embedded type의 side effect
            Address address = new Address("city", "street", "1000");

            Member member5 = new Member();
            member5.setUsername("member1");
            member5.setHomeAddress(address);
            em.persist(member5);

//            Member member6 = new Member();
//            member6.setUsername("member1");
//            member6.setHomeAddress(address);
//            em.persist(member6);

            // side effect 방지
            Address copyAddress = new Address(address.getCity(), address.getStreet(), address.getZipCode());

            Member member6 = new Member();
            member6.setUsername("member1");
            member6.setHomeAddress(copyAddress);
            em.persist(member6);

            // 불변객체 수정
            Address newAddress = new Address("NewCity", address.getStreet(), address.getZipCode());
            member5.setHomeAddress(newAddress);

            // 값타입 컬렉션
            Member member7 = new Member();
            member7.setUsername("member1");
            member7.setHomeAddress(new Address("home city", "street", "10000"));

            member7.getFavoriteFood().add("치킨");
            member7.getFavoriteFood().add("족발");
            member7.getFavoriteFood().add("피자");

//            member7.getAddressesHistory().add(new Address("old1", "street", "10000"));
//            member7.getAddressesHistory().add(new Address("old2", "street", "10000"));

            em.persist(member7);

            em.flush();
            em.clear();

            Member findMember3 = em.find(Member.class, member7.getId());

//            List<Address> addressesHistory = findMember3.getAddressesHistory();
//            for (Address address1 : addressesHistory) {
//                System.out.println("address = " + address1.getCity());
//            }

            Set<String> favoriteFoods = findMember3.getFavoriteFood();
            for (String favoriteFood : favoriteFoods) {
                System.out.println("favoriteFood = " + favoriteFood);
            }

            // 값타입 수정 homeCity -> newCity
            Address a = findMember3.getHomeAddress();
            findMember3.setHomeAddress(new Address("newCity", a.getStreet(), a.getZipCode()));

            //치킨 -> 한식
            // string 은 지우고나서 새로 넣어야 함. update 안됨
            findMember3.getFavoriteFood().remove("치킨");
            findMember3.getFavoriteFood().add("한식");

            //주소
//            findMember3.getAddressesHistory().remove(new Address("old1", "street", "10000"));
//            findMember3.getAddressesHistory().add(new Address("newCity1", "street", "10000"));

            //oneToMany 방식
            member7.getAddressesHistory().add(new AddressEntity("old1", "street", "10000"));
            member7.getAddressesHistory().add(new AddressEntity("old2", "street", "10000"));

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void printMember(Member member1) {
        System.out.println("member = " + member1.getUsername());
    }

    private static void printMemberAndTeam (Member member1) {
        String username = member1.getUsername();
        System.out.println("username = " + username);

        Team team = member1.getTeam();
        System.out.println("team = " + team.getName());
    }
}