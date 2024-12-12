package sistemaBancario;

public class ValidadorCPF {

    public static boolean validarCPF(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            return false;
        }

        try {
            long CPF = Long.parseLong(cpf);
            long DI = CPF / 100;
            long DV = CPF % 100;
            long quebra1 = 0, soma1 = 0, resto1, DV1;
            long quebra2 = 0, soma2 = 0, resto2, DV2, AUX;

            for (int i = 2; i <= 10; i++) {
                quebra1 = (DI % 10) * i;
                DI = DI / 10;
                soma1 = soma1 + quebra1;
            }

            resto1 = soma1 % 11;
            DV1 = 11 - resto1;
            if (DV1 == 10 || DV1 == 11) {
                DV1 = 0;
            }

            DI = CPF / 100;
            AUX = DI * 10 + DV1;

            for (int i = 2; i <= 11; i++) {
                quebra2 = (AUX % 10) * i;
                AUX = AUX / 10;
                soma2 = soma2 + quebra2;
            }

            resto2 = soma2 % 11;
            DV2 = 11 - resto2;
            if (DV2 == 10 || DV2 == 11) {
                DV2 = 0;
            }

            return DV1 * 10 + DV2 == DV;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

