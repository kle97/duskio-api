package com.duskio.features.indexer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.duskio.common.constant.Constant.ADMIN_API_PATH;

@RestController
@RequiredArgsConstructor @Slf4j
@RequestMapping(ADMIN_API_PATH + "indexer")
@Tag(name = "indexer", description = "Mass Indexer Admin API")
public class MassIndexerController {

    private final MassIndexerService massIndexerService;
    
    @GetMapping("/mass-index")
    @Operation(summary = "reinitialize indexes", tags = "mass-indexer-admin")
    public ResponseEntity<Void> massIndex() throws InterruptedException, IOException {
        massIndexerService.reinitializeIndexes();
        return ResponseEntity.ok().build();
    }
}
