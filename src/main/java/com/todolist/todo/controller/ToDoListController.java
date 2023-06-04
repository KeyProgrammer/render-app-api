package com.todolist.todo.controller;

import com.todolist.todo.models.ListElementCreator;
import com.todolist.todo.models.ListElementDTO;
import com.todolist.todo.models.ListElementEntity;
import com.todolist.todo.service.ToDoListService;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/todo")
@CrossOrigin
public class ToDoListController {

    private final ToDoListService toDoListService;

    @GetMapping
    public List<ListElementEntity> getToDoList() {
        return toDoListService.getToDoList();
    }

    @PostMapping
    public ListElementEntity addToDoListElement(@RequestBody ListElementCreator newToDoListElement) {
        return toDoListService.addToDoListElement(newToDoListElement);
    }

    @PutMapping("/{id}")
    public void updateToDoListElement(@PathVariable Long id,@RequestBody ListElementDTO updateToDoListElement) {
        toDoListService.updateToDoListElement(id,updateToDoListElement);
    }

    @DeleteMapping("/{id}")
    public void deleteToDoListElement(@PathVariable Long id) {
        toDoListService.deleteToDoListElement(id);
    }
}
