package Vista;

import Exceptions.ErrorTipoCuentaYMonedaException;
import Exceptions.NoExisteSucursalException;
import constants.TipoCuenta;
import constants.TipoMoneda;
import java.time.LocalDate;
import java.sql.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import modelo.Banco;
import modelo.CajaDeAhorro;
import modelo.Cliente;
import modelo.Cuenta;
import modelo.CuentaCorriente;
import modelo.Empleado;
import modelo.Sucursal;
import servicios.BancoService;
import servicios.ClienteService;
import servicios.CuentaService;
import servicios.EmpleadoService;
import servicios.IBancoService;
import servicios.IClienteService;
import servicios.ICuentaService;
import servicios.IEmpleadoService;
import servicios.ISucursalService;
import servicios.SucursalService;

/**
 * Sistema Bancario realizado en la capacitacion brindada por UTN.
 * @author Matias Alancay
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) {
        IBancoService bancoService = new BancoService();
        IEmpleadoService empleadoService = new EmpleadoService();
        IClienteService clienteService = new ClienteService();
        ICuentaService cuentaService = new CuentaService();
        ISucursalService sucursalService = new SucursalService();
        boolean error = true;

        int opcion;
        Scanner input = new Scanner(System.in);        
        Banco banco = bancoService.buscarBancoPorId(1);//fijo el primer banco como banco a usar        

        do {
            try {
                do {
                    mostrarMenuPrincipal();
                    opcion = input.nextInt();
                    switch (opcion) {
                        case 1:
                            agregarCliente(banco, sucursalService, clienteService, cuentaService, input);
                            break;
                        case 2:
                            agregarCuentaACliente(banco, clienteService, cuentaService, input);
                            break;
                        case 3:
                            listarClientesPorSucursales(banco, sucursalService);
                            break;
                        case 4:
                            listarClientesDeUnaSucursal(banco, sucursalService, input);
                            break;
                        case 5:
                            extraerDinero(banco, clienteService, cuentaService, input);
                            break;
                        case 6:
                            consultarSaldo(banco, clienteService, input);
                            break;
                        case 7:
                            depositarDinero(banco, clienteService, cuentaService, input);
                            break;
                        case 8:
                            transferirDinero(banco, clienteService, cuentaService, input);
                            break;
                        case 9:
                            eliminarSucursal(banco, sucursalService, input);
                            break;
                        case 10:
                            agregarSucursal(banco, sucursalService, input);
                            break;
                        case 11:
                            agregarEmpleado(banco, sucursalService, empleadoService, input);
                            break;
                        case 12:
                            System.out.println("Muchas gracias! Vuelva pronto!");
                            break;
                        default:
                            System.out.println("INFO:Ingrese una opción válida.\n\n");
                            break;
                    }

                } while ((opcion != 12));

            } catch (InputMismatchException e) {
                System.out.println("Se ha producido un error. Ingrese un valor entero.");
                error = false;
                input.next();
            }
        } while (!error);
    }
        
    public static void mostrarMenuPrincipal() {
        System.out.println("************MENU PRINCIPAL************");
        System.out.println("1)Agregar Cliente");
        System.out.println("2)Agregar cuenta a Cliente");
        System.out.println("3)Listar Clientes por sucursal");
        System.out.println("4)Listar Clientes de una sucursal");
        System.out.println("5)Extraer dinero");
        System.out.println("6)Consultar Saldo");
        System.out.println("7)Realizar Deposito");
        System.out.println("8)Realizar transferencias");
        System.out.println("9)Eliminar una sucursal");
        System.out.println("10)Agregar sucursal");
        System.out.println("11)Agregar empleado");
        System.out.println("12)Salir");
        System.out.println("");
        System.out.println("\nIngrese opción deseada:");
    }
    
    public static void agregarCliente(Banco banco, ISucursalService sucursalService, IClienteService clienteService, ICuentaService cuentaService, Scanner input) {
        try {
            System.out.println("******Agregar Cliente:******");
            System.out.println("*Seleccione sucursal:*");
            for (Sucursal s : sucursalService.buscarSucursalesPorBanco(banco.getId())) {
                System.out.println("Sucursal Id: " + s.getNumeroSucursal() + "\tNombre sucursal:" + s.getNombreSucursal());
            }
            System.out.print("\nIngrese el Id de la sucursal elegida:");
            int idSucursal = input.nextInt();
            Sucursal sucursal = sucursalService.buscarSucursalPorId(idSucursal);

            //agregar Cliente
            input.nextLine(); //limpia el scanner
            System.out.println("*Datos personales del cliente:*");
            System.out.print("Ingrese apellido del nuevo Cliente: ");
            String apellido = input.nextLine();
            System.out.print("Ingrese nombre del nuevo Cliente: ");
            String nombre = input.nextLine();
            System.out.print("Ingrese DNI del nuevo Cliente: ");
            String dni = input.nextLine();
            System.out.print("Ingrese telefono del nuevo Cliente: ");
            String telefono = input.nextLine();
            System.out.print("Ingrese email del nuevo Cliente: ");
            String email = input.nextLine();
            System.out.print("Ingrese domicilio del nuevo Cliente: ");
            String domicilio = input.nextLine();            
            Date fechaAlta = Date.valueOf(LocalDate.now());
            Cliente nuevoCliente = new Cliente(domicilio, fechaAlta, sucursal, dni, nombre, apellido, telefono, email);

            //agrego cliente
            clienteService.agregarCliente(nuevoCliente);
            //recupero cliente agregado
            nuevoCliente = clienteService.buscarClientePorDni(dni);
            if (nuevoCliente != null) {
                System.out.println("Se agrego cliente.");
                System.out.println("*Crear cuenta:*");
                System.out.println("Seleccione el Tipo de Cuenta que desea abrir:");
                System.out.println("1) Caja de Ahorro en Pesos");
                System.out.println("2) Caja de Ahorro en Dolares");
                System.out.println("3) Cuenta Corriente en Pesos");
                System.out.println("4) Cuenta Corriente en Dolares");

                int tipoCuenta = input.nextInt();
                input.nextLine();

                System.out.println("Ingrese CBU para la cuenta:");
                String cbu = input.nextLine();
                Cuenta cuenta = null;
                switch (tipoCuenta) {
                    case 1:
                        cuenta = new CajaDeAhorro(0.00, cbu, nuevoCliente, banco, TipoCuenta.CAJA_AHORRO.getDescripcion(), TipoMoneda.PESOS.getDescripcion());
                        break;
                    case 2:
                        cuenta = new CajaDeAhorro(0.00, cbu, nuevoCliente, banco, TipoCuenta.CAJA_AHORRO.getDescripcion(), TipoMoneda.DOLARES.getDescripcion());
                        break;
                    case 3:
                        cuenta = new CuentaCorriente(0.00, cbu, nuevoCliente, banco, TipoCuenta.CUENTA_CORRIENTE.getDescripcion(), TipoMoneda.PESOS.getDescripcion());
                        break;
                    case 4:
                        cuenta = new CuentaCorriente(0.00, cbu, nuevoCliente, banco, TipoCuenta.CUENTA_CORRIENTE.getDescripcion(), TipoMoneda.DOLARES.getDescripcion());
                        break;
                    default:
                        System.out.println("No se ingreso una opcion valida.");
                }
                if (cuenta != null) {
                    cuentaService.agregarCuenta(cuenta);
                    System.out.println("Se agrego cuenta.");
                }

            } else {
                System.out.println("No se agrego cliente.");
            }

        } catch (InputMismatchException e) {
            System.out.println("No se ingreso el valor esperado.");
            input.next();
        } catch (Exception e) {
            System.out.println("Se ha producido un error.");
            input.next();
        }
    }

    public static void agregarCuentaACliente(Banco banco, IClienteService clienteService, ICuentaService cuentaService, Scanner input) {
        try {
            input.nextLine();
            System.out.println("******Agregar cuenta a cliente:******");
            System.out.println("Ingrese DNI del cliente a buscar:");
            String dni = input.nextLine();

            Cliente clienteBuscado = clienteService.buscarClientePorDni(dni);
            if (clienteBuscado != null) {
                System.out.println("************MENU CLIENTE-CUENTA************");
                System.out.println("1)Alta de Caja de Ahorro en Pesos");
                System.out.println("2)Alta de Caja de Ahorro en dólares");
                System.out.println("3)Alta de Cuenta Corriente en Pesos");
                System.out.println("4)Alta de Cuenta Corriente en dólares");
                System.out.println("\nIngrese opción deseada:");

                int tipoCuenta = input.nextInt();
                input.nextLine();
                System.out.println("Ingrese CBU para la cuenta:");
                String cbu = input.nextLine();
                Cuenta cuenta = null;
                switch (tipoCuenta) {
                    case 1:
                        cuenta = new CajaDeAhorro(0.00, cbu, clienteBuscado, banco, TipoCuenta.CAJA_AHORRO.getDescripcion(), TipoMoneda.PESOS.getDescripcion());
                        break;
                    case 2:
                        cuenta = new CajaDeAhorro(0.00, cbu, clienteBuscado, banco, TipoCuenta.CAJA_AHORRO.getDescripcion(), TipoMoneda.DOLARES.getDescripcion());
                        break;
                    case 3:
                        cuenta = new CuentaCorriente(0.00, cbu, clienteBuscado, banco, TipoCuenta.CUENTA_CORRIENTE.getDescripcion(), TipoMoneda.PESOS.getDescripcion());
                        break;
                    case 4:
                        cuenta = new CuentaCorriente(0.00, cbu, clienteBuscado, banco, TipoCuenta.CUENTA_CORRIENTE.getDescripcion(), TipoMoneda.DOLARES.getDescripcion());
                        break;
                    default:
                        System.out.println("No se ingreso una opcion valida.");
                }
                if (cuenta != null) {
                    cuentaService.agregarCuenta(cuenta);
                    System.out.println("Se agrego cuenta al cliente.");
                }

            } else {
                System.out.println("No se encontro cliente con el DNI ingresado.");
            }
        } catch (InputMismatchException e) {
            System.out.println("No se ingreso el valor esperado.");
            input.next();
        } catch (Exception e) {
            System.out.println("Se ha producido un error.");
            input.next();
        }
    }

    public static void listarClientesPorSucursales(Banco banco, ISucursalService sucursalService) {
        try {
            System.out.println("******Listar Cliente por sucursales:******");
            for (Sucursal s : sucursalService.buscarSucursalesPorBanco(banco.getId())) {
                System.out.println("Sucursal:" + s.getNombreSucursal() + "\tNro Sucursal:" + s.getNumeroSucursal());
                if (!s.getListaClientes().isEmpty()) {
                    for (Cliente cli : s.getListaClientes()) {
                        System.out.println("\t\n" + cli);
                        if (!cli.getCuentas().isEmpty()) {
                            for (Cuenta c : cli.getCuentas()) {
                                System.out.println("\t" + c);
                            }
                        } else {
                            System.out.println("\t*No tiene cuentas aún.");
                        }
                    }
                } else {
                    System.out.println("\tLa sucursal no tiene clientes aun.");
                }

            }
        } catch (Exception e) {
            System.out.println("Se ha producido un error.");
        }

    }

    public static void listarClientesDeUnaSucursal(Banco banco, ISucursalService sucursalService, Scanner input) {
        try {
            input.nextLine();
            System.out.println("******Listar Clientes por sucursal:******");
            System.out.println("*Seleccione sucursal:*");
            for (Sucursal s : sucursalService.buscarSucursalesPorBanco(banco.getId())) {
                System.out.println("Sucursal Id: " + s.getNumeroSucursal() + "\tNombre sucursal:" + s.getNombreSucursal());
            }
            System.out.print("\nIngrese el Id de la sucursal elegida:");
            int idSucursal = input.nextInt();

            Sucursal sucursal = sucursalService.buscarSucursalPorId(idSucursal);

            if (sucursal != null) {
                if (!sucursal.getListaClientes().isEmpty()) {
                    for (Cliente cli : sucursal.getListaClientes()) {
                        System.out.println("\t\n" + cli);
                        if (!cli.getCuentas().isEmpty()) {
                            for (Cuenta c : cli.getCuentas()) {
                                System.out.println("\t" + c);
                            }
                        } else {
                            System.out.println("\t*No tiene cuentas aún.");
                        }
                    }
                } else {
                    System.out.println("\tNo tiene clientes agregados aun.");
                }

            } else {
                System.out.println("No se encontró sucursal.");
            }

        } catch (InputMismatchException e) {
            System.out.println("No se ingreso el valor esperado.");
            input.next();
        } catch (Exception e) {
            System.out.println("Se ha producido un error.");
            input.next();
        }
    }

    public static void extraerDinero(Banco banco, IClienteService clienteService, ICuentaService cuentaService, Scanner input) {
        try {
            input.nextLine();
            System.out.println("******Extracción de dinero:******");
            System.out.println("Ingrese DNI del cliente a buscar:");
            String dni = input.nextLine();

            Cliente clienteBuscado = clienteService.buscarClientePorDni(dni);
            if (clienteBuscado != null) {
                System.out.println("\t\n" + clienteBuscado);
                if (!clienteBuscado.getCuentas().isEmpty()) {
                    for (Cuenta c : clienteBuscado.getCuentas()) {
                        System.out.println("\t" + c);
                    }
                    System.out.println("Ingrese CBU de la cuenta de la cual desea extraer dinero:");
                    String cbu = input.nextLine();
                    Cuenta cuentaOrigen = cuentaService.buscarCuentaPorCBU(cbu);

                    System.out.println("Ingrese monto a extraer:");
                    Double monto = input.nextDouble();

                    if (cuentaService.extraerDinero(cuentaOrigen, monto)) {
                        System.out.println("Se realizo extraccion con exito.");
                    } else {
                        System.out.println("No se pudo realizar la extraccion.");
                    }
                } else {
                    System.out.println("\t*No tiene cuentas aún.");
                }
            } else {
                System.out.println("No se encontro cliente con el DNI ingresado.");
            }
        } catch (InputMismatchException e) {
            System.out.println("No se ingreso el valor esperado.");
            input.next();
        } catch (Exception e) {
            System.out.println("Se ha producido un error.");
            input.next();
        }

    }

    public static void consultarSaldo(Banco banco, IClienteService clienteService, Scanner input) {
        try {
            input.nextLine();
            System.out.println("******Consultar saldo:******");
            System.out.println("Ingrese DNI del cliente a buscar:");
            String dni = input.nextLine();

            Cliente clienteBuscado = clienteService.buscarClientePorDni(dni);
            if (clienteBuscado != null) {
                System.out.println("\t\n" + clienteBuscado);
                if (!clienteBuscado.getCuentas().isEmpty()) {
                    for (Cuenta c : clienteBuscado.getCuentas()) {
                        System.out.println("\t" + c);
                    }
                } else {
                    System.out.println("\t*No tiene cuentas aún.");
                }
            } else {
                System.out.println("No se encontro cliente con el DNI ingresado.");
            }
        } catch (InputMismatchException e) {
            System.out.println("No se ingreso el valor esperado.");
            input.next();
        } catch (Exception e) {
            System.out.println("Se ha producido un error.");
            input.next();
        }

    }

    public static void depositarDinero(Banco banco, IClienteService clienteService, ICuentaService cuentaService, Scanner input) {
        try {
            input.nextLine();
            System.out.println("******Deposito de dinero:******");
            System.out.println("Ingrese DNI del cliente a buscar:");
            String dni = input.nextLine();

            Cliente clienteBuscado = clienteService.buscarClientePorDni(dni);
            if (clienteBuscado != null) {
                System.out.println("\t\n" + clienteBuscado);
                if (!clienteBuscado.getCuentas().isEmpty()) {
                    for (Cuenta c : clienteBuscado.getCuentas()) {
                        System.out.println("\t" + c);
                    }
                    System.out.println("Ingrese CBU de la cuenta de la cual desea depositar dinero:");
                    String cbu = input.nextLine();
                    Cuenta cuentaOrigen = cuentaService.buscarCuentaPorCBU(cbu);

                    System.out.println("Ingrese monto a depositar:");
                    Double monto = input.nextDouble();

                    if (cuentaService.depositarDinero(cuentaOrigen, monto)) {
                        System.out.println("Se realizo deposito con exito.");
                    } else {
                        System.out.println("No se pudo realizar deposito.");
                    }
                } else {
                    System.out.println("\t*No tiene cuentas aún.");
                }
            } else {
                System.out.println("No se encontro cliente con el DNI ingresado.");
            }
        } catch (InputMismatchException e) {
            System.out.println("No se ingreso el valor esperado.");
            input.next();
        } catch (Exception e) {
            System.out.println("Se ha producido un error.");
            input.next();
        }
    }

    public static void transferirDinero(Banco banco, IClienteService clienteService, ICuentaService cuentaService, Scanner input) {
        try {
            input.nextLine();
            System.out.println("******Transferir monto a otra cuenta:******");
            System.out.println("Ingrese su DNI para buscar sus cuentas:");
            String dni = input.nextLine();

            Cliente clienteBuscado = clienteService.buscarClientePorDni(dni);
            if (clienteBuscado != null) {
                System.out.println("\t\n" + clienteBuscado);
                if (!clienteBuscado.getCuentas().isEmpty()) {
                    for (Cuenta c : clienteBuscado.getCuentas()) {
                        System.out.println("\t" + c);
                    }
                    System.out.println("Ingrese CBU de la cuenta(Origen) desde la cual desea transferir dinero:");
                    String cbu = input.nextLine();
                    Cuenta cuentaOrigen = cuentaService.buscarCuentaPorCBU(cbu);

                    if (cuentaOrigen != null) {
                        System.out.println("Ingrese CBU de la cuenta(Destino) a la cual desea transferir dinero:");
                        String cbuDestino = input.nextLine();

                        System.out.println("Ingrese monto a transferir:");
                        Double monto = input.nextDouble();

                        if (cuentaService.transferirDinero(cuentaOrigen, cbuDestino, monto)) {
                            System.out.println("Se realizo transferencia con exito.");
                        } else {
                            System.out.println("No se pudo realizar la transferencia.");
                        }
                    } else {
                        System.out.println("No se encontro cuenta con el CBU ingresado.");
                    }

                } else {
                    System.out.println("\t*No tiene cuentas aún.");
                }
            } else {
                System.out.println("No se encontro cliente con el DNI ingresado.");
            }

        } catch (InputMismatchException e) {
            System.out.println("No se ingreso el valor esperado.");
            input.next();
        } catch (ErrorTipoCuentaYMonedaException e) {
            System.out.println(e.getMessage());
            input.next();
        } catch (Exception e) {
            System.out.println("Se ha producido un error.");
            input.next();
        }

    }

    public static void eliminarSucursal(Banco banco, ISucursalService sucursalService, Scanner input) {
        try {
            System.out.println("***Eliminar sucursal***");
            List<Sucursal> listaSucursales = sucursalService.buscarSucursalesPorBanco(banco.getId());

            if (listaSucursales.size() > 1) {
                for (Sucursal s : listaSucursales) {
                    System.out.println("Sucursal Id: " + s.getNumeroSucursal() + "\tNombre sucursal:" + s.getNombreSucursal());
                }
                System.out.print("\nIngrese el Id de la sucursal a eliminar:");
                int idSucursal = input.nextInt();
                System.out.print("\nIngrese el Id de la sucursal donde se trasladaran los clientes:");
                int idSucursalDestino = input.nextInt();

                sucursalService.eliminarSucursal(idSucursal, idSucursalDestino);
                System.out.println("Se elimino sucursal con exito.");

            } else {
                System.out.println("Solo se puede eliminar sucursal si existen mas de 1 sucursal.");
            }
        } catch (InputMismatchException e) {
            System.out.println("No se ingreso el valor esperado.");
            input.next();
        } catch (NoExisteSucursalException ex) {
            System.out.println(ex.getMessage());
            input.next();
        } catch (Exception e) {
            System.out.println("Se ha producido un error.");
            input.next();
        }
    }

    public static void agregarSucursal(Banco banco, ISucursalService sucursalService, Scanner input) {
        try {
            input.nextLine();
            System.out.println("***Agregar sucursal***");
            System.out.println("Ingrese nombre de la sucursal:");
            String nombre = input.nextLine();
            Sucursal sucursal = new Sucursal(nombre, banco.getId());
            sucursalService.agregarSucursal(sucursal);
            System.out.println("Se agrego sucursal");
        } catch (InputMismatchException e) {
            System.out.println("No se ingreso el valor esperado.");
            input.next();
        } catch (Exception e) {
            System.out.println("Se ha producido un error.");
            input.next();
        }

    }

    public static void agregarEmpleado(Banco banco, ISucursalService sucursalService,IEmpleadoService empleadoService, Scanner input) {
        try {            
            System.out.println("******Agregar Empleado:******");
            System.out.println("*Seleccione sucursal:*");
            for (Sucursal s : sucursalService.buscarSucursalesPorBanco(banco.getId())) {
                System.out.println("Sucursal Id: " + s.getNumeroSucursal() + "\tNombre sucursal:" + s.getNombreSucursal());
            }
            System.out.print("\nIngrese el Id de la sucursal elegida:");
            int idSucursal = input.nextInt();
            Sucursal sucursal = sucursalService.buscarSucursalPorId(idSucursal);

            //agregar empleado
            input.nextLine(); //limpia el scanner
            System.out.println("*Datos personales del Empleado:*");
            System.out.print("Ingrese nro de legajo del nuevo empleado: ");
            int legajo = input.nextInt();
            input.nextLine();
            System.out.print("Ingrese apellido del nuevo empleado: ");
            String apellido = input.nextLine();
            System.out.print("Ingrese nombre del nuevo empleado: ");
            String nombre = input.nextLine();
            System.out.print("Ingrese DNI del nuevo empleado: ");
            String dni = input.nextLine();
            System.out.print("Ingrese telefono del nuevo empleado: ");
            String telefono = input.nextLine();
            System.out.print("Ingrese email del nuevo empleado: ");
            String email = input.nextLine();
            
            Date fechaAlta = Date.valueOf(LocalDate.now());
            Empleado nuevo = new Empleado(legajo, fechaAlta, sucursal, dni, nombre, apellido, telefono, email);

            //agrego empleado
            empleadoService.agregarEmpleado(nuevo);
            System.out.println("Se agrego empleado");

        } catch (InputMismatchException e) {
            System.out.println("No se ingreso el valor esperado.");
            input.next();
        } catch (Exception e) {
            System.out.println("Se ha producido un error.");
            input.next();
        }
    }

}
