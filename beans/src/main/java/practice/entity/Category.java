package practice.entity;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "category_name")
    @NotNull
    private String categoryName;

    @Column(name = "category_level")
    @NotNull
    private Integer categoryLevel;

    @Column(name = "parent_id")
    @NotNull
    private Long parentId;

    @Column(name = "category_icon")
    private String categoryIcon;

    @Column(name = "category_slogan")
    private String categorySlogan;

    @Column(name = "category_pic")
    private String categoryPic;

    @Column(name = "category_bg_color")
    private String categoryBgColor;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "category_id", insertable = false, updatable = false)
    private Category parentCategory;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentCategory", cascade = CascadeType.ALL)
    private List<Category> childCategories;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Product> products;

    @OneToMany(mappedBy = "rootCategory", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Product> allChildProducts;
}
