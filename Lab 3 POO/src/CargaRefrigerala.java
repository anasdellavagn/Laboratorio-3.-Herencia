public class CargaRefrigerada extends Envio {

    private double temperatura;

    public CargaRefrigerada(String codigo, String remitente,
                            String destinatario, double pesoReal,
                            String tipoDestino, double temperatura) {

        super(codigo, remitente, destinatario,
              pesoReal, tipoDestino);

        if (!Double.isFinite(temperatura) ||
            temperatura < -25 || temperatura > 8) {
            throw new IllegalArgumentException(
                "La temperatura debe estar entre -25 y 8 grados"
            );
        }

        if (!tipoDestino.equalsIgnoreCase("Urbano")) {
            throw new IllegalArgumentException(
                "La carga refrigerada solo permite destino urbano"
            );
        }

        this.temperatura = temperatura;
    }

    @Override
    public double calcularCosto() {
        double tarifaBase = 25.0;
        double costoPeso = getPesoReal() * 12.0;
        double cadenaFrio = 60.0;

        return tarifaBase + costoPeso + cadenaFrio;
    }

    @Override
    public int estimarEntrega() {
        return 1;
    }

    @Override
    public boolean esCompatible(Vehiculo vehiculo) {
        return vehiculo != null && vehiculo.isRefrigerado();
    }

    @Override
    public int maxIntentos() {
        return 1;
    }

    @Override
    public String getCategoria() {
        return "Carga refrigerada";
    }
}
