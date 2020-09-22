
package sa.m.builders.api.client;

import sa.m.builders.api.client.model.CPF;
import sa.m.builders.api.client.model.PessoaFisica;
import java.util.Calendar;
import java.util.TimeZone;

/**
 *
 * @author msa
 */
public enum PessoaFisicaSample {
    PEDRO(new PessoaFisica(new CPF("74427647545"), "Pedro", cal(2012, 1, 1))),
    PEDRO_ALVARES(new PessoaFisica(new CPF("42578844100"), "Pedro Álvares", cal(2001, 1, 27))),
    ANDRE_DAS_NEVES(new PessoaFisica(new CPF("77635188712"), "André das Neves", cal(2013, 5, 31))),
    RICARDO_MAGNO_SOBRINHO(new PessoaFisica(new CPF("28138459816"), "Ricardo Magno Sobrinho", cal(1987, 6, 17))),
    CARLOS_X_1(new PessoaFisica(new CPF("83332945963"), "Carlos X", cal(2008, 2, 15))),
    CARLOS_X_2(new PessoaFisica(new CPF("42651664154"), "Carlos X", cal(2010, 3, 22))), // HOMÔNIMO
    REGINA(new PessoaFisica(new CPF("854.189.671-42"), "Regina", cal(2010, 3, 22))),
    ALBERTO(new PessoaFisica(new CPF("437.853.919-51"), "Alberto", cal(1975, 2, 3))),
    XAVIER(new PessoaFisica(new CPF("478.447.743-80"), "Xavier", cal(2012, 1, 1))),
    ROBERTO(new PessoaFisica(new CPF("495.683.242-75"), "Roberto", cal(2001, 1, 27))),
    ADRIANA(new PessoaFisica(new CPF("623.692.659-06"), "Adriana", cal(2013, 5, 31))),
    CAROL(new PessoaFisica(new CPF("817.238.162-05"), "Carol", cal(1987, 6, 17))),
    JOANA(new PessoaFisica(new CPF("853.283.834-00"), "Joana", cal(1970, 5, 4))),
    AUGUSTO(new PessoaFisica(new CPF("684.792.573-69"), "Augusto", cal(1898, 12, 30))), // TOO OLD
    NAME_BAD_1(new PessoaFisica(new CPF("981.673.254-82"), "F00", cal(2001, 1, 27))), // BAD NAME INVALID CHAR
    NAME_BAD_2(new PessoaFisica(new CPF("441.312.992-08"), "R", cal(2001, 1, 27))), // BAD NAME TOO SHORT
    NAME_BAD_3(new PessoaFisica(new CPF("944.582.080-04"), "Kaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", cal(2013, 05, 31))), // BAD NAME TOO LONG
    TADEU(new PessoaFisica(new CPF("581.189.150-42"), "Tadeu", cal(1898, 12, 30))), // TOO OLD
    TAMARA(new PessoaFisica(new CPF("241.111.593-89"), "Tamara", Calendar.getInstance()))
    ;

    public static Calendar cal(int year, int month, int day) {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        return cal;
    }
    
    private final PessoaFisica pessoa;

    private PessoaFisicaSample(PessoaFisica pessoa) {
        this.pessoa = pessoa;
    }
    
    public PessoaFisica instance() {
        return pessoa.copy();
    }
    
    public String nome() {
        return pessoa.getNome();
    }
    
    public String cpf() {
        return pessoa.getCpf();
    }
    
    public Calendar dataNascimento() {
        return pessoa.getDataNascimento();
    }
     
    public int idade() {
        return pessoa.getIdade();
    }
    
}
