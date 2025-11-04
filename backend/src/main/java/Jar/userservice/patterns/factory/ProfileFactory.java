package Jar.userservice.patterns.factory;

import Jar.userservice.model.Profile;
import Jar.userservice.model.User;
import Jar.userservice.model.enums.Role;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class ProfileFactory {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Profile createProfile(Role role, User user) {
        Map<String, Object> specificData = new HashMap<>();

        // Configurar datos específicos según el rol
        switch (role) {
            case FARMER:
                specificData.put("productosCultivados", new String[]{});
                specificData.put("extensionTerreno", 0.0);
                specificData.put("tiposCultivo", new String[]{});
                break;

            case STORE:
                specificData.put("nombreTienda", "");
                specificData.put("insumosDisponibles", new String[]{});
                specificData.put("categorias", new String[]{});
                specificData.put("horarioAtencion", "");
                break;

            case CUSTOMER:
                specificData.put("productosFrecuentes", new String[]{});
                specificData.put("preferenciaCompra", "");
                break;

            case ADMINISTRATION:
                specificData.put("permisos", new String[]{"ALL"});
                specificData.put("nivelAcceso", "TOTAL");
                break;
        }

        // Convertir los Maps a JSON String
        String paymentMethodsJson = convertMapToJson(new HashMap<>());
        String specificDataJson = convertMapToJson(specificData);

        // Crear el perfil usando el builder
        return Profile.builder()
                .user(user)
                .paymentMethodsJson(paymentMethodsJson)
                .specificDataJson(specificDataJson)
                .build();
    }

    // Método auxiliar para convertir Map a JSON String
    private static String convertMapToJson(Map<String, Object> map) {
        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return "{}"; // Retornar JSON vacío en caso de error
        }
    }
}