package com.haroldohenrique.revenda_motos.modules.cliente.services;

import java.util.Optional;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.haroldohenrique.revenda_motos.exceptions.MotoNotFoundException;
import com.haroldohenrique.revenda_motos.exceptions.UserNotFoundException;
import com.haroldohenrique.revenda_motos.modules.cliente.model.ApplyMotoEntity;
import com.haroldohenrique.revenda_motos.modules.cliente.model.ClienteEntity;
import com.haroldohenrique.revenda_motos.modules.cliente.repository.ApplyMotoRepository;
import com.haroldohenrique.revenda_motos.modules.cliente.repository.ClienteRepository;
import com.haroldohenrique.revenda_motos.modules.loja.models.MotoEntity;
import com.haroldohenrique.revenda_motos.modules.loja.repositories.MotoRepository;

@ExtendWith(MockitoExtension.class)
public class ApplyMotoClienteUseCaseTest {

    // nao deve ser possível se aplicar a uma moto se o cliente não existir

    @InjectMocks
    private ApplyMotoClienteUseCase applyMotoClienteUseCase;

    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private MotoRepository motoRepository;

    @Mock
    private ApplyMotoRepository applyMotoRepository;

    @Test
    @DisplayName("Should not be able to apply moto with cliente not found")
    public void should_not_be_apply_jon_with_candidate_not_found() {
        try {
            applyMotoClienteUseCase.execute(null, null);
        } catch (Exception ex) {
            assertThat(ex).isInstanceOf(UserNotFoundException.class);
        }
    }

    @Test
    @DisplayName("Should not be able to apply moto with moto not found")
    public void should_not_be_able_to_apply_moto_with_moto_not_found() {
        var clienteId = UUID.randomUUID();
        var cliente = new ClienteEntity();
        cliente.setId(clienteId);
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        try {
            applyMotoClienteUseCase.execute(clienteId, null);
        } catch (Exception ex) {
            assertThat(ex).isInstanceOf(MotoNotFoundException.class);
        }
    }

    @Test
    @DisplayName("Should be able to create a new apply moto")
    public void should_be_able_to_create_a_new_apply_moto() {
        //TODO tentar entender esse código de merda
        var clienteId = UUID.randomUUID();
        var motoId = UUID.randomUUID();
        var applyMoto = ApplyMotoEntity.builder().clienteId(clienteId).motoId(motoId).build();
        var applyMotoCreatedId = ApplyMotoEntity.builder().id(UUID.randomUUID()).build();

        //quando ele procurar pelo o ID ele instanciará uma nova entidade de cliente
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(new ClienteEntity()));
        when(motoRepository.findById(motoId)).thenReturn(Optional.of(new MotoEntity()));
        when(applyMotoRepository.save(applyMoto)).thenReturn(applyMotoCreatedId);
        
        
        var result = applyMotoClienteUseCase.execute(clienteId, motoId);
        assertThat(result).hasFieldOrProperty("id");
        assertNotNull(result.getId());
        //TODO devo arrumar o textnotes do testes java
    }
}