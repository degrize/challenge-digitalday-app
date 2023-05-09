package com.challenge.digitaldayapp.repository;

import com.challenge.digitaldayapp.domain.Quartier;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class QuartierRepositoryWithBagRelationshipsImpl implements QuartierRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Quartier> fetchBagRelationships(Optional<Quartier> quartier) {
        return quartier.map(this::fetchVilles);
    }

    @Override
    public Page<Quartier> fetchBagRelationships(Page<Quartier> quartiers) {
        return new PageImpl<>(fetchBagRelationships(quartiers.getContent()), quartiers.getPageable(), quartiers.getTotalElements());
    }

    @Override
    public List<Quartier> fetchBagRelationships(List<Quartier> quartiers) {
        return Optional.of(quartiers).map(this::fetchVilles).orElse(Collections.emptyList());
    }

    Quartier fetchVilles(Quartier result) {
        return entityManager
            .createQuery(
                "select quartier from Quartier quartier left join fetch quartier.villes where quartier is :quartier",
                Quartier.class
            )
            .setParameter("quartier", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Quartier> fetchVilles(List<Quartier> quartiers) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, quartiers.size()).forEach(index -> order.put(quartiers.get(index).getId(), index));
        List<Quartier> result = entityManager
            .createQuery(
                "select distinct quartier from Quartier quartier left join fetch quartier.villes where quartier in :quartiers",
                Quartier.class
            )
            .setParameter("quartiers", quartiers)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
