package org.softuni.kaizer_watches.repository;

import org.softuni.kaizer_watches.domain.entities.Watch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WatchRepository extends JpaRepository<Watch, String> {

    @Query(value = "" +
            "  SELECT *\n" +
            "    FROM watches AS w\n" +
            "ORDER BY w.views DESC\n" +
            "   LIMIT 4;", nativeQuery = true)
    List<Watch> getTop4Watches();
}
