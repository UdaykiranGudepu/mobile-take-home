package com.ud.rickmortyinfo.ui.modules.episodes.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.ud.rickmortyinfo.ui.common.utils.Utils;

import java.util.List;

/**
 * Character
 *
 * @author uday.gudepu
 * Created on 2019-09-26
 * Copyright © 2019 RickyMortyInfo. All rights reserved.
 */
public class Characters implements Parcelable {

    /**
     * id : 1
     * name : Rick Sanchez
     * status : Alive
     * species : Human
     * type :
     * gender : Male
     * origin : {"name":"Earth (C-137)","url":"https://rickandmortyapi.com/api/location/1"}
     * location : {"name":"Earth (Replacement Dimension)","url":"https://rickandmortyapi.com/api/location/20"}
     * image : https://rickandmortyapi.com/api/character/avatar/1.jpeg
     * episode : ["https://rickandmortyapi.com/api/episode/1","https://rickandmortyapi.com/api/episode/2","https://rickandmortyapi.com/api/episode/3","https://rickandmortyapi.com/api/episode/4","https://rickandmortyapi.com/api/episode/5","https://rickandmortyapi.com/api/episode/6","https://rickandmortyapi.com/api/episode/7","https://rickandmortyapi.com/api/episode/8","https://rickandmortyapi.com/api/episode/9","https://rickandmortyapi.com/api/episode/10","https://rickandmortyapi.com/api/episode/11","https://rickandmortyapi.com/api/episode/12","https://rickandmortyapi.com/api/episode/13","https://rickandmortyapi.com/api/episode/14","https://rickandmortyapi.com/api/episode/15","https://rickandmortyapi.com/api/episode/16","https://rickandmortyapi.com/api/episode/17","https://rickandmortyapi.com/api/episode/18","https://rickandmortyapi.com/api/episode/19","https://rickandmortyapi.com/api/episode/20","https://rickandmortyapi.com/api/episode/21","https://rickandmortyapi.com/api/episode/22","https://rickandmortyapi.com/api/episode/23","https://rickandmortyapi.com/api/episode/24","https://rickandmortyapi.com/api/episode/25","https://rickandmortyapi.com/api/episode/26","https://rickandmortyapi.com/api/episode/27","https://rickandmortyapi.com/api/episode/28","https://rickandmortyapi.com/api/episode/29","https://rickandmortyapi.com/api/episode/30","https://rickandmortyapi.com/api/episode/31"]
     * url : https://rickandmortyapi.com/api/character/1
     * created : 2017-11-04T18:48:46.250Z
     */

    private int mId;
    private String mName;
    private String mStatus;
    private String mSpecies;
    private String mType;
    private String mGender;
    private String mImage;
    private String mUrl;
    private String mCreated;
    private List<String> mEpisode;
    private ObservableField<Status> mCharacterStatus = new ObservableField<>();
    private String mOrigin;
    private String mLocation;
    private Bitmap mBitmap;


    public enum Status {
        ALIVE("Alive"),
        DEAD("Dead"),
        UNKNOWN("Unknown");

        private String value;

        Status(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        @NonNull
        public String toString() {
            return this.getValue();
        }

    }

    public Characters() {

    }

    protected Characters(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mStatus = in.readString();
        mSpecies = in.readString();
        mType = in.readString();
        mGender = in.readString();
        mImage = in.readString();
        mUrl = in.readString();
        mCreated = in.readString();
        mEpisode = in.createStringArrayList();
        mOrigin = in.readString();
        mLocation = in.readString();
        mBitmap = in.readParcelable(Bitmap.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeString(mStatus);
        dest.writeString(mSpecies);
        dest.writeString(mType);
        dest.writeString(mGender);
        dest.writeString(mImage);
        dest.writeString(mUrl);
        dest.writeString(mCreated);
        dest.writeStringList(mEpisode);
        dest.writeString(mOrigin);
        dest.writeString(mLocation);
        dest.writeParcelable(mBitmap, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Characters> CREATOR = new Creator<Characters>() {
        @Override
        public Characters createFromParcel(Parcel in) {
            return new Characters(in);
        }

        @Override
        public Characters[] newArray(int size) {
            return new Characters[size];
        }
    };

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public void setStatus(String status) {
        this.mStatus = status;
        if (status.equalsIgnoreCase(Status.ALIVE.toString())) {
            mCharacterStatus.set(Status.ALIVE);
        } else if (status.equalsIgnoreCase(Status.DEAD.toString())) {
            mCharacterStatus.set(Status.DEAD);
        } else {
            mCharacterStatus.set(Status.UNKNOWN);
        }
    }

    public String getStatus() {
        return mStatus;
    }

    public Status getCharacterStatus() {
        return mCharacterStatus.get();
    }

    public ObservableField<Status> getObservableStatus() {
        return mCharacterStatus;
    }

    public String getSpecies() {
        return mSpecies;
    }

    public void setSpecies(String species) {
        this.mSpecies = species;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        this.mType = type;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        this.mGender = gender;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        this.mImage = image;
    }

    public void setCreated(String created) {
        this.mCreated = created;
    }

    public String getCreated() {
        return mCreated;
    }

    public String getFormattedDate() {
        return Utils.convertDateFormat(mCreated, "yyyy-MM-dd'T'HH:mm:ss", "MMMM dd, yyyy");
    }

    public void setOrigin(String origin) {
        this.mOrigin = origin;
    }

    public void setLocation(String location) {
        this.mLocation = location;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

}
