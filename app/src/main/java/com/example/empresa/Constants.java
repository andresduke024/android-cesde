package com.example.empresa;

public class Constants {
    /*
     CAMBIAR POR LA IP DEL EQUIPO DONDE SE ESTA CORRIENDO XAMPP Y LA API DE PHP http://IP-LOCAL:PUERTO/NOMBRE-CARPETA-API/

     IP-LOCAL -> Correr comando ipconfig en el cmd y tomar la dirección IPv4
     PUERTO -> El puerto en el que esta corriend el servidor normalmente el 8080 en windows (Se visualiza y configura en XAMPP)
     NOMBRE-CARPETA-API -> El nombre de la carpeta en que se se encuentra la API (que debe estar en la carpeta htdocs de XAMPP)

     NOTA: NO USAR LOCALHOST PORQUE LOS SIMULADORES NO PUEDEN ACCEDER A ESTA IP SE. DEBE USAR LA IP PUBLICA DEL EQUIPO
    */
    static String API = "http://192.168.1.6:80/Empresa1API/";
}
