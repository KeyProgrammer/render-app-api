package com.todolist.todo.service;

import com.todolist.todo.models.ListElementCreator;
import com.todolist.todo.models.ListElementDTO;
import com.todolist.todo.models.ListElementEntity;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
//    public void updateToDoListElement(Long id, ListElementDTO updatedToDoListElement) {
//        if (this.elementExist(id)) {
//            toDoList.stream()
//                    .forEach(ListElementEntity -> {
//                        if (ListElementEntity.getId().equals(id)) {
//                            ListElementEntity.setComplete(updatedToDoListElement.getComplete());
//                            ListElementEntity.setText(updatedToDoListElement.getText());
//                        }
//                    });
//        } else {
//            throw new RuntimeException("Element o podanym ID nie istnieje");
//        }
//    }


//    public void updateToDoListElement(Long id, ListElementDTO updatedToDoListElement) {
//        if (this.elementExist(id)) {
//            Optional<ListElementEntity> listElementEntityOptional = toDoList.stream()
//                    .filter(currentElement -> currentElement.getId().equals(id))
//                    .findFirst()
//                    .map(oldElement -> {
//                        ListElementEntity updatedListElement = new ListElementEntity(id,
//                                updatedToDoListElement.getText(),
//                                updatedToDoListElement.getComplete());
//                        return updatedListElement;
//                    });
//            this.toDoList = listElementEntityOptional.stream().toList();
//        } else {
//            throw new RuntimeException("Element o podanym ID nie istnieje");
//        }
//    }
@Service
@AllArgsConstructor
public class ToDoListService {

    // CREATE - addToDoListElement(), UPDATE - updateToDoListElement(id), DELETE - deleteToDoListElement(id)
    // READ - getToDoList() można dodać ewentualnie getToDoListElement(id) (raczej nie)
    private List<ListElementEntity> toDoList;

    public ListElementEntity addToDoListElement(ListElementCreator newToDoListElement) {
        ListElementEntity newElement;
            if (toDoList.size() == 0) {
                this.toDoList = new ArrayList<>();
                newElement = new ListElementEntity((long) 1,newToDoListElement.getText(), false);
            } else {
                Long currentId = toDoList.get(toDoList.size()-1).getId();
                newElement = new ListElementEntity(currentId + 1,newToDoListElement.getText(), false);
            }
        this.toDoList.add(newElement);
        return newElement;
    }

    public List<ListElementEntity> getToDoList() {
        if (toDoList.size() == 0) {
            return new ArrayList<>();
        }
        return toDoList;
    }

    public void updateToDoListElement(Long id, ListElementDTO updatedToDoListElement) {
        if (this.elementExist(id)) {
            toDoList.stream().filter(currentElement -> currentElement.getId().equals(id))
                    .forEach(ListElementEntity -> {
                            ListElementEntity.setComplete(updatedToDoListElement.isComplete());
                            ListElementEntity.setText(updatedToDoListElement.getText());
                    });
        } else {
            throw new RuntimeException("Element o podanym ID nie istnieje");
        }
    }


    private boolean elementExist(Long id) {
        ListElementEntity ElementByIdOptional = toDoList.stream()
                .filter(element -> element.getId().equals(id))
                .findFirst().orElse(null);
        if (ElementByIdOptional == null) {
            return false;
        }
        return true;
    }

    public void deleteToDoListElement(Long id) {
        if (this.elementExist(id)) {
            List<ListElementEntity> updatedList = toDoList.stream()
                    .filter(currentElement -> currentElement.getId() != id)
                    .collect(Collectors.toList());
            this.toDoList = updatedList;
        } else {
            throw new RuntimeException("Element o podanym ID nie istnieje");
        }
    }


}
