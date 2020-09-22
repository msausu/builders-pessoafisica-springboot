package sa.m.builders.api.client.model;



import sa.m.builders.api.client.converter.CPFConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author msa
 * 
 * cpf deve ser único mas nomes e dataNascimento podem ser iguais (homônimos, mesmo dia de nascimento)
 */
@Entity
@Table(name = "PESSOA_FISICA", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"cpf"})})
public class PessoaFisica implements Serializable {
    public static final int
                PESSOA_FISICA_NAME_MIN = 2, PESSOA_FISICA_NAME_MAX = 50, PESSOA_FISICA_IDADE_MAX = 120;
    public static final String PAT_NOME = "(\\p{javaUpperCase}\\p{javaLowerCase}*)",
            PAT_DE_DO_DA = "(d(e|((o|a)s?)) )?",
            PAT_NOME_MEIO = "( " + PAT_DE_DO_DA + PAT_NOME + ")*",
            PAT_NOME_COMPLETO = "^" + PAT_NOME + PAT_NOME_MEIO + "$";
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @NotNull
    @Column(name="cpf")
    @Convert(converter = CPFConverter.class)
    private CPF cpf;

    @NotNull
    @Column(name="nome")
    @Size(min = PESSOA_FISICA_NAME_MIN, max = PESSOA_FISICA_NAME_MAX)
    @Pattern(regexp = PAT_NOME_COMPLETO)
    private String nome;

    @Column(name="nascimento")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", locale = "pt-BR", timezone = "Brazil/East")
    @PastOrPresent
    private java.util.Calendar dataNascimento;

    @Transient 
    private int idade;

    public PessoaFisica() {
    }

    public PessoaFisica(CPF cpf, String nome, Calendar dataNascimento) {
        this.cpf = cpf;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }
    
    public Long getId() {
        return id;
    }

    public String getCpf() {
        return cpf.print();
    }

    public void setCpf(CPF cpf) {
        this.cpf = cpf;
    }
    
    public void setCpf(String cpf) {
        this.cpf = new CPF(cpf);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome.trim();
    }

    public Calendar getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Calendar dataNascimento) {
        this.dataNascimento = dataNascimento;
        if (getIdade() > PESSOA_FISICA_IDADE_MAX) throw new IllegalArgumentException("idade inválida");
    }

    public int getIdade() {
        Instant now = Instant.now(), before = dataNascimento.toInstant();
        return (int)ChronoUnit.YEARS.between(
                before.atZone(ZoneId.systemDefault()), 
                now.atZone(ZoneId.systemDefault()));
    }

    @Override
    public int hashCode() {
        final long num = cpf.asNumber();
        int hash = 7;
        hash = 79 * hash + (int) (num ^ (num >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PessoaFisica other = (PessoaFisica) obj;
        if (this.cpf != other.cpf) {
            return false;
        }
        return true;
    }

    @JsonIgnore
    public PessoaFisica copy() {
        return new PessoaFisica(cpf.copy(), nome, dataNascimento);
    }
}
