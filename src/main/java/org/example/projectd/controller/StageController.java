package org.example.projectd.controller;


import lombok.RequiredArgsConstructor;
import org.example.projectd.dto.StageDTO;
import org.example.projectd.service.StageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/project/stage")
@RequiredArgsConstructor
public class StageController {
    private final StageService stageService;

    @GetMapping
    public ResponseEntity<List<StageDTO>> getStagesForUser(
            @RequestHeader("X-User-Id") String userId
    ) {
        List<StageDTO> stages = stageService.getStagesByUserId(Integer.parseInt(userId));
        return ResponseEntity.ok(stages);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<StageDTO>> getPendingStages() {
        List<StageDTO> pendingStages = stageService.getPendingStages();
        return ResponseEntity.ok(pendingStages);
    }

    @PutMapping
    public ResponseEntity<StageDTO> updateStage(
            @RequestBody StageDTO stageDTO
    ) {
        StageDTO updatedStage = stageService.updateStage(stageDTO);
        return ResponseEntity.ok(updatedStage);
    }

}
