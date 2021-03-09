package hznu.linxin.cniaoshop_04.bean;

import java.io.Serializable;

/**
 * @author: BacSon
 * data: 2021/3/8
 */
public class BaseBean implements Serializable {

    protected   long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
