package com.example.studentcheckinbackend.services;

import com.example.studentcheckinbackend.models.Room;
import com.example.studentcheckinbackend.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    public final RoomRepository roomRepository;
    @Autowired
    public RoomService(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }
    public List<String> getValidationErrors(BindingResult bindingResult) {
        List<String> errorMessages = new ArrayList<>();

        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();

            for (ObjectError error : errors) {
                errorMessages.add(error.getDefaultMessage());
            }
        }

        return errorMessages;
    }

    public boolean isRoomNameExists(String RoomName) {
        Optional<Room> existingClassRoom = roomRepository.findByRoomName(RoomName);
        return existingClassRoom.isPresent();
    }
    public Room getRoomById(Long roomId) {
        return roomRepository.findById(roomId).orElse(null);
    }
}
