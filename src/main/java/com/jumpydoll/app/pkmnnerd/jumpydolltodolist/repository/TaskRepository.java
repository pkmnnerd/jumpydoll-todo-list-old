package com.jumpydoll.app.pkmnnerd.jumpydolltodolist.repository;

import com.jumpydoll.app.pkmnnerd.jumpydolltodolist.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskRepository extends MongoRepository<Task, String> {
}
