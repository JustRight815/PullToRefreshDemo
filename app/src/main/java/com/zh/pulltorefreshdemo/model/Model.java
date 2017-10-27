package com.zh.pulltorefreshdemo.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * 模拟数据
 */

public class Model implements Parcelable, MultiItemEntity {
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
    }

    public Model() {
    }

    protected Model(Parcel in) {
        this.type = in.readString();
    }

    public static final Creator<Model> CREATOR = new Creator<Model>() {
        @Override
        public Model createFromParcel(Parcel source) {
            return new Model(source);
        }

        @Override
        public Model[] newArray(int size) {
            return new Model[size];
        }
    };

    @Override
    public String toString() {
        return "ItemListBean{" +
                "type='" + type + '\'' +
                '}';
    }

    @Override
    public int getItemType() {
        return 0;
    }
}
