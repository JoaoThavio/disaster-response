package com.github.joao.disaster_response.domain.service;


import com.github.joao.disaster_response.domain.model.Ocorrencia;
import com.github.joao.disaster_response.domain.model.enums.StatusOcorrencia;
import com.github.joao.disaster_response.domain.repository.OcorrenciaRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class OcorrenciaService {

    private final OcorrenciaRepository repository;
    private final SimpMessagingTemplate messagingTemplate;

    public OcorrenciaService(OcorrenciaRepository repository, SimpMessagingTemplate simpMessagingTemplate) {
        this.repository = repository;
        this.messagingTemplate = simpMessagingTemplate;
    }

    public Ocorrencia criar(Ocorrencia ocorrencia){
        Ocorrencia salva = repository.save(ocorrencia);
        messagingTemplate.convertAndSend("/topic/ocorrencias", salva);

        return salva;
    }

    public Ocorrencia atualizarStatus(Long id, StatusOcorrencia novoStatus){
        Ocorrencia ocorrencia = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ocorrência não encontrada"));
        StatusOcorrencia statusAtual = ocorrencia.getStatus();

        boolean transicaoInvalida =
                (statusAtual == StatusOcorrencia.ENCERRADA) ||
                        (statusAtual == StatusOcorrencia.ATIVA && novoStatus == StatusOcorrencia.ENCERRADA) ||
                        (statusAtual == null && novoStatus == StatusOcorrencia.ENCERRADA);

        if (transicaoInvalida) {
            throw new RuntimeException("Transição de status inválida: " + statusAtual + " → " + novoStatus);
        }

        ocorrencia.setStatus(novoStatus);
        Ocorrencia atualizada = repository.save(ocorrencia);
        messagingTemplate.convertAndSend("/topic/ocorrencias", atualizada);
        return atualizada;

    }
}
