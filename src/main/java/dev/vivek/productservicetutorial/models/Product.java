package dev.vivek.productservicetutorial.models;


import dev.vivek.productservicetutorial.dtos.RatingDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product extends BaseModel{
    private String title;
    private Double price;
    private String description;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private Category category;
    private String imageUrl;

    //private RatingDto rating;
    private boolean isPublic;
}
