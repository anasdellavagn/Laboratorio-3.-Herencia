public abstract class Envio {

    private String codigo;
    private String remitente;
    private String destinatario;
    private double pesoReal;
    private String tipoDestino;
    private Estado estado;
    private int intentos;

    protected Envio(String codigo, String remitente,
                    String destinatario, double pesoReal,
                    String tipoDestino) {

        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("Codigo invalido");
        }

        if (remitente == null || remitente.trim().isEmpty()) {
            throw new IllegalArgumentException("Remitente invalido");
        }

        if (destinatario == null || destinatario.trim().isEmpty()) {
            throw new IllegalArgumentException("Destinatario invalido");
        }

        if (!Double.isFinite(pesoReal) || pesoReal <= 0) {
            throw new IllegalArgumentException("Peso invalido");
        }

        if (tipoDestino == null ||
            (!tipoDestino.equalsIgnoreCase("Urbano") &&
             !tipoDestino.equalsIgnoreCase("Departamental"))) {
            throw new IllegalArgumentException("Destino invalido");
        }

        this.codigo = codigo.trim();
        this.remitente = remitente.trim();
        this.destinatario = destinatario.trim();
        this.pesoReal = pesoReal;
        this.tipoDestino = tipoDestino.trim();
        this.estado = Estado.BODEGA;
        this.intentos = 0;
    }

    public abstract double calcularCosto();

    public abstract int estimarEntrega();

    public abstract int maxIntentos();

    public abstract String getCategoria();

    public boolean esCompatible(Vehiculo vehiculo) {
        return vehiculo != null;
    }

    public String getCodigo() {
        return codigo;
    }

    public double getPesoReal() {
        return pesoReal;
    }

    public String getDestino() {
        return tipoDestino;
    }

    public Estado getEstado() {
        return estado;
    }

    public int getIntentos() {
        return intentos;
    }

    public void asignarDespacho() {
        if (estado != Estado.BODEGA) {
            throw new IllegalStateException(
                "El envio no esta disponible en bodega"
            );
        }

        if (intentos >= maxIntentos()) {
            throw new IllegalStateException(
                "El envio ya agoto sus intentos"
            );
        }

        estado = Estado.ASIGNADO;
    }

    public void registrarSalida() {
        if (estado != Estado.ASIGNADO) {
            throw new IllegalStateException(
                "El envio no esta asignado"
            );
        }

        if (intentos >= maxIntentos()) {
            throw new IllegalStateException(
                "No quedan intentos disponibles"
            );
        }

        intentos++;
        estado = Estado.EN_RUTA;
    }

    public void registrarResultado(boolean entregado) {
        if (estado != Estado.EN_RUTA) {
            throw new IllegalStateException(
                "El envio no esta en ruta"
            );
        }

        if (entregado) {
            estado = Estado.ENTREGADO;
        } else if (intentos >= maxIntentos()) {
            estado = Estado.DEVUELTO;
        } else {
            estado = Estado.BODEGA;
        }
    }

    public void regresarBodega() {
        if (estado != Estado.ASIGNADO) {
            throw new IllegalStateException(
                "El envio no puede regresar a bodega"
            );
        }

        estado = Estado.BODEGA;
    }
}
