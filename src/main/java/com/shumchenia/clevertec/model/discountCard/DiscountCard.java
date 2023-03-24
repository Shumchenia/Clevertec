package com.shumchenia.clevertec.model.discountCard;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "discount_card")
public class DiscountCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    @ToString.Exclude
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name="percent")
    private Double percent;
}
