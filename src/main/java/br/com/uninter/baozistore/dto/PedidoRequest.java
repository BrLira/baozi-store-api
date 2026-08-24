package br.com.uninter.baozistore.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PedidoRequest(
        @NotNull(message = "O clienteId e obrigatorio") Long clienteId,
        @NotNull(message = "O produtoId e obrigatorio") Long produtoId,
        @NotNull(message = "A quantidade e obrigatoria")
        @Positive(message = "A quantidade deve ser maior que zero") Integer quantidade) {
}
