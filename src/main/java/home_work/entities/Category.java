package home_work.entities;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class Category {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int categoryId;

//  TODO: как заполнять поле parent_category_id ?
//    @Column(name = "parent_category_id")
    @ManyToOne
    @JoinColumn(name = "category_id", insertable = false, updatable = false)  //одно из полей, если оно ссылается на один и тот же столбец
    private Category parentCategory;                                          //д.б. помечено как insertable = false, updatable = false

    @Column(name = "category_name", nullable = false)
    private String categoryName;


    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
