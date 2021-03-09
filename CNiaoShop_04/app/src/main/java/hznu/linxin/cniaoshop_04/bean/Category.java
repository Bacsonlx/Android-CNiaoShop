package hznu.linxin.cniaoshop_04.bean;

/**
 * @author: BacSon
 * data: 2021/3/8
 */
public class Category extends BaseBean {

    public Category() { }

    public Category(String name) {

        this.name = name;
    }

    public Category(long id ,String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
}

