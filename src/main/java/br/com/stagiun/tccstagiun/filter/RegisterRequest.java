package br.com.stagiun.tccstagiun.filter;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterRequest {
    private String nome;
    private String email;
    private String senha;
}
