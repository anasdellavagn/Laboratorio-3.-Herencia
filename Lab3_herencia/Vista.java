package vista;

import java.util.Scanner;

public class Vista {
    private Scanner scanner;

    public Vista() {
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        System.out.println("\n===== SISTEMA DE ENVÍOS EnviaGT =====");
        System.out.println("1. Cotizar envío");
        System.out.println("2. Registrar nuevo envío");
        System.out.println("3. Registrar nuevo vehículo");
        System.out.println("4. Consultar envíos");
        System.out.println("5. Consultar vehículos");
        System.out.println("6. Crear despacho");
        System.out.println("7. Asignar envío a despacho");
        System.out.println("8. Retirar envío de despacho");
        System.out.println("9. Poner despacho en ruta");
        System.out.println("10. Registrar resultado de entrega");
        System.out.println("11. Ver reportes");
        System.out.println("0. Salir");
    }

    public void mostrarMenuReportes() {
        System.out.println("\n--- MENÚ DE REPORTES ---");
        System.out.println("1. Reporte de envíos (por categoría y estado)");
        System.out.println("2. Reporte de ingresos (registros y reintentos)");
        System.out.println("3. Reporte de despachos activos");
        System.out.println("0. Volver al menú principal");
    }

    public String leerString(String mensaje) {
        String input;
        while (true) {
            System.out.print(mensaje + ": ");
            input = scanner.nextLine();
            if (input != null) {
                input = input.trim();
                if (!input.isEmpty()) {
                    return input;
                }
            }
            System.out.println("Error: El texto no puede estar vacío. Intente de nuevo.");
        }
    }

    public int leerEntero(String mensaje) {
        while (true) {
            try {
                String entrada = leerString(mensaje);
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Error: Formato de número entero inválido. Intente de nuevo.");
            }
        }
    }

    public double leerDouble(String mensaje) {
        while (true) {
            try {
                String entrada = leerString(mensaje);
                double valor = Double.parseDouble(entrada);
                if (Double.isNaN(valor) || Double.isInfinite(valor)) {
                    System.out.println("Error: El valor no es un número finito válido.");
                    continue;
                }
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Error: Formato numérico inválido. Intente de nuevo.");
            }
        }
    }

    public boolean leerBoolean(String mensaje) {
        while (true) {
            String respuesta = leerString(mensaje + " (S/N)").toUpperCase();
            if (respuesta.equals("S") || respuesta.equals("SI") || respuesta.equals("TRUE")) {
                return true;
            } else if (respuesta.equals("N") || respuesta.equals("NO") || respuesta.equals("FALSE")) {
                return false;
            }
            System.out.println("Error: Respuesta no válida. Ingrese S o N.");
        }
    }

    public boolean confirmar(String mensaje) {
        return leerBoolean(mensaje);
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}