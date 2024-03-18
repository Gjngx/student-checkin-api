package com.example.studentcheckinbackend.controllers;


import com.example.studentcheckinbackend.models.Room;
import com.example.studentcheckinbackend.models.ResponseObject;
import com.example.studentcheckinbackend.repositories.RoomRepository;
import com.example.studentcheckinbackend.services.RoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {
    private final RoomRepository roomRepository;
    private final RoomService roomService;
    @Autowired
    public RoomController(RoomRepository roomRepository, RoomService roomService) {
        this.roomRepository = roomRepository;
        this.roomService = roomService;
    }
    @GetMapping("")
    public ResponseEntity<List<Room>> getAllRooms(){
        List<Room> rooms = roomRepository.findAll();
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findRoomById(@PathVariable Long id){
        Optional<Room> foundRooms = roomRepository.findById(id);
        if (foundRooms.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("success", "Tìm thấy phòng học với mã phòng là "+ id , foundRooms)
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Mã phòng học " + id + " Không tồn tại","")
            );
        }
    }

    @PostMapping("")
    public ResponseEntity<ResponseObject> insertRoom(@Valid @RequestBody Room newRoom, BindingResult bindingResult) {

        List<String> errorMessages = roomService.getValidationErrors(bindingResult);

        if (!errorMessages.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("failed", "Lỗi validation", errorMessages)
            );
        }

        if (roomService.isRoomNameExists(newRoom.getRoomName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ResponseObject("failed", "Tên phòng học đã tồn tại", "")
            );
        }

        Room savedRoom = roomRepository.save(newRoom);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject("success", "Đã thêm phòng học thành công","")
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateRoom(@Valid @RequestBody Room newRoom, @PathVariable Long id, BindingResult bindingResult) {

        List<String> errorMessages = roomService.getValidationErrors(bindingResult);

        if (!errorMessages.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("failed", "Lỗi validation", errorMessages)
            );
        }

        Room checkRoom = roomService.getRoomById(id);

        if (checkRoom == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Không tìm thấy phòng học có mã: " + id, "")
            );
        }

        if (!checkRoom.getRoomName().equals(newRoom.getRoomName())) {
            if (roomService.isRoomNameExists(newRoom.getRoomName())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(
                        new ResponseObject("failed", "Tên phòng học đã tồn tại", "")
                );
            }
        }

        Room updatedRoom = roomRepository.findById(id)
                .map(room -> {
                    room.setRoomName(newRoom.getRoomName());
                    return roomRepository.save(room);
                })
                .orElse(null);

        if (updatedRoom != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("success", "Cập nhật phòng học "+ id +" thành công", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Không tìm thấy phòng học có mã: " + id, "")
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteRoom (@PathVariable Long id){
        boolean exists = roomRepository.existsById(id);
        if(exists){
            roomRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("success", "Xoá phòng học có mã: "+ id +" thành công", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Phòng học không tồn tại!", "")
        );
    }

}
