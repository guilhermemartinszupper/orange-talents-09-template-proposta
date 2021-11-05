package br.com.zupedu.gui.desafioproposta.proposta.cartao;

import br.com.zupedu.gui.desafioproposta.handler.ApiBussinesException;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.biometria.Biometria;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.carteiraDigital.CarteiraDigital;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.carteiraDigital.NomeCarteira;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.viagem.AvisoViagem;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cartao {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numeroCartao;
    private LocalDateTime emitidoEm;
    private String titular;
    private Integer limite;
    @OneToMany(mappedBy = "cartao",cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
    private List<Biometria> biometrias = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private StatusCartao statusCartao;
    @OneToMany(mappedBy = "cartao",cascade = CascadeType.ALL)
    private List<AvisoViagem> avisosViagems = new ArrayList<>();
    @OneToMany(mappedBy = "cartao",cascade = CascadeType.ALL)
    private List<CarteiraDigital> carteirasDigitais = new ArrayList<>();

    @Deprecated
    public Cartao() {
    }

    public Cartao(String numeroCartao, LocalDateTime emitidoEm, String titular, Integer limite) {
        Assert.isTrue(numeroCartao != null && !numeroCartao.isEmpty(),"numeroCartao nao pode ser null nem vazio");
        Assert.isTrue(titular != null && !titular.isEmpty(),"titular nao pode ser null nem vazio");
        Assert.notNull(emitidoEm,"emitido em nao pode ser null");
        Assert.isTrue(limite != null && limite >= 0,"Limite nao pode ser null nem negativo");
        this.numeroCartao = numeroCartao;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.limite = limite;
        this.statusCartao = StatusCartao.ATIVO;
    }

    public Long getId() {
        return id;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public String getTitular() {
        return titular;
    }

    public Integer getLimite() {
        return limite;
    }

    public List<Biometria> getBiometrias() {
        return biometrias;
    }

    public Biometria getBiometria(Biometria biometria){
        int p = biometrias.lastIndexOf(biometria);
        return biometrias.get(p);
    }

    public StatusCartao getStatusCartao() {
        return statusCartao;
    }

    public CarteiraDigital getUltimaCarteira() {
        return this.carteirasDigitais.get(carteirasDigitais.size() - 1);
    }

    public List<CarteiraDigital> getCarteirasDigitais() {
        return carteirasDigitais;
    }

    public String ofuscaNumeroCartao() {
        return numeroCartao.substring(numeroCartao.length() - 4);
    }

    public void adicionaBiometria(Biometria biometria) {
        Assert.notNull(biometria, "biometria nao pode ser null");
        biometrias.add(biometria);
    }

    public void bloquear() {
        if(this.statusCartao.equals(StatusCartao.BLOQUEADO)){
            throw new ApiBussinesException("Bloqueio","Cartao Ja Esta Bloqueado",HttpStatus.UNPROCESSABLE_ENTITY);
        }
        this.statusCartao = StatusCartao.BLOQUEADO;
    }

    public void adicionaAvisoViagem(AvisoViagem avisoViagem) {
        Assert.notNull(avisoViagem, "AvisoViagem nao pode ser null");
        this.avisosViagems.add(avisoViagem);
    }

    public List<AvisoViagem> getAvisosViagems() {
        return avisosViagems;
    }

    public AvisoViagem getUltimaViagem(){
        return this.avisosViagems.get(avisosViagems.size() - 1);
    }

    public void associaCarteira(CarteiraDigital carteiraDigital) {
        if(jaPossuiEssaCarteira(carteiraDigital.getNomeCarteira())){
            throw new ApiBussinesException("Carteira Digital","Esse Cartao Ja Associado a Essa Carteira Digital", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        this.carteirasDigitais.add(carteiraDigital);
    }

    public boolean jaPossuiEssaCarteira(NomeCarteira carteira) {
        return this.carteirasDigitais.stream().anyMatch(carteiraDigital -> carteiraDigital.getNomeCarteira().equals(carteira));
    }
}
