package ru.katkova.egannouncerbot.data;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name ="PROMOTIONS")
public class Promotion {
    @Id
    @Column(name = "id")
    public String id;
    @Column(name = "title")
    public String title;
    @Column(name = "description")
    public String description;
    @Column(name = "startdate")
    public Date startDate;
    @Column(name = "enddate")
    public Date endDate;
    @Transient
    public String imageUrl;
}
