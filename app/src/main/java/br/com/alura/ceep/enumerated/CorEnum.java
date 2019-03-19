package br.com.alura.ceep.enumerated;

public enum CorEnum {

    AZUL("#408EC9"),
    BRANCO("#FFFFFF"),
    VERMELHO("#EC2F4B"),
    VERDE("#9ACD32"),
    AMARELO("#F9F256"),
    LILAS("#F1CBFF"),
    CINZA("#D2D4DC"),
    MARROM("#A47C48"),
    ROXO("#BE29EC");

    private String corHexa;

    CorEnum(String corHexa) {
        this.corHexa = corHexa;
    }

    public static CorEnum valueOfCorHexa(String corHexa) {
        for (CorEnum corEnum :
            CorEnum.values()) {
            if (corEnum.corHexa.equals(corHexa))
                return corEnum;
        }

        return CorEnum.BRANCO;
    }

    public String getCorHexa() {
        return corHexa;
    }

}
