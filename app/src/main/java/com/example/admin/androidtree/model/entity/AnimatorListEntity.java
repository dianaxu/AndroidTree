package com.example.admin.androidtree.model.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Diana
 * @date 2017/7/18
 */

public class AnimatorListEntity {
    public ArrayList<TagsEntity> tagsEntityList;

    public static class TagsEntity {
        public String tagsName;
        public ArrayList<TagInfo> tagInfoList;
    }

    public static class TagInfo {
        public int tagIndex;
        public String tagName;

        public TagInfo() {
        }

        public TagInfo(int tagIndex, String tagName) {
            this.tagIndex = tagIndex;
            this.tagName = tagName;
        }
    }

    public class  ss{

    }

}
