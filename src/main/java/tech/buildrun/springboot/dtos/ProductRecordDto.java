package tech.buildrun.springboot.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) para a entidade Product.
 *
 * <p>Esta classe é usada para transferir dados relacionados a produtos entre
 * diferentes camadas da aplicação. Utiliza o recurso de `record` do Java para
 * fornecer uma implementação imutável e concisa.</p>
 *
 * <p>Validações são aplicadas aos campos para garantir que os dados estejam
 * corretos antes de serem processados.</p>
 *
 * @param name  o nome do produto, não pode ser nulo ou vazio
 * @param value o valor do produto, não pode ser nulo
 */
public record ProductRecordDto(@NotBlank String name, @NotNull BigDecimal value) {
}
