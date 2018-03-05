package io.github.hufghani.ntsp_uom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by hamzaghani on 02/03/2018.
 */

public class Option implements Serializable
{

    @SerializedName("caption")
    @Expose
    private String caption;
    @SerializedName("target")
    @Expose
    private String target;
    private final static long serialVersionUID = 6645902025225845721L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Option() {
    }

    /**
     *
     * @param target
     * @param caption
     */
    public Option(String caption, String target) {
        super();
        this.caption = caption;
        this.target = target;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Option{").append("caption='").append(caption).append('\'').append(", target='").append(target).append('\'').append('}').toString();
    }
}

