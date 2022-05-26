package br.com.stagiun.tccstagiun.model.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cargo", schema = "sis_vaga")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(callSuper = true, of = {"description"})
@Builder
@Data
public class Cargo extends IdModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @Column
    private String experiencia;

    @Column
    private String area_atuacao;

    @Column
    private String beneficios;

    @Column
    private BigDecimal salario;

    @Column
    private String habilidades_desejadas;

    @Column
    private String competencias_desejadas;

    public void update(Long id, Cargo cargo) {
        this.id = id;
        this.descricao = cargo.getDescricao();
        this.experiencia = cargo.getExperiencia();
        this.area_atuacao = cargo.getArea_atuacao();
        this.beneficios = cargo.getBeneficios();
        this.salario = cargo.getSalario();
        this.habilidades_desejadas = cargo.getHabilidades_desejadas();
        this.competencias_desejadas = cargo.getCompetencias_desejadas();
    }

}
