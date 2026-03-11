package com.github.joao.disaster_response.domain.model;

import com.github.joao.disaster_response.domain.model.enums.StatusOcorrencia;
import com.github.joao.disaster_response.domain.model.enums.TipoOcorrencia;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Ocorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    @NotBlank
    private String titulo;

    @Column
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column
    @NotNull
    private TipoOcorrencia tipo;

    @Enumerated(EnumType.STRING)
    @Column
    private StatusOcorrencia status;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp

    private LocalDateTime criadoEm;

    @Column
    @Min(1)
    @Max(5)
    private Integer gravidade;
}
