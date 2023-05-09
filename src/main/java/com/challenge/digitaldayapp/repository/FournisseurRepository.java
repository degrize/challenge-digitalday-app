package com.challenge.digitaldayapp.repository;

import com.challenge.digitaldayapp.domain.Fournisseur;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Fournisseur entity.
 */
@Repository
public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {
    default Optional<Fournisseur> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Fournisseur> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Fournisseur> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct fournisseur from Fournisseur fournisseur left join fetch fournisseur.quartier",
        countQuery = "select count(distinct fournisseur) from Fournisseur fournisseur"
    )
    Page<Fournisseur> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct fournisseur from Fournisseur fournisseur left join fetch fournisseur.quartier")
    List<Fournisseur> findAllWithToOneRelationships();

    @Query("select fournisseur from Fournisseur fournisseur left join fetch fournisseur.quartier where fournisseur.id =:id")
    Optional<Fournisseur> findOneWithToOneRelationships(@Param("id") Long id);
}
