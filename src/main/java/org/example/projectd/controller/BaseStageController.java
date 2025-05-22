package org.example.projectd.controller;


import lombok.RequiredArgsConstructor;
import org.example.projectd.dto.BaseStageDTO;
import org.example.projectd.service.BaseStageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/v1/project/base-stage")
@RequiredArgsConstructor
public class BaseStageController {
    private final BaseStageService baseStageService;

    @GetMapping("")
    public ResponseEntity<List<BaseStageDTO>> getAllBaseStages() {
        return new ResponseEntity<>(baseStageService.getAllBaseStages(), HttpStatus.OK);
    }
}
