package jpa.hello.repository;

import jpa.hello.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepository {
    @PersistenceContext
    private EntityManager em;
    public void save(Member member){
        em.persist(member);
    }
    public Optional<Member> findById(Long id){
        return Optional.ofNullable(em.find(Member.class, id));
    }
    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }
    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
