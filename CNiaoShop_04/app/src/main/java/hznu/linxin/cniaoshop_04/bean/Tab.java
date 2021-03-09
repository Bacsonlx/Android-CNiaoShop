package hznu.linxin.cniaoshop_04.bean;

/**
 * @author: BacSon
 * data: 2021/3/6
 */
public class Tab {
    // 都使用id进行引用 所以都是int类型
    private int title;
    private int icon;

    public Tab(Class fragment, int title, int icon) {
        this.title = title;
        this.icon = icon;
        this.fragment = fragment;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Class getFragment() {
        return fragment;
    }

    public void setFragment(Class fragment) {
        this.fragment = fragment;
    }

    private Class fragment;
}
