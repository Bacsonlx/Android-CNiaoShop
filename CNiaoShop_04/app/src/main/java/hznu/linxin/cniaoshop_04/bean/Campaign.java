package hznu.linxin.cniaoshop_04.bean;

import java.io.Serializable;

/**
 * @author: BacSon
 * data: 2021/3/9
 */

/**
 *  代表首页的资源图片
 */
public class Campaign implements Serializable {


    private Long id;
    private String title;
    private String imgUrl;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}

