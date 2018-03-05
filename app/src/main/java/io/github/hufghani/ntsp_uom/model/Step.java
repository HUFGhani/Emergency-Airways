package io.github.hufghani.ntsp_uom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hamzaghani on 02/03/2018.
 */

public class Step implements Serializable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("question")
    @Expose
    private Object question;
    @SerializedName("options")
    @Expose
    private List<Option> options = null;
    private final static long serialVersionUID = -5750296212514947176L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Step() {
    }

    /**
     *
     * @param content
     * @param id
     * @param title
     * @param question
     * @param options
     */
    public Step(String id, String title, String content, Object question, List<Option> options) {
        super();
        this.id = id;
        this.title = title;
        this.content = content;
        this.question = question;
        this.options = options;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Object getQuestion() {
        return question;
    }

    public void setQuestion(Object question) {
        this.question = question;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }


    @Override
    public String toString() {
        return new StringBuilder().append("Step{").append("id='").append(id).append('\'').append(", title='").append(title).append('\'').append(", content='").append(content).append('\'').append(", question=").append(question).append(", options=").append(options).append('}').toString();
    }
}
