package modelo;

import java.util.ArrayList;

public class Despacho {
    private int numero;
    private Vehiculo vehiculo;
    private ArrayList<Envio> envios;
    private String estado;
    private ArrayList<Envio> enviosConResultado;

    public Despacho(int numero, Vehiculo vehiculo) {
        if (numero <= 0) {
            throw new IllegalArgumentException("El número de despacho debe ser positivo.");
        }
        if (vehiculo == null) {
            throw new IllegalArgumentException("El vehículo no puede ser nulo.");
        }
        this.numero = numero;
        this.vehiculo = vehiculo;
        this.envios = new ArrayList<>();
        this.enviosConResultado = new ArrayList<>();
        this.estado = "En preparación";
    }

    public int getNumero() {
        return numero;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public ArrayList<Envio> getEnvios() {
        return new ArrayList<>(envios); // Devuelve una copia de la lista
    }

    public String getEstado() {
        return estado;
    }

    public double calcularPesoCargado() {
        double pesoTotal = 0;
        for (Envio e : envios) {
            pesoTotal += e.getPesoReal();
        }
        return pesoTotal;
    }

    public double calcularCapacidadDisponible() {
        return vehiculo.getCapacidadMax() - calcularPesoCargado();
    }

    public void asignarEnvio(Envio envio) {
        if (!"En preparación".equals(estado)) {
            throw new IllegalStateException("Solo se pueden asignar envíos cuando el despacho está en preparación.");
        }
        if (envio == null) {
            throw new IllegalArgumentException("El envío no puede ser nulo.");
        }
        if (!Estado.BODEGA.equals(envio.getEstado())) {
            throw new IllegalStateException("El envío debe estar en BODEGA para ser asignado.");
        }
        if (envios.contains(envio)) {
            throw new IllegalStateException("El envío ya está asignado a este despacho.");
        }
        if (!envio.esCompatible(vehiculo)) {
            throw new IllegalArgumentException("El envío no es compatible con el vehículo asignado.");
        }
        if (envio.getPesoReal() > calcularCapacidadDisponible()) {
            throw new IllegalArgumentException("El peso del envío excede la capacidad disponible del vehículo.");
        }

        envios.add(envio);
        envio.marcarAsignado();
    }

    public void retirarEnvio(Envio envio) {
        if (!"En preparación".equals(estado)) {
            throw new IllegalStateException("Solo se pueden retirar envíos cuando el despacho está en preparación.");
        }
        if (!envios.contains(envio)) {
            throw new IllegalArgumentException("El envío no forma parte de este despacho.");
        }

        envios.remove(envio);
        envio.regresarBodega();
    }

    public double salir() {
        if (!"En preparación".equals(estado)) {
            throw new IllegalStateException("El despacho no está en preparación.");
        }
        if (envios.isEmpty()) {
            throw new IllegalStateException("No se puede poner en ruta un despacho vacío.");
        }

        this.estado = "En ruta";
        double cargosReintento = 0;

        for (Envio e : envios) {
            e.registrarSalida();
            if (e.getIntentos() > 1) {
                cargosReintento += 15.0; // Q15 por cada intento adicional al primero tras salir
            }
        }

        return cargosReintento;
    }

    public void registrarResultado(Envio envio, boolean entregado) {
        if (!"En ruta".equals(estado)) {
            throw new IllegalStateException("El despacho no se encuentra en ruta.");
        }
        if (!envios.contains(envio)) {
            throw new IllegalArgumentException("El envío no pertenece a este despacho.");
        }
        if (enviosConResultado.contains(envio)) {
            throw new IllegalStateException("El resultado de este envío ya fue registrado en este recorrido.");
        }

        envio.registrarResultado(entregado);
        enviosConResultado.add(envio);

        if (todosConResultado()) {
            this.estado = "Finalizado";
        }
    }

    private boolean todosConResultado() {
        return enviosConResultado.size() == envios.size();
    }

    public boolean estaFinalizado() {
        return "Finalizado".equals(estado);
    }
}