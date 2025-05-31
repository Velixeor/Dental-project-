package org.example.projectd.controller;


import lombok.RequiredArgsConstructor;
import org.example.projectd.dto.PatternStageDTO;
import org.example.projectd.service.PatternStageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/project/pattern-stage")
@RequiredArgsConstructor
public class PatternStageController {
    private final PatternStageService patternStageService;

    @GetMapping("/by-type/{typeServiceId}")
    public ResponseEntity<List<PatternStageDTO>> getStagesByTypeService(@PathVariable Integer typeServiceId) {
        return new ResponseEntity<>(patternStageService.getPatternStagesByTypeService(typeServiceId), HttpStatus.OK);
    }

    @PutMapping("/order")
    public ResponseEntity<Void> updateExecutionOrder(@RequestBody List<PatternStageDTO> updatedStages) {
        patternStageService.updateExecutionOrder(updatedStages);
        return ResponseEntity.ok().build();
    }
}
