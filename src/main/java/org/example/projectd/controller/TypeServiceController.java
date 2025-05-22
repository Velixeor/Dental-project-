package org.example.projectd.controller;


import lombok.RequiredArgsConstructor;
import org.example.projectd.dto.PageResponse;
import org.example.projectd.dto.TypeServiceDTO;
import org.example.projectd.service.TypeServiceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/project/type-service")
@RequiredArgsConstructor
public class TypeServiceController {
    private final TypeServiceService typeServiceService;

    @GetMapping("")
    public ResponseEntity<PageResponse<TypeServiceDTO>> getAllTypeServices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<TypeServiceDTO> result = typeServiceService.getAllTypeServices(PageRequest.of(page, size));
        return new ResponseEntity<>(PageResponse.fromPage(result), HttpStatus.OK);
    }
}
