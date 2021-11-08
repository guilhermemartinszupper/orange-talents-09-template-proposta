package br.com.zupedu.gui.desafioproposta.proposta;

import br.com.zupedu.gui.desafioproposta.proposta.analise.StatusProposta;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.Cartao;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {
    Optional<Proposta> findByDocumentoHash(String documentoHash);

    List<Proposta> findAllByStatusProposta(StatusProposta emAnalise, Pageable page);

    List<Proposta> findAllByStatusPropostaAndCartao(StatusProposta Status, Cartao cartao, Pageable page);
}
