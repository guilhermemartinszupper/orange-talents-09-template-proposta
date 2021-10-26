package br.com.zupedu.gui.desafioproposta.proposta;

import br.com.zupedu.gui.desafioproposta.proposta.analise.StatusProposta;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Proposta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false)
    private String documento;
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

    @Deprecated
    public Proposta() {
    }

    public Proposta(String documento, String email, String nome, String endereco, BigDecimal salarioBruto) {
        Assert.isTrue(documento != null && documento.length() > 0, "Documento nao pode ser vazio e nem nulo");
        Assert.isTrue(email != null && email.length() > 0, "Email nao pode ser vazio e nem nulo");
        Assert.isTrue(nome != null && nome.length() > 0, "Nome nao pode ser vazio e nem nulo");
        Assert.isTrue(endereco != null && endereco.length() > 0, "Endereco nao pode ser vazio e nem nulo");
        Assert.isTrue(salarioBruto != null && salarioBruto.compareTo(BigDecimal.ZERO) > -1, "Salario nao pode ser nulo nem negativo");
        this.documento = documento.replaceAll("[^a-zA-Z0-9]", "");
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salarioBruto = salarioBruto;
    }

    public Long getId() {
        return id;
    }

    public String getDocumento() {
        return documento;
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

    public void setStatusProposta(StatusProposta statusProposta) {
        this.statusProposta = statusProposta;
    }

    @Override
    public String toString() {
        return "Proposta{" +
                "id=" + id +
                ", documento='" + documento + '\'' +
                ", email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", endereco='" + endereco + '\'' +
                ", salarioBruto=" + salarioBruto +
                ", statusProposta=" + statusProposta +
                '}';
    }
}
