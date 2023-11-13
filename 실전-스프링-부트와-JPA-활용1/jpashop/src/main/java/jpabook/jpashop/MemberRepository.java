package jpabook.jpashop;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class MemberRepository {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public Long save(Member member) {
		em.persist(member);
		return member.getId();
	}

	public Member find(Long id) {
		return em.find(Member.class, id);
	}

}
