package br.com.grupo63.serviceclockin.api.controller.clockin;

import br.com.grupo63.serviceclockin.controller.ClockInController;
import br.com.grupo63.serviceclockin.controller.dto.ClockInControllerDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "ClockIn", description = "Saves clock in data.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/clockin")
public class ClockInAPIController {

    private final ClockInController controller;

    @Operation(
            tags = "2ª chamada - Fluxo principal - Registrar ponto",
            summary = "Registra o ponto do usuario",
            description = "Seria utilizado registrar o ponto do usuario em questao")
    @GetMapping
    public ResponseEntity<ClockInControllerDTO> queue(@RequestParam(name = "userId") int userId) {
        return ResponseEntity.ok(controller.queue(userId));
    }

}
