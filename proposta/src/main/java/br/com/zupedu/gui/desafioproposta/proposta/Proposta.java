package br.com.zupedu.gui.desafioproposta.proposta;

import br.com.zupedu.gui.desafioproposta.commons.encrypter.Encriptador;
import br.com.zupedu.gui.desafioproposta.proposta.analise.StatusProposta;
import br.com.zupedu.gui.desafioproposta.proposta.cartao.Cartao;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Proposta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false)
    @Convert(converter = Encriptador.class)
    private String documentoCriptografado;
    @Column(unique = true,nullable = false)
    private String documentoHash;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String endereco;
    @Column(nullable = false)
    private BigDecimal salarioBruto;
    @Enumerated(EnumType.STRING)
    private StatusProposta statusProposta;
    @OneToOne(cascade = CascadeType.ALL)
    private Cartao cartao;

    @Deprecated
    public Proposta() {
    }

    public Proposta(String documento, String email, String nome, String endereco, BigDecimal salarioBruto) {
        Assert.isTrue(documento != null && documento.length() > 0, "Documento nao pode ser vazio e nem nulo");
        Assert.isTrue(email != null && email.length() > 0, "Email nao pode ser vazio e nem nulo");
        Assert.isTrue(nome != null && nome.length() > 0, "Nome nao pode ser vazio e nem nulo");
        Assert.isTrue(endereco != null && endereco.length() > 0, "Endereco nao pode ser vazio e nem nulo");
        Assert.isTrue(salarioBruto != null && salarioBruto.compareTo(BigDecimal.ZERO) > -1, "Salario nao pode ser nulo nem negativo");
        String documentoLimpo = documento.replaceAll("[^a-zA-Z0-9]", "");
        this.documentoCriptografado = documentoLimpo;
        this.documentoHash = Encriptador.hashEncode(documentoLimpo);
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salarioBruto = salarioBruto;
    }

    public Long getId() {
        return id;
    }

    public String getDocumentoCriptografado() {
        return documentoCriptografado;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalarioBruto() {
        return salarioBruto;
    }

    public StatusProposta getStatusProposta() {
        return statusProposta;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void setStatusProposta(StatusProposta statusProposta) {
        this.statusProposta = statusProposta;
    }

    public String getDocumentoHash() {
        return documentoHash;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }
}
