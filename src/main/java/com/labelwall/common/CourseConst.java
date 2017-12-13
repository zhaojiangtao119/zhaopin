package com.labelwall.common;

/**
 * Created by Administrator on 2017-12-13.
 */
public class CourseConst {

    public static final String STUDY_COUNT = "study_count";

    public static final String UPDATE_TIME = "update_time";

    public interface CourseType {
        int charges = 0;//付费
        int free = 1;//免费
    }

    public interface CommentType {
        int comment = 0;//评论
        int questions = 1;//问答
    }

    public enum CollectionType {
        COLLECTION_CLASSIFY_COURSE(1, "课程收藏");

        private int code;
        private String value;

        CollectionType(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }
    }

}
