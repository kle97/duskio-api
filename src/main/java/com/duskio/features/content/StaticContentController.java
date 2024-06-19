package com.duskio.features.content;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Tag(name = "content", description = "Content API")
public class StaticContentController {
    
    @GetMapping("/public/rapidoc")
    public String apiDocs() {
        return "rapidoc.html";
    }
}
