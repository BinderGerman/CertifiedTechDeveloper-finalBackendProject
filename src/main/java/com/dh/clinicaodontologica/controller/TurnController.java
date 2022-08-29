package com.dh.clinicaodontologica.controller;

import com.dh.clinicaodontologica.dto.TurnDTO;
import com.dh.clinicaodontologica.model.Turn;
import com.dh.clinicaodontologica.service.TurnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@RestController
@RequestMapping("/turnos")
public class TurnController {

    @Autowired
    TurnService turnService;

    @PostMapping
    public ResponseEntity<Turn> createTurn(@RequestBody Turn turn) {
        return ResponseEntity.ok(turnService.createTurn(turn));
    }

    @GetMapping("/{id}")
    public TurnDTO readTurn(@PathVariable Long id) {
        return turnService.readTurn(id);
    }

    @PutMapping
    public ResponseEntity<Turn> updateTurn(@RequestBody Turn turn) {
        return ResponseEntity.ok(turnService.updateTurn(turn));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTurn(@PathVariable Long id) {
        turnService.deleteTurn(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public Set<TurnDTO> getListTurns(){
       return turnService.getListTurns();
    }
}
