package hellojpa;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@SequenceGenerator(name="member_seq_generator", sequenceName = "member_seq")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="member_seq_generator") // AUTO: db 방언에 따라 자동으로, IDENTITY ,SEQUENCE, TABLE
    private String id;

    @Column(name = "name", updatable = false, nullable = false, unique = true, columnDefinition = "Empty")
    // unique 잘 안씀 >> 이름이 random 생성이라 알아보기 어려움
    private Long username;

    private Integer age;

    @Enumerated(EnumType.STRING) // ODINAL (default): 관련 data 추가될 때 순서로 들어가면 잘못된 data 들어갈 수 있음(쓰면 안됨)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    private LocalDate testLocalDate;

    private LocalDateTime testLocalDateTime;

    @Lob
    private String description;

    @Transient
    private int temp;

    public Member() {

    }

//    public Member(Long id, String name) {
//        this.id = id;
//        this.name = name;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
}
