package controlador;

import modelo.*;
import vista.Vista;

import java.util.ArrayList;
import java.util.HashMap;

public class Controlador {
    private Vista vista;
    private ArrayList<Envio> envios;
    private ArrayList<Vehiculo> vehiculos;
    private ArrayList<Despacho> despachos;
    private int siguienteNumeroDespacho;
    private HashMap<String, Double> ingresosRegistroPorCategoria;
    private HashMap<String, Double> ingresosReintentoPorCategoria;

    public Controlador(Vista vista) {
        if (vista == null) {
            throw new IllegalArgumentException("La vista no puede ser nula.");
        }
        this.vista = vista;
        this.envios = new ArrayList<>();
        this.vehiculos = new ArrayList<>();
        this.despachos = new ArrayList<>();
        this.siguienteNumeroDespacho = 1;
        this.ingresosRegistroPorCategoria = new HashMap<>();
        this.ingresosReintentoPorCategoria = new HashMap<>();
    }

    public void iniciar() {
        cargarDatosIniciales();
        int opcion = -1;
        do {
            try {
                vista.mostrarMenu();
                opcion = vista.leerEntero("Seleccione una opción");
                switch (opcion) {
                    case 1:
                        cotizarEnvio();
                        break;
                    case 2:
                        registrarEnvio();
                        break;
                    case 3:
                        registrarVehiculo();
                        break;
                    case 4:
                        consultarEnvios();
                        break;
                    case 5:
                        consultarVehiculos();
                        break;
                    case 6:
                        crearDespacho();
                        break;
                    case 7:
                        asignarEnvio();
                        break;
                    case 8:
                        retirarEnvio();
                        break;
                    case 9:
                        salirDespacho();
                        break;
                    case 10:
                        registrarResultadoEntrega();
                        break;
                    case 11:
                        mostrarReportes();
                        break;
                    case 0:
                        vista.mostrarMensaje("Saliendo del sistema...");
                        break;
                    default:
                        vista.mostrarMensaje("Opción inválida.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                vista.mostrarMensaje("Error de operación: " + e.getMessage());
            } catch (Exception e) {
                vista.mostrarMensaje("Ocurrió un error inesperado: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void cargarDatosIniciales() {
        try {
            vehiculos.add(new Vehiculo("V001", 500.0, false));
            vehiculos.add(new Vehiculo("V002", 300.0, true));
            vehiculos.add(new Vehiculo("V003", 1000.0, false));

            envios.add(new Sobre("S001", "Ana Gómez", "Carlos Pérez", 0.3, "Urbano"));
            envios.add(new Sobre("S002", "Juan Ruiz", "María López", 0.4, "Departamental"));
            envios.add(new Paquete("P001", "Empresa A", "Cliente B", 5.0, "Urbano", 30, 30, 30));
            envios.add(new CargaRefrigerada("CR001", "Farmacia X", "Hospital Y", 15.0, "Urbano", 4.0));
        } catch (Exception ignored) {
        }
    }

    private Envio buscarEnvio(String codigo) {
        for (Envio e : envios) {
            if (e.getCodigo().equalsIgnoreCase(codigo)) {
                return e;
            }
        }
        return null;
    }

    private Vehiculo buscarVehiculo(String placa) {
        for (Vehiculo v : vehiculos) {
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                return v;
            }
        }
        return null;
    }

    private Despacho buscarDespacho(int numero) {
        for (Despacho d : despachos) {
            if (d.getNumero() == numero) {
                return d;
            }
        }
        return null;
    }

    private boolean vehiculoDisponible(Vehiculo vehiculo) {
        for (Despacho d : despachos) {
            if (d.getVehiculo().equals(vehiculo) && !d.estaFinalizado()) {
                return false;
            }
        }
        return true;
    }

    private Envio solicitarDatosEnvio() {
        String codigo = vista.leerString("Código del envío");
        if (buscarEnvio(codigo) != null) {
            throw new IllegalArgumentException("Ya existe un envío registrado con ese código.");
        }
        String remitente = vista.leerString("Remitente");
        String destinatario = vista.leerString("Destinatario");
        double pesoReal = vista.leerDouble("Peso real (kg)");
        String tipoDestino = vista.leerString("Tipo de destino (Urbano / Departamental)");

        vista.mostrarMensaje("Seleccione categoría de envío:");
        vista.mostrarMensaje("1. Sobre");
        vista.mostrarMensaje("2. Paquete estándar");
        vista.mostrarMensaje("3. Paquete frágil");
        vista.mostrarMensaje("4. Carga refrigerada");
        int catOpt = vista.leerEntero("Opción");

        switch (catOpt) {
            case 1:
                return new Sobre(codigo, remitente, destinatario, pesoReal, tipoDestino);
            case 2:
                double largo = vista.leerDouble("Largo (cm)");
                double ancho = vista.leerDouble("Ancho (cm)");
                double alto = vista.leerDouble("Alto (cm)");
                return new Paquete(codigo, remitente, destinatario, pesoReal, tipoDestino, largo, ancho, alto);
            case 3:
                double l = vista.leerDouble("Largo (cm)");
                double a = vista.leerDouble("Ancho (cm)");
                double al = vista.leerDouble("Alto (cm)");
                double valorDecl = vista.leerDouble("Valor declarado");
                return new PaqueteFragil(codigo, remitente, destinatario, pesoReal, tipoDestino, l, a, al, valorDecl);
            case 4:
                double temp = vista.leerDouble("Temperatura requerida (°C)");
                return new CargaRefrigerada(codigo, remitente, destinatario, pesoReal, tipoDestino, temp);
            default:
                throw new IllegalArgumentException("Categoría de envío no válida.");
        }
    }

    private void cotizarEnvio() {
        Envio envioTemp = solicitarDatosEnvio();
        double costo = envioTemp.calcularCosto();
        int dias = envioTemp.estimarEntrega();
        vista.mostrarMensaje("--- COTIZACIÓN ---");
        vista.mostrarMensaje("Categoría: " + envioTemp.getCategoria());
        vista.mostrarMensaje("Costo estimado: Q" + costo);
        vista.mostrarMensaje("Tiempo estimado: " + dias + " días hábiles.");
    }

    private void registrarEnvio() {
        Envio nuevoEnvio = solicitarDatosEnvio();
        double costo = nuevoEnvio.calcularCosto();
        int dias = nuevoEnvio.estimarEntrega();

        vista.mostrarMensaje("Costo del envío: Q" + costo + " | Tiempo estimado: " + dias + " días.");
        if (vista.confirmar("¿Confirma registrar este envío?")) {
            envios.add(nuevoEnvio);
            String cat = nuevoEnvio.getCategoria();
            ingresosRegistroPorCategoria.put(cat, ingresosRegistroPorCategoria.getOrDefault(cat, 0.0) + costo);
            vista.mostrarMensaje("Envío registrado exitosamente en bodega.");
        } else {
            vista.mostrarMensaje("Registro cancelado.");
        }
    }

    private void registrarVehiculo() {
        String placa = vista.leerString("Placa del vehículo");
        if (buscarVehiculo(placa) != null) {
            throw new IllegalArgumentException("Ya existe un vehículo registrado con esta placa.");
        }
        double capacidad = vista.leerDouble("Capacidad máxima (kg)");
        boolean refrigerado = vista.leerBoolean("¿Cuenta con refrigeración?");

        Vehiculo v = new Vehiculo(placa, capacidad, refrigerado);
        vehiculos.add(v);
        vista.mostrarMensaje("Vehículo registrado exitosamente.");
    }

    private void consultarEnvios() {
        if (envios.isEmpty()) {
            vista.mostrarMensaje("No hay envíos registrados.");
            return;
        }
        vista.mostrarMensaje("--- LISTA DE ENVÍOS ---");
        for (Envio e : envios) {
            vista.mostrarMensaje("Código: " + e.getCodigo() + " | Cat: " + e.getCategoria() + 
                               " | Estado: " + e.getEstado() + " | Intentos: " + e.getIntentos());
        }
    }

    private void consultarVehiculos() {
        if (vehiculos.isEmpty()) {
            vista.mostrarMensaje("No hay vehículos registrados.");
            return;
        }
        vista.mostrarMensaje("--- LISTA DE VEHÍCULOS ---");
        for (Vehiculo v : vehiculos) {
            boolean disponible = vehiculoDisponible(v);
            vista.mostrarMensaje("Placa: " + v.getPlaca() + " | Capacidad: " + v.getCapacidadMax() + 
                               "kg | Refrigerado: " + (v.isRefrigerado() ? "Sí" : "No") + 
                               " | Disponible: " + (disponible ? "Sí" : "No"));
        }
    }

    private void crearDespacho() {
        String placa = vista.leerString("Placa del vehículo para el despacho");
        Vehiculo v = buscarVehiculo(placa);
        if (v == null) {
            throw new IllegalArgumentException("Vehículo no encontrado.");
        }
        if (!vehiculoDisponible(v)) {
            throw new IllegalStateException("El vehículo seleccionado ya cuenta con un despacho sin finalizar.");
        }

        Despacho despacho = new Despacho(siguienteNumeroDespacho++, v);
        despachos.add(despacho);
        vista.mostrarMensaje("Despacho #" + despacho.getNumero() + " creado exitosamente en preparación.");
    }

    private void asignarEnvio() {
        int numDespacho = vista.leerEntero("Número de despacho");
        Despacho d = buscarDespacho(numDespacho);
        if (d == null) {
            throw new IllegalArgumentException("Despacho no encontrado.");
        }

        String codigoEnvio = vista.leerString("Código del envío a asignar");
        Envio e = buscarEnvio(codigoEnvio);
        if (e == null) {
            throw new IllegalArgumentException("Envío no encontrado.");
        }

        d.asignarEnvio(e);
        vista.mostrarMensaje("Envío asignado correctamente al despacho.");
    }

    private void retirarEnvio() {
        int numDespacho = vista.leerEntero("Número de despacho");
        Despacho d = buscarDespacho(numDespacho);
        if (d == null) {
            throw new IllegalArgumentException("Despacho no encontrado.");
        }

        String codigoEnvio = vista.leerString("Código del envío a retirar");
        Envio e = buscarEnvio(codigoEnvio);
        if (e == null) {
            throw new IllegalArgumentException("Envío no encontrado.");
        }

        d.retirarEnvio(e);
        vista.mostrarMensaje("Envío retirado del despacho y devuelto a bodega.");
    }

    private void salirDespacho() {
        int numDespacho = vista.leerEntero("Número de despacho");
        Despacho d = buscarDespacho(numDespacho);
        if (d == null) {
            throw new IllegalArgumentException("Despacho no encontrado.");
        }

        double cargos = d.salir();
        for (Envio e : d.getEnvios()) {
            if (e.getIntentos() > 1) {
                String cat = e.getCategoria();
                ingresosReintentoPorCategoria.put(cat, ingresosReintentoPorCategoria.getOrDefault(cat, 0.0) + 15.0);
            }
        }

        vista.mostrarMensaje("Despacho puesto en ruta exitosamente. Cargos por reintentos generados: Q" + cargos);
    }

    private void registrarResultadoEntrega() {
        int numDespacho = vista.leerEntero("Número de despacho");
        Despacho d = buscarDespacho(numDespacho);
        if (d == null) {
            throw new IllegalArgumentException("Despacho no encontrado.");
        }

        String codigoEnvio = vista.leerString("Código del envío");
        Envio e = buscarEnvio(codigoEnvio);
        if (e == null) {
            throw new IllegalArgumentException("Envío no encontrado.");
        }

        boolean entregado = vista.leerBoolean("¿Fue entregado exitosamente?");
        d.registrarResultado(e, entregado);
        vista.mostrarMensaje("Resultado registrado correctamente.");
        if (d.estaFinalizado()) {
            vista.mostrarMensaje("¡El despacho #" + d.getNumero() + " ha sido finalizado!");
        }
    }

    private void mostrarReportes() {
        int opcion;
        do {
            vista.mostrarMenuReportes();
            opcion = vista.leerEntero("Seleccione una opción de reporte");
            switch (opcion) {
                case 1:
                    reporteEnvios();
                    break;
                case 2:
                    reporteIngresos();
                    break;
                case 3:
                    reporteDespachosActivos();
                    break;
                case 0:
                    break;
                default:
                    vista.mostrarMensaje("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void reporteEnvios() {
        vista.mostrarMensaje("--- REPORTE DE ENVÍOS ---");
        HashMap<String, Integer> conteoCat = new HashMap<>();
        HashMap<String, Integer> conteoEstado = new HashMap<>();

        for (Envio e : envios) {
            conteoCat.put(e.getCategoria(), conteoCat.getOrDefault(e.getCategoria(), 0) + 1);
            String est = e.getEstado().name();
            conteoEstado.put(est, conteoEstado.getOrDefault(est, 0) + 1);
        }

        vista.mostrarMensaje("Por Categoría:");
        for (String cat : conteoCat.keySet()) {
            vista.mostrarMensaje(" - " + cat + ": " + conteoCat.get(cat));
        }

        vista.mostrarMensaje("Por Estado:");
        for (String est : conteoEstado.keySet()) {
            vista.mostrarMensaje(" - " + est + ": " + conteoEstado.get(est));
        }
    }

    private void reporteIngresos() {
        vista.mostrarMensaje("--- REPORTE DE INGRESOS ---");
        double totalReg = 0;
        double totalReint = 0;

        vista.mostrarMensaje("Ingresos por Registro:");
        for (String cat : ingresosRegistroPorCategoria.keySet()) {
            double val = ingresosRegistroPorCategoria.get(cat);
            totalReg += val;
            vista.mostrarMensaje(" - " + cat + ": Q" + val);
        }
        vista.mostrarMensaje("Total Registros: Q" + totalReg);

        vista.mostrarMensaje("Ingresos por Reintentos:");
        for (String cat : ingresosReintentoPorCategoria.keySet()) {
            double val = ingresosReintentoPorCategoria.get(cat);
            totalReint += val;
            vista.mostrarMensaje(" - " + cat + ": Q" + val);
        }
        vista.mostrarMensaje("Total Reintentos: Q" + totalReint);
        vista.mostrarMensaje("TOTAL GENERAL: Q" + (totalReg + totalReint));
    }

    private void reporteDespachosActivos() {
        vista.mostrarMensaje("--- DESPACHOS ACTIVOS ---");
        boolean hayActivos = false;
        for (Despacho d : despachos) {
            if (!d.estaFinalizado()) {
                hayActivos = true;
                vista.mostrarMensaje("Número: " + d.getNumero() + " | Vehículo: " + d.getVehiculo().getPlaca() + 
                                   " | Estado: " + d.getEstado() + " | Peso cargado: " + d.calcularPesoCargado() + 
                                   "kg | Capacidad disp: " + d.calcularCapacidadDisponible() + "kg");
            }
        }
        if (!hayActivos) {
            vista.mostrarMensaje("No hay despachos activos en este momento.");
        }
    }
}