package ddwu.mobile.final_project.ma02_20170915;

import java.io.Serializable;

public class ReviewDto implements Serializable {
    //자신이 간 음식점에 대한 리뷰 정보를 담은 dto
    //'리뷰제목, 장소, 날짜, 평점, 세부내용' 이 있다.
    private long _id;
    private String title;
    private String location;
    private String date;
    private String gpa;
    private String contents;

    public ReviewDto(long _id, String title, String location, String date, String gpa, String contents, int img) {
        this._id = _id;
        this.title = title;
        this.location = location;
        this.date = date;
        this.gpa = gpa;
        this.contents = contents;
    }

    public ReviewDto(String title, String location, String date, String gpa, String contents, int img) {
        this.title = title;
        this.location = location;
        this.date = date;
        this.gpa = gpa;
        this.contents = contents;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGpa() {
        return gpa;
    }

    public void setGpa(String gpa) {
        this.gpa = gpa;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

}