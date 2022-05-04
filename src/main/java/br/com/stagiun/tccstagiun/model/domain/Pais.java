package br.com.stagiun.tccstagiun.model.domain;

import lombok.*;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.*;

@Entity
@Table(name = "pais", schema = "sis_vaga")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(callSuper = true, of = { "description" })
@Builder
@Data
public class Pais extends IdModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    public void update(Long id, Pais pais) {
        this.id = id;
        this.descricao = pais.getDescricao();
    }

}
