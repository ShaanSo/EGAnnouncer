package ru.katkova.egannouncerbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.katkova.egannouncerbot.data.Promotion;

import java.util.Date;
import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, String> {

    public List<Promotion> findByTitleAndStartDateAndEndDate(String title, Date startdate, Date enddate);
}
