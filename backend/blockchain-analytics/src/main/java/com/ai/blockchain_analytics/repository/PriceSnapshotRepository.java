package com.ai.blockchain_analytics.repository;

import com.ai.blockchain_analytics.model.PriceSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Ce que ça fait :
//Interface qui étend JpaRepository, donc tu hérites de toutes les méthodes CRUD (save, findAll, findById, delete, etc.).
//C’est le moyen principal d’interagir avec ta base depuis Spring.
//Résultat : tu peux sauvegarder et lire des snapshots sans écrire de SQL.
@Repository
public interface PriceSnapshotRepository extends JpaRepository<PriceSnapshot, Long> {
}
