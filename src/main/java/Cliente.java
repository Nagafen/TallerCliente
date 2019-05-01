/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.util.Scanner;
import javax.ws.rs.core.MediaType;

public class Cliente {

    public static void main(String[] args) {
        try {

            Client client = Client.create();
            WebResource webResource = null;
            ClientResponse response = null;
            int opcion = -1;
            Scanner lectura = new Scanner(System.in);
            System.out.println("Bienvenido al sistema del Hostpital");
           
            do {
                System.out.println("1.Registrar   2.Listar   3.Salir");
                opcion = lectura.nextInt();
                switch (opcion) {
                    case 1:
 
                        System.out.println("¿Ha sido atendido previamente en este hospital? Y/N");
                        String opcion2 = lectura.next();
                        if (opcion2.equalsIgnoreCase("Y")) {

                            ObjectMapper mapper = new ObjectMapper();
                            Paciente paciente = new Paciente();

                            System.out.println("Ingrese su nombre");
                            String nombre = lectura.next();
                            paciente.setNombre(nombre);

                            webResource = client.resource("http://localhost:8090/myapp/rest/pacientes/" + nombre);

                            response = webResource.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);

                            if (response.getStatus() != 200) {
                                System.out.println("Usuario no encontrado \n");
                                System.out.println("Registrese primero \n");
                                
                                System.out.println("----------------------------------------------------");
                                
                                System.out.println("Ingrese su nombre \n");
                                String nombreR = lectura.next();
                                lectura.nextLine();
                                paciente.setNombre(nombreR);
                                
                                System.out.println("----------------------------------------------------");
                                System.out.println("Ingrese su direccion \n");
                                String direccion = lectura.nextLine();
                                paciente.setAddress(direccion);
                                
                                System.out.println("----------------------------------------------------");
                                
                                System.out.println("Ingrese su fecha de nacimiento (YYYY MM DD) \n");
                                String fecha = lectura.nextLine();
                                paciente.setFecha(fecha);
                                
                                System.out.println("----------------------------------------------------");
                                System.out.println("Ingrese su numero de telefono (99 9999 9999) \n");
                                String telefono = lectura.nextLine();
                                paciente.setNumero(telefono);
                                
                                System.out.println("----------------------------------------------------");
                                System.out.println("Ingrese su nivel de salud \n");
                                String estado = lectura.nextLine();
                                paciente.setStatus(estado);
                                
                                String input = mapper.writeValueAsString(paciente);
                                
                                System.out.println(input);
                                webResource = client.resource("http://localhost:8090/myapp/rest/pacientes");
                                response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, input);
                                
                                if (response.getStatus() != 200) {
                                    System.out.println(response.toString());
                            throw new RuntimeException("Failed : HTTP error code : "
                                    + response.getStatus());
                                }else{
                                    System.out.println("Ingresado Correctamente");
                                    break;
                                }
                                
                            } else {
                                System.out.println("Registro encontrado");
                                String output = response.getEntity(String.class);
                                System.out.println("Ya se encuentra registrado en el Hospital \n");
                                System.out.println(output);
                                break;
                            }
                        }
                        break;

                    case 2:
                        webResource = client
                                .resource("http://localhost:8090/myapp/rest/pacientes");

                        response = webResource.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);

                        if (response.getStatus() != 200) {
                            System.out.println(response.toString());
                            throw new RuntimeException("Failed : HTTP error code : "
                                    + response.getStatus());
                        }

                        String output = response.getEntity(String.class);
                        System.out.println("Output from Server .... \n");
                        System.out.println(output);
                        break;
                        
                    default:
                        System.out.println("Opcion invalida");

                }

            } while (opcion != 3);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
}
