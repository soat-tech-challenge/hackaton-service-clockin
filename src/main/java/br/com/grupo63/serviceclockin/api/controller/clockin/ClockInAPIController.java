package br.com.grupo63.serviceclockin.api.controller.clockin;

import br.com.grupo63.serviceclockin.controller.ClockInController;
import br.com.grupo63.serviceclockin.controller.dto.ClockInControllerDTO;
import br.com.grupo63.serviceclockin.controller.dto.ClockinGroupedByDateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ClockIn", description = "Saves clock in data.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class ClockInAPIController {

    private final ClockInController controller;

    @Operation(
            summary = "Registra o ponto do usuario",
            description = "Seria utilizado registrar o ponto do usuario em questao")
    @PostMapping
    public ResponseEntity<ClockInControllerDTO> queue(@RequestParam(name = "userId") int userId) {
        return ResponseEntity.ok(controller.queue(userId));
    }

    @Operation(
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping("/user/clock-ins")
    public ResponseEntity<List<ClockinGroupedByDateDTO>> listByUser(HttpServletRequest request) {
        return ResponseEntity.ok(controller.listByUser(Integer.parseInt((String) request.getAttribute("userId"))));
    }

    @Operation(
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PostMapping("/user/report")
    public ResponseEntity<String> generateReport(HttpServletRequest request) {
        int userId = Integer.parseInt((String) request.getAttribute("userId"));
        String userEmail = (String) request.getAttribute("userEmail");

        controller.generateReport(userId, userEmail);

        return ResponseEntity.ok("Um relatório foi enviado ao e-mail: " + userEmail);
    }

}
