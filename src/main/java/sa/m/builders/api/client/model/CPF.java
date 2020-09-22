
package sa.m.builders.api.client.model;

/**
 *
 * @author msa
 */
import com.fasterxml.jackson.annotation.JsonIgnore;
import static sa.m.builders.api.client.model.CPF.Format.DEFAULT;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 *
 * @author msa
 *
 */
public final class CPF {

    private static final int[] WEIGHTS_11 = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2},
            WEIGHTS_10 = Arrays.copyOfRange(WEIGHTS_11, 1, 10);
    private static final String PAT_D2 = "[0-9]{2}", PAT_D3 = "[0-9]{3}",
            PAT_SEP_1 = "([._]?)", PAT_SEP_2 = "[-/]?",
            PAT_CPF = PAT_D3 + PAT_SEP_1 + PAT_D3 + "\\1" + PAT_D3 + PAT_SEP_2 + PAT_D2;
    private static final String[] INVALID_CPF = {"00000000000", "11111111111",
        "22222222222", "33333333333", "44444444444", "55555555555",
        "66666666666", "77777777777", "88888888888", "99999999999"
    };

    public enum Format {
        DEFAULT, MASK_1, MASK_2
    };
    public final String CPF;

    /**
     *
     * @param cpf  um cpf (String) com ou sem pontuação
     * @throws IllegalArgumentException se o cpf é inválido
     */
    public CPF(final String cpf) {
        this(cpf, true);
    }
    
        /**
     *
     * @param cpf  um cpf (String) com ou sem pontuação
     * @param validate se pcpf necessita validacao
     * @throws IllegalArgumentException se o cpf é inválido
     */
    public CPF(final String cpf, boolean validate) {
        if (!validate) {
            this.CPF = cpf;
        } else {
            this.CPF = unPunctuate(cpf);
            if (!isValid(this.CPF)) {
                throw new IllegalArgumentException();
            }
        }
    }

    public long asNumber() {
        return Long.parseLong(CPF);
    }

    public static String unPunctuate(final String cpf) throws IllegalArgumentException {
        if (cpf == null || !cpf.trim().matches(PAT_CPF)) {
            throw new IllegalArgumentException();
        }
        return cpf.trim().replace(".", "").replace("_", "").replace("/", "").replace("-", "");
    }

    /**
     *
     * @param cpf  um cpf (String) com ou sem pontuação
     * @return     booleano indicando se o cpf é válido
     */
    public static boolean isValid(final String cpf) {
        for (String invalid : INVALID_CPF) {
            if (cpf.equals(invalid)) {
                return false;
            }
        }
        final String CPF_1_a_9 = cpf.substring(0, 9);
        final int CPF_10 = Integer.parseInt(cpf.substring(9, 10));
        final int CPF_11 = Integer.parseInt(cpf.substring(10, 11));
        final int DV_10 = calcDV(CPF_1_a_9, WEIGHTS_10);
        final int DV_11 = calcDV(CPF_1_a_9 + DV_10, WEIGHTS_11);
        return CPF_10 == DV_10 && CPF_11 == DV_11;
    }

    private static int calcDV(final String str, int[] weights) {
        int sum = innerProduct(digits(str), weights);
        sum = 11 - (sum % 11);
        return sum > 9 ? 0 : sum;
    }

    private static int[] digits(final String str) {
        return Arrays.stream(str.split("")).mapToInt(Integer::parseInt).toArray();
    }

    private static int innerProduct(int[] a, int[] b) {
        if (a == null || b == null || a.length > b.length) {
            throw new IllegalArgumentException();
        }
        return IntStream.range(0, a.length).reduce(0, (ip, i) -> ip + a[i] * b[i]);
    }

    public String print() {
        return print(DEFAULT);
    }

    /**
     *
     * @param format formato (enum) da máscara de impressão
     * @return       cpf (string) formatado para impressão
     */
    public String print(Format format) {
        String p1 = CPF.substring(0, 3), p2 = CPF.substring(3, 6), p3 = CPF.substring(6, 9), p4 = CPF.substring(9, 11);
        switch (format) {
            case MASK_1:
                return p1 + "_" + p2 + "_" + p3 + "-" + p4;
            case MASK_2:
                return p1 + "." + p2 + "." + p3 + "/" + p4;
            case DEFAULT:
            default:
                return p1 + "." + p2 + "." + p3 + "-" + p4;
        }
    }

    @Override
    public String toString() {
        return CPF;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.CPF);
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
        final CPF other = (CPF) obj;
        return Objects.equals(this.CPF, other.CPF);
    }

    @JsonIgnore
    protected CPF copy() {
        return new CPF(CPF, false);
    }

}
