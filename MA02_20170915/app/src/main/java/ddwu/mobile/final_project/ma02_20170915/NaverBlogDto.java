package ddwu.mobile.final_project.ma02_20170915;

import java.io.Serializable;

public class NaverBlogDto implements Serializable {
    //블로그 게시물의 '제목, 요약, 게시일'을 담은 dto
    private  int _id;
    private String title;
    private String description;
    private String postdate;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostdate() {
        return postdate;
    }

    public void setPostdate(String postdate) {
        this.postdate = postdate;
    }

    public String toString() {return "제목)" + getTitle() + "\n요약) " + getDescription() +"\n날짜)" + getPostdate();}
}
