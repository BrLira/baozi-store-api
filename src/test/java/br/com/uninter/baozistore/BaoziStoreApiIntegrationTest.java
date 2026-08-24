package br.com.uninter.baozistore;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class BaoziStoreApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveExecutarFluxoCompletoDeClienteProdutoEPedido() throws Exception {
        String clienteJson = """
                {"nome":"AdautoBrenne5048958","clienteDesde":"2026-08-24"}
                """;
        String clienteResposta = mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clienteJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("AdautoBrenne5048958"))
                .andReturn().getResponse().getContentAsString();
        JsonNode cliente = objectMapper.readTree(clienteResposta);

        String produtoJson = """
                {"nome":"Baozi Tradicional","preco":8.50,"estoque":true}
                """;
        String produtoResposta = mockMvc.perform(post("/api/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(produtoJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Baozi Tradicional"))
                .andReturn().getResponse().getContentAsString();
        JsonNode produto = objectMapper.readTree(produtoResposta);

        String pedidoJson = String.format(
                "{\"clienteId\":%d,\"produtoId\":%d,\"quantidade\":6}",
                cliente.get("id").asLong(), produto.get("id").asLong());
        String pedidoResposta = mockMvc.perform(post("/api/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pedidoJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.quantidade").value(6))
                .andExpect(jsonPath("$.cliente.nome").value("AdautoBrenne5048958"))
                .andReturn().getResponse().getContentAsString();
        JsonNode pedido = objectMapper.readTree(pedidoResposta);

        mockMvc.perform(get("/api/clientes/{id}", cliente.get("id").asLong()))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Baozi Tradicional"));
        mockMvc.perform(get("/api/pedidos/{id}", pedido.get("id").asLong()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.produto.nome").value("Baozi Tradicional"));

        mockMvc.perform(delete("/api/pedidos/{id}", pedido.get("id").asLong()))
                .andExpect(status().isNoContent());
        mockMvc.perform(delete("/api/produtos/{id}", produto.get("id").asLong()))
                .andExpect(status().isNoContent());
        mockMvc.perform(delete("/api/clientes/{id}", cliente.get("id").asLong()))
                .andExpect(status().isNoContent());
    }
}
