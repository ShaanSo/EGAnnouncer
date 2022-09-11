package ru.katkova.egannouncerbot.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.katkova.egannouncerbot.data.Promotion;
import ru.katkova.egannouncerbot.repository.PromotionRepository;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class PromotionService {

    @Autowired
    PromotionRepository promotionRepository;

    public boolean existsInDB(Promotion promotion) {
        List<Promotion> promotionList= promotionRepository.findByTitleAndStartDateAndEndDate(promotion.title, promotion.startDate, promotion.endDate);
        return !promotionList.isEmpty();
    }

    public void putIntoDB(Promotion promotion) {
        promotionRepository.save(promotion);
    }

    @SneakyThrows
    public boolean isActualPromotion(Promotion promotion) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date startDateFormatted = df.parse(df.format(promotion.startDate));
        Date endDateFormatted = df.parse(df.format(promotion.endDate));
        Date GmtDate = df.parse(df.format(new Date()));
        System.out.println(GmtDate);
        System.out.println(startDateFormatted);
        System.out.println(endDateFormatted);
        return GmtDate.after(startDateFormatted) && GmtDate.before(endDateFormatted);
    }
}
