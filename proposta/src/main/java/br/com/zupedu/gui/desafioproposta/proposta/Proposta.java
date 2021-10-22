package br.com.zupedu.gui.desafioproposta.proposta;

import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Entity
public class Proposta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String documento;
    @NotBlank @Email
    private String email;
    @NotBlank
    private String nome;
    @NotBlank
    private String endereco;
    @NotNull
    @PositiveOrZero
    private BigDecimal salarioBruto;

    @Deprecated
    public Proposta() {
    }

    public Proposta(String documento, String email, String nome, String endereco, BigDecimal salarioBruto) {
        Assert.isTrue(documento != null && documento.length() > 0, "Documento nao pode ser vazio e nem nulo");
        Assert.isTrue(email != null && email.length() > 0, "Email nao pode ser vazio e nem nulo");
        Assert.isTrue(nome != null && nome.length() > 0, "Nome nao pode ser vazio e nem nulo");
        Assert.isTrue(endereco != null && endereco.length() > 0, "Endereco nao pode ser vazio e nem nulo");
        Assert.isTrue(salarioBruto != null && salarioBruto.compareTo(BigDecimal.ZERO) > -1, "Salario nao pode ser nulo nem negativo");
        this.documento = documento;
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
}
