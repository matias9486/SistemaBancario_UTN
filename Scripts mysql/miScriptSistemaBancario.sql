CREATE DATABASE sistemaBancario_MatiasAlancay; 
use sistemaBancario_MatiasAlancay;

create table if not exists  Bancos(
bancoId INT primary key AUTO_INCREMENT NOT NULL,
nombre VARCHAR(30) NOT NULL
);

create table if not exists  Sucursales(
sucursalId INT primary key AUTO_INCREMENT NOT NULL,
nombre VARCHAR(30) NOT NULL,
bancoId int not null,
FOREIGN KEY (bancoId) REFERENCES Bancos(bancoId)
);

create table if not exists CLIENTES(
clienteId INT primary key AUTO_INCREMENT NOT NULL,
# CUIL BIGINT NOT NULL, 
dni VARCHAR(8) NOT NULL,
nombre VARCHAR(60) NOT NULL,
apellido VARCHAR(60) NOT NULL,
telefono VARCHAR(60) NOT NULL,
email VARCHAR(60) NOT NULL,
sucursalId int not null,
domicilio VARCHAR(60) NOT NULL,
altaCliente date not null,
bancoId int not null,
FOREIGN KEY (sucursalId) REFERENCES Sucursales(sucursalId),
FOREIGN KEY (bancoId) REFERENCES Bancos(bancoId)
);


create table if not exists CUENTAS(
cuentaId INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
cbu VARCHAR(20) NOT NULL,
saldo FLOAT NOT NULL,
clienteId INT NOT NULL,
bancoId int not null,
tipoCuenta VARCHAR(20) NOT NULL, # Cuenta_Corriente, Caja_Ahorro
tipoMoneda VARCHAR(20) NOT NULL, # CA, CC
FOREIGN KEY (bancoId) REFERENCES Bancos(bancoId),
FOREIGN KEY (clienteId) REFERENCES clientes(clienteId)
);

#Integer id, integer legajo, Date fechaIngreso, Sucursal sucursal, String dni, String nombre,String apellido, String telefono, String email) {
create table if not exists EMPLEADOS(
empleadoId INT primary key AUTO_INCREMENT NOT NULL,
nroLegajo int not null,
dni VARCHAR(8) NOT NULL,
nombre VARCHAR(60) NOT NULL,
apellido VARCHAR(60) NOT NULL,
telefono VARCHAR(60) NOT NULL,
email VARCHAR(60) NOT NULL,
sucursalId int not null,
fechaIngreso date not null,
bancoId int not null,
FOREIGN KEY (sucursalId) REFERENCES Sucursales(sucursalId),
FOREIGN KEY (bancoId) REFERENCES Bancos(bancoId)
);


