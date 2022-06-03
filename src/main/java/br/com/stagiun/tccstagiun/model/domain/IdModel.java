package br.com.stagiun.tccstagiun.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The type Audit model.
 *
 * @author 5P-ADS
 *
 */
@Data
@NoArgsConstructor
@MappedSuperclass
@JsonInclude(JsonInclude.Include.NON_NULL)
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt, updatedAt"}, allowGetters = true, ignoreUnknown = true)
public abstract class IdModel implements Serializable {

    private static final long serialVersionUID = 3661315543425633373L;


    /*@NotNull
    @Column(name = "created_date", nullable = false)
    @JsonIgnore
    @CreatedDate
    @ApiModelProperty(notes = "createdDate")
    private LocalDateTime createdDate = DateUtil.convert(new Date());

    @NotNull
    @Column(name = "create_by")
    @JsonIgnore
    @CreatedBy
    @ApiModelProperty(notes = "createBy")
    private String createBy = Constants.CURRENT_USER;

    @Column(name = "last_modified_date")
    @JsonIgnore
    @LastModifiedDate
    @Getter
    @Setter
    @ApiModelProperty(notes = "lastModifiedDate")
    private LocalDateTime lastModifiedDate;

    @Column(name = "last_modified_by")
    @JsonIgnore
    @LastModifiedBy
    @Getter
    @Setter
    @ApiModelProperty(notes = "lastModifiedBy")
    private String lastModifiedBy;*/


    @Column(name = "ultima_alteracao")
    @JsonIgnore
    @LastModifiedDate
    @Getter
    @Setter
    private LocalDateTime lastModifiedDate = LocalDateTime.now();

}
