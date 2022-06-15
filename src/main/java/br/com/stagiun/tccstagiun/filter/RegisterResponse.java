package br.com.stagiun.tccstagiun.filter;

import br.com.stagiun.tccstagiun.model.domain.Usuario;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterResponse {
    private Long id;
    private Usuario usuario;
}
