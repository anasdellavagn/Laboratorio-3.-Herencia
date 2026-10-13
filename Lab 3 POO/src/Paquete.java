package modelo;

public class Paquete extends Envio {

    private double largo;
    private double ancho;
    private double alto;

    public Paquete(String codigo, String remitente,
                   String destinatario, double pesoReal,
                   String tipoDestino, double largo,
                   double ancho, double alto) {

        super(codigo, remitente, destinatario,
              pesoReal, tipoDestino);

        if (!Double.isFinite(largo) || largo <= 0 ||
            !Double.isFinite(ancho) || ancho <= 0 ||
            !Double.isFinite(alto) || alto <= 0) {

            throw new IllegalArgumentException(
                "Las dimensiones deben ser positivas"
            );
        }

        this.largo = largo;
        this.ancho = ancho;
        this.alto = alto;
    }

    public double calcularPesoVolu() {
        return (largo * ancho * alto) / 5000.0;
    }

    public double calcularPesoFac() {
        double pesoMayor = Math.max(
            getPesoReal(), calcularPesoVolu()
        );

        return Math.ceil(pesoMayor);
    }

    @Override
    public double calcularCosto() {
        double tarifaBase;

        if (getDestino().equalsIgnoreCase("Urbano")) {
            tarifaBase = 25.0;
        } else {
            tarifaBase = 40.0;
        }

        return tarifaBase + (8.0 * calcularPesoFac());
    }

    @Override
    public int estimarEntrega() {
        if (getDestino().equalsIgnoreCase("Urbano")) {
            return 2;
        } else {
            return 4;
        }
    }

    @Override
    public int maxIntentos() {
        return 3;
    }

    @Override
    public String getCategoria() {
        return "Paquete";
    }
}
