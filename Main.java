import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProductoDAO dao = new ProductoDAO();
        int opcion = 0;

        do {
            System.out.println("\n=== SISTEMA DE GESTIÓN DE PRODUCTOS (SQLITE) ===");
            System.out.println("1. Registrar producto");
            System.out.println("2. Consultar productos");
            System.out.println("3. Actualizar producto");
            System.out.println("4. Eliminar producto");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); 

                switch (opcion) {
                    case 1:
                        System.out.print("Código: ");
                        String codigo = scanner.nextLine();
                        System.out.print("Nombre: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Descripción: ");
                        String descripcion = scanner.nextLine();
                        
                        double precio = -1;
                        while (precio < 0) {
                            System.out.print("Precio (>= 0): ");
                            if (scanner.hasNextDouble()) {
                                precio = scanner.nextDouble();
                                if (precio < 0) System.out.println("El precio no puede ser negativo.");
                            } else {
                                System.out.println("Valor inválido.");
                                scanner.next();
                            }
                        }

                        int cantidad = -1;
                        while (cantidad < 0) {
                            System.out.print("Cantidad (entero >= 0): ");
                            if (scanner.hasNextInt()) {
                                cantidad = scanner.nextInt();
                                if (cantidad < 0) System.out.println("La cantidad no puede ser negativa.");
                            } else {
                                System.out.println("Valor inválido.");
                                scanner.next();
                            }
                        }
                        scanner.nextLine();

                        Producto nuevo = new Producto(codigo, nombre, descripcion, precio, cantidad);
                        if (dao.insertar(nuevo)) {
                            System.out.println("¡Producto registrado con éxito!");
                        }
                        break;

                    case 2:
                        List<Producto> productos = dao.listar();
                        if (productos.isEmpty()) {
                            System.out.println("No hay productos registrados.");
                        } else {
                            System.out.println("\n--- LISTA DE PRODUCTOS ---");
                            for (Producto p : productos) {
                                System.out.println(p);
                            }
                        }
                        break;

                    case 3:
                        System.out.print("Ingrese el código del producto a actualizar: ");
                        String codAct = scanner.nextLine();
                        System.out.print("Nueva descripción: ");
                        String nuevaDesc = scanner.nextLine();
                        
                        double nuevoPrecio = -1;
                        while (nuevoPrecio < 0) {
                            System.out.print("Nuevo precio (>= 0): ");
                            if (scanner.hasNextDouble()) {
                                nuevoPrecio = scanner.nextDouble();
                                if (nuevoPrecio < 0) System.out.println("El precio no puede ser negativo.");
                            } else {
                                System.out.println("Valor inválido.");
                                scanner.next();
                            }
                        }

                        int nuevaCant = -1;
                        while (nuevaCant < 0) {
                            System.out.print("Nueva cantidad (entero >= 0): ");
                            if (scanner.hasNextInt()) {
                                nuevaCant = scanner.nextInt();
                                if (nuevaCant < 0) System.out.println("La cantidad no puede ser negativa.");
                            } else {
                                System.out.println("Valor inválido.");
                                scanner.next();
                            }
                        }
                        scanner.nextLine();

                        if (dao.actualizar(codAct, nuevaDesc, nuevoPrecio, nuevaCant)) {
                            System.out.println("¡Producto actualizado correctamente!");
                        } else {
                            System.out.println("No se encontró un producto con ese código.");
                        }
                        break;

                    case 4:
                        System.out.print("Ingrese el código del producto a eliminar: ");
                        String codDel = scanner.nextLine();
                        System.out.print("¿Está seguro de eliminar este producto? (s/n): ");
                        String confirmacion = scanner.nextLine();

                        if (confirmacion.equalsIgnoreCase("s")) {
                            if (dao.eliminar(codDel)) {
                                System.out.println("¡Producto eliminado exitosamente!");
                            } else {
                                System.out.println("No se encontró el producto.");
                            }
                        } else {
                            System.out.println("Operación cancelada.");
                        }
                        break;

                    case 5:
                        System.out.println("Saliendo del programa...");
                        break;

                    default:
                        System.out.println("Opción inválida.");
                }
            } else {
                System.out.println("Ingrese un número válido.");
                scanner.next();
            }
        } while (opcion != 5);
        scanner.close();
    }
}