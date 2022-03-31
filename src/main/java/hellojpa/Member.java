package hellojpa;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
//@SequenceGenerator(name="member_seq_generator", sequenceName = "member_seq")
public class Member extends BaseEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="member_seq_generator") // AUTO: db 방언에 따라 자동으로, IDENTITY ,SEQUENCE, TABLE
    private Long id;

    @Column(name = "USERNAME")
    private String username;

//    @Column(name = "TEAM_ID")
//    private Long teamId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

//    @Column(name = "name", updatable = false, nullable = false, unique = true, columnDefinition = "Empty")
//     unique 잘 안씀 >> 이름이 random 생성이라 알아보기 어려움
//    private Long username;

    @ManyToMany
    @JoinTable(name = "MEMBER_PRODUCT")
    private List<Product> products = new ArrayList<>();

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Team getTeam() {
        return team;
    }

//    public void changeTeam(Team team) {
//        this.team = team;
//        team.getMembers().add(this);
//    }

    //    public Long getTeamId() {
//        return teamId;
//    }
//
//    public void setTeamId(Long teamId) {
//        this.teamId = teamId;
//    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

//    public LoDate getCreatedDate() {
//        return createdDate;
//    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public LocalDate getTestLocalDate() {
        return testLocalDate;
    }

    public void setTestLocalDate(LocalDate testLocalDate) {
        this.testLocalDate = testLocalDate;
    }

    public LocalDateTime getTestLocalDateTime() {
        return testLocalDateTime;
    }

    public void setTestLocalDateTime(LocalDateTime testLocalDateTime) {
        this.testLocalDateTime = testLocalDateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public void setTeam(Team team) {
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

// controller 에서는 entity 반환하면 안됨 (entity > dto로 변환해서 반환해야 함. entity 변경시 api spec이 변경되어 버리기 때문에)
    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", team=" + team +
                ", age=" + age +
                ", roleType=" + roleType +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                ", testLocalDate=" + testLocalDate +
                ", testLocalDateTime=" + testLocalDateTime +
                ", description='" + description + '\'' +
                ", temp=" + temp +
                '}';
    }
}
