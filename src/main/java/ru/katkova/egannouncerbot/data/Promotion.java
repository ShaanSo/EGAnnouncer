package ru.katkova.egannouncerbot.data;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import ru.katkova.egannouncerbot.repository.PromotionRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Getter
@Setter
public class Promotion {
    public String id;
    public String title;
    public String description;
    public Date startDate;
    public Date endDate;
    public String imageUrl;

    @SneakyThrows
    public boolean isActualPromotion() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date startDateFormatted = df.parse(df.format(startDate));
        Date endDateFormatted = df.parse(df.format(endDate));
        Date GmtDate = df.parse(df.format(new Date()));
        System.out.println(GmtDate);
        System.out.println(startDateFormatted);
        System.out.println(endDateFormatted);
        return GmtDate.after(startDateFormatted) && GmtDate.before(endDateFormatted);
    }

    public boolean existsInDB() {
        PromotionRepository connectionP = new PromotionRepository();
        return connectionP.existsInDB(title, new java.sql.Date(startDate.getTime()),new java.sql.Date(endDate.getTime()));
    }

    public void putIntoDB() {
        PromotionRepository connectionP = new PromotionRepository();
        connectionP.createNewPromotion(id, title, description,new java.sql.Date(startDate.getTime()),new java.sql.Date(endDate.getTime()));
    }
}
