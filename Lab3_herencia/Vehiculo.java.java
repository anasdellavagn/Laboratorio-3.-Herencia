package modelo;

public class Vehiculo {
    private String placa;
    private double capacidadMax;
    private boolean refrigerado;

    public Vehiculo(String placa, double capacidadMax, boolean refrigerado) {
        if (placa == null || placa.trim().isEmpty()) {
            throw new IllegalArgumentException("La placa no puede estar vacía.");
        }
        if (capacidadMax <= 0 || Double.isInfinite(capacidadMax) || Double.isNaN(capacidadMax)) {
            throw new IllegalArgumentException("La capacidad máxima debe ser un número positivo y finito.");
        }
        this.placa = placa.trim();
        this.capacidadMax = capacidadMax;
        this.refrigerado = refrigerado;
    }

    public String getPlaca() {
        return placa;
    }

    public double getCapacidadMax() {
        return capacidadMax;
    }

    public boolean isRefrigerado() {
        return refrigerado;
    }
}