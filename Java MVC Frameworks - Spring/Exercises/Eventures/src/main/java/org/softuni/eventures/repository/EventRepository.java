package org.softuni.eventures.repository;

import org.softuni.eventures.domain.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, String> {

    @Query(value = "" +
            "   SELECT e.name, e.start_date_time, e.end_date_time, sum(o.tickets_count)\n" +
            "     FROM events AS e\n" +
            "LEFT JOIN orders AS o\n" +
            "       ON e.id = o.event_id\n" +
            "      AND o.user_id = :userId\n" +
            " GROUP BY e.id", nativeQuery = true)
    List<Object> findMyEventsWithTicketsCount(@Param("userId") String userId);
}
