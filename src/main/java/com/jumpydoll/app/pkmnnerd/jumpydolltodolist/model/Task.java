package com.jumpydoll.app.pkmnnerd.jumpydolltodolist.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("task")
public class Task {
    @Id
    String id;
    String description;
    Long createTime;
    boolean finished;

    public Task(String id, String description, long createTime, boolean finished) {
        super();
        this.id = id;
        this.description = description;
        this.createTime = createTime;
        this.finished = finished;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
