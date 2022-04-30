package br.com.stagiun.tccstagiun.model.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tipo_empresa", schema = "sis_vaga")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(callSuper = true, of = { "description" })
@Builder
@Data
public class TipoEmpresa extends IdModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;
}