package com.example.kiosk.menu.entity;

import com.example.global.BasedEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Menu extends BasedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private boolean isSoldOut;

    @Column(nullable = false)
    private String category;

    public Menu(String name, Integer price, boolean isSoldOut, String category) {
        this.name = name;
        this.price = price;
        this.isSoldOut = isSoldOut;
        this.category = category;
    }
}