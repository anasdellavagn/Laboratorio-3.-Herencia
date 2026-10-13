public class PaqueteFragil extends Paquete {

    private double valorDeclarado;

    public PaqueteFragil(String codigo, String remitente,
                         String destinatario, double pesoReal,
                         String tipoDestino, double largo,
                         double ancho, double alto,
                         double valorDeclarado) {

        super(codigo, remitente, destinatario,
              pesoReal, tipoDestino, largo, ancho, alto);

        if (!Double.isFinite(valorDeclarado) ||
            valorDeclarado <= 0) {
            throw new IllegalArgumentException(
                "El valor declarado debe ser positivo"
            );
        }

        this.valorDeclarado = valorDeclarado;
    }

    @Override
    public double calcularCosto() {
        double costoBase = super.calcularCosto();
        double embalaje = 15.0;
        double seguro = valorDeclarado * 0.03;

        return costoBase + embalaje + seguro;
    }

    @Override
    public int estimarEntrega() {
        return super.estimarEntrega() + 1;
    }

    @Override
    public int maxIntentos() {
        return 2;
    }

    @Override
    public String getCategoria() {
        return "Paquete fragil";
    }
}
