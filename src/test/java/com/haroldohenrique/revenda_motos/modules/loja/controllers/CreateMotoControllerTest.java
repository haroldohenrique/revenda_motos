package com.haroldohenrique.revenda_motos.modules.loja.controllers;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.haroldohenrique.revenda_motos.modules.loja.dto.CreateMotoDTO;
import com.haroldohenrique.revenda_motos.modules.loja.models.LojaEntity;
import com.haroldohenrique.revenda_motos.modules.loja.models.MotoEntity.TipoMoto;
import com.haroldohenrique.revenda_motos.modules.loja.repositories.LojaRepository;
import com.haroldohenrique.revenda_motos.utils.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateMotoControllerTest {
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private LojaRepository lojaRepository;

    // Aqui está criando uma estrutura para rodar o teste
    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    public void should_be_able_to_create_a_new_moto() throws Exception {

        var createdLojaEntity = LojaEntity.builder()
                .cnpj("12345678912345")
                .email("lojanova@gmail.com")
                .name("loja maneira")
                .username("lojalogin")
                .password("123456789")
                .build();
        createdLojaEntity = lojaRepository.saveAndFlush(createdLojaEntity);

        var createdMotoDTO = CreateMotoDTO.builder()
                .ano(2015)
                .tipo(TipoMoto.CARENADA)
                .name("cbr fireblade")
                .build();

        var result = mvc.perform(MockMvcRequestBuilders.post("/loja/moto/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJSON(createdMotoDTO))
                .header("Authorization",
                        TestUtils.generatedToken(createdLojaEntity.getId(),
                                "w!z%C*F-JaNdRgUkXp2s5v8y/B?E(H+KbPeShVmYq3t6w9z$C&F)J@NcQfTjWnZr")))
                .andExpect(MockMvcResultMatchers.status().isOk());

        System.out.println(result);
    }

    @Test
    public void should_not_be_able_to_create_a_new_moto_if_loja_not_found() throws Exception{
        var createdMotoDTO = CreateMotoDTO.builder()
                .ano(2015)
                .tipo(TipoMoto.CARENADA)
                .name("cbr fireblade")
                .build();

                mvc.perform(MockMvcRequestBuilders.post("/loja/moto/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtils.objectToJSON(createdMotoDTO))
                            .header("Authorization",
                                    TestUtils.generatedToken(UUID.randomUUID(),
                                            "w!z%C*F-JaNdRgUkXp2s5v8y/B?E(H+KbPeShVmYq3t6w9z$C&F)J@NcQfTjWnZr")))
                                            .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }
}
