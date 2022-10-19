package ru.katkova.egannouncerbot.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonData {
    public Data data;

    public List<Elements> getElements(){
        return  this.data.getCatalog().getSearchStore().getElements();
    }

    @SneakyThrows
    public List<Promotion> getCurrentPromotionsList() {
        List<Promotion> promotionsList = new ArrayList<>();
        getElements().stream().filter(element -> !element.getTitle().isEmpty())
                .filter(element -> isNotNull(element.getPromotions()))
                .filter(element -> !element.getPromotions().getPromotionalOffers().isEmpty())
                .filter(element -> !element.getPromotions().getPromotionalOffers().get(0).getPromotionalOffers().isEmpty())
                .forEach(element -> {
                    element.getPromotions().getPromotionalOffers().stream()
                            .forEach(promotionalOffer -> {promotionalOffer.getPromotionalOffers().stream()
                                    .forEach(innerPromotionalOffer ->{
                                        if (innerPromotionalOffer.getDiscountSetting() != null &&
                                        !innerPromotionalOffer.getDiscountSetting().getDiscountPercentage().isEmpty() &&
                                        innerPromotionalOffer.getDiscountSetting().getDiscountPercentage().equals("0")) {
                                            Promotion promotion = new Promotion();
                                            promotion.setTitle(element.getTitle());
                                            promotion.setDescription(element.getDescription());
                                            promotion.setImageUrl(element.getKeyImages().get(0).getUrl());
                                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                                            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                                            try {
                                                Date start = simpleDateFormat.parse(innerPromotionalOffer.getStartDate());
                                                Date end = simpleDateFormat.parse(innerPromotionalOffer.getEndDate());
                                                promotion.setStartDate(start);
                                                promotion.setEndDate(end);
                                                promotionsList.add(promotion);
                                            } catch (ParseException e) {
                                            }
                                        }
                                    });});
                });

//        for (Elements el: getElements()) {
//            Promotion promotion = new Promotion();
//            if (!el.getTitle().isEmpty()) {
//                promotion.setTitle(el.getTitle());
//            } else continue;
//            if (!el.getDescription().isEmpty()) {
//                promotion.setDescription(el.getDescription());
//            }
//            if (!el.getKeyImages().isEmpty()) {
//                promotion.setImageUrl(el.getKeyImages().get(0).getUrl());
//            }
//            if (isNotNull(el.getPromotions()) && !el.getPromotions().getPromotionalOffers().isEmpty()) {
//                for (PromotionalOffers offer: el.getPromotions().getPromotionalOffers()) {
//                    if (!offer.getPromotionalOffers().isEmpty()) {
//                        for (PromotionalOffers innerOffers: offer.getPromotionalOffers()) {
//                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//                            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//                            Date start = simpleDateFormat.parse(innerOffers.getStartDate());
//                            Date end = simpleDateFormat.parse(innerOffers.getEndDate());
//                            promotion.setStartDate(start);
//                            promotion.setEndDate(end);
//                        }
//                    }
//                }
//            } else continue;
//            promotionsList.add(promotion);
//        }
        return promotionsList;
    }

    private boolean isNotNull(Object obj) {
        return obj != null;
    }
}
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
class Data {
    public Catalog Catalog;
}
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
class Catalog {
    public SearchStore searchStore;
}

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
class SearchStore {
    public List<Elements> elements;
}

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
class Elements {
    public String title;
    public String description;
    public List<KeyImages> keyImages;
    public Promotions promotions;

    public boolean existInDataBase() {
        return true;
    }
}

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
class KeyImages {
    public String url;
}

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
class Promotions {
    public List<PromotionalOffers> promotionalOffers;
}

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
class PromotionalOffers {
    public String startDate;
    public String endDate;
    public List<PromotionalOffers> promotionalOffers;
    public DiscountSetting discountSetting;
}

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
class DiscountSetting {
    public String discountPercentage;
    public String discountType;
}
