package com.haroldohenrique.revenda_motos.modules.cliente.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.haroldohenrique.revenda_motos.modules.cliente.dto.ClienteDTO;
import com.haroldohenrique.revenda_motos.modules.cliente.dto.CreateClienteDTO;
import com.haroldohenrique.revenda_motos.modules.cliente.model.ApplyMotoEntity;
import com.haroldohenrique.revenda_motos.modules.cliente.services.ApplyMotoClienteUseCase;
import com.haroldohenrique.revenda_motos.modules.cliente.services.CreateClienteUseCase;
import com.haroldohenrique.revenda_motos.modules.cliente.services.ListAllMotosFilterUseCase;
import com.haroldohenrique.revenda_motos.modules.cliente.services.ProfileClienteUseCase;
import com.haroldohenrique.revenda_motos.modules.loja.models.MotoEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cliente")
@Tag(name = "Cliente", description = "Informações do cliente")
public class ClienteController {
    @Autowired
    private CreateClienteUseCase createClienteUseCase;

    @Autowired
    private ProfileClienteUseCase profileClienteUseCase;

    @Autowired
    private ListAllMotosFilterUseCase listAllMotosFilterUseCase;

    @Autowired
    private ApplyMotoClienteUseCase applyMotoClienteUseCase;

    // @Autowired
    // private SearchClienteUseCase searchClienteUseCase;

    @PostMapping("/")
    @Operation(summary = "Criar o perfil do cliente", description = "Essa função faz a criação do perfil do cliente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = CreateClienteDTO.class))
            }),
            @ApiResponse(responseCode = "409", description = "Conflit. User already exists")
    })
    public ResponseEntity<Object> create(@Valid @RequestBody CreateClienteDTO createClienteDTO) {
        try {
            var result = createClienteUseCase.execute(createClienteDTO);
            return ResponseEntity.ok().body(result);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('CLIENTE')")
    @Operation(summary = "Perfil do cliente", description = "Retorna o perfil do cliente com base no id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ClienteDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> get(HttpServletRequest request) {
        var clienteId = request.getAttribute("cliente_id");
        try {
            var clienteDTO = this.profileClienteUseCase.execute(UUID.fromString(clienteId.toString()));
            return ResponseEntity.ok().body(clienteDTO);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    // @GetMapping("/")
    // public ResponseEntity<Object> getAll() {
    // try {
    // var clientes = searchClienteUseCase.getAllExecute();
    // return ResponseEntity.ok().body(clientes);

    // } catch (Exception ex) {
    // return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    // }
    // }

    // @GetMapping("/search")
    // public ResponseEntity<Object> getById(@RequestParam String email) {
    // try {
    // var cliente = searchClienteUseCase.getByEmailExecute(email);
    // return ResponseEntity.ok().body(cliente);

    // } catch (Exception ex) {
    // return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    // }
    // }

    @GetMapping("/moto")
    @PreAuthorize("hasRole('CLIENTE')")
    @Operation(summary = "Listagem de motos disponíveis", description = "Essa função retorna todas as motos disponíveis com base no filtro passado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = MotoEntity.class)))
            }),@ApiResponse(responseCode = "404", description = "Moto not found.")
    })
    @SecurityRequirement(name = "jwt_auth")
    public List<MotoEntity> findMotoByFilter(@RequestParam String filter) {
        return this.listAllMotosFilterUseCase.execute(filter);
    }

    @PostMapping("/moto/apply")
    @PreAuthorize("hasRole('CLIENTE')")
    @Operation(summary = "Aplicar o cliente em uma moto", description = "Essa função aplica o cliente em uma moto.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ApplyMotoEntity.class))
            }),
            @ApiResponse(responseCode = "404", description = "Moto Not found")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> applyMoto(HttpServletRequest request, @RequestBody UUID motoId) {
        var clienteId = request.getAttribute("cliente_id");
        try {
            var result = this.applyMotoClienteUseCase.execute(UUID.fromString(clienteId.toString()), motoId);
            return ResponseEntity.ok().body(result);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
