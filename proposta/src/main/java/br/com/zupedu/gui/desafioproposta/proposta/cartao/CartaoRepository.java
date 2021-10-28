package br.com.zupedu.gui.desafioproposta.proposta.cartao;

import br.com.zupedu.gui.desafioproposta.proposta.cartao.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {
}
