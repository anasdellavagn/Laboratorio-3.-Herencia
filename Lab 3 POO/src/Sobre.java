package modelo;

public class Sobre extends Envio {

    public Sobre(String codigo, String remitente,
                 String destinatario, double pesoReal,
                 String tipoDestino) {

        super(codigo, remitente, destinatario,
              pesoReal, tipoDestino);

        if (pesoReal > 0.5) {
            throw new IllegalArgumentException(
                "El sobre no puede pesar mas de 0.5 kg"
            );
        }
    }

    @Override
    public double calcularCosto() {
        if (getDestino().equalsIgnoreCase("Urbano")) {
            return 20.0;
        } else {
            return 35.0;
        }
    }

    @Override
    public int estimarEntrega() {
        if (getDestino().equalsIgnoreCase("Urbano")) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public int maxIntentos() {
        return 3;
    }

    @Override
    public String getCategoria() {
        return "Sobre";
    }
}
